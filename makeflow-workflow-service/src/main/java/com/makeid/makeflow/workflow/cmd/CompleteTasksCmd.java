package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.ErrCodeEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.exception.TaskException;
import com.makeid.makeflow.workflow.process.CoreExecution;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 完成任务
 * @create 2023-06-21
 */
public class CompleteTasksCmd extends AbstractCommand<Void> implements LockCommand{

    protected final List<String> taskIds;

    public CompleteTasksCmd(List<String> taskIds, Map<String, Object> variables) {
        this.taskIds = taskIds;
        this.variables = variables;
    }



    @Override
    public Void execute() {
        List<TaskEntity> taskEntities = Context.getTaskService().findTaskByIds(taskIds);
        validateTaskDoingStatus(taskEntities);
        validateTaskHandler(taskEntities,Context.getUserId());
        batchDoTask(taskEntities);
        List<ActivityEntity> activities = findActivities(taskEntities);
        batchDealActivity(activities);
        List<ExecuteEntity> executions = findExecution(activities);
        //找到对应执行实例 尝试执行
        List<ProcessInstanceExecution> executionList = Context.getExecutionService().transferExecution(executions);
        executionList.forEach(CoreExecution::activate);
        executionList.forEach(e->e.completeTask(taskEntities));
        return null;
    }

    protected void batchDealActivity(List<ActivityEntity> activities) {

    }

    protected  void batchDoTask(List<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setCompleteTime(new Date());
            taskEntity.setStatus(TaskStatusEnum.DONE.status);
        }
        save(taskEntities);
    }

    protected void validateTaskHandler(List<TaskEntity> taskEntities,String handler) {
        for (TaskEntity taskEntity : taskEntities) {
            if(!StringUtils.equals(handler,taskEntity.getHandler())) {
                throw new TaskException(ErrCodeEnum.HANDLER_INCONSISTENT);
            }
        }
    }

    protected void validateTaskDoingStatus(List<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity : taskEntities) {
            if(!StringUtils.equals(taskEntity.getStatus(), TaskStatusEnum.DOING.status)) {
                throw new TaskException(ErrCodeEnum.TASK_STATUS_NOT_DOING);
            }
        }
    }

    protected List<ActivityEntity> findActivities(List<TaskEntity> taskEntities) {
        List<String> activityIds = taskEntities.stream()
                .map(TaskEntity::getActivityId)
                .distinct()
                .collect(Collectors.toList());
       List<ActivityEntity> activityEntities =  Context.getActivityService().findByIds(activityIds);
       return activityEntities;
    }

    protected List<ExecuteEntity> findExecution(List<ActivityEntity> activityEntities) {
        List<String> executionIds = activityEntities.stream().map(ActivityEntity::getExecutionId)
                .distinct()
                .collect(Collectors.toList());
        List<ExecuteEntity> executions = Context.getExecutionService().findByIds(executionIds);
        return executions;
    }

    protected void save(List<TaskEntity> taskEntities) {
        Context.getTaskService().save(taskEntities);
    }
}
