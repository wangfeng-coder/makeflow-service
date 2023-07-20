package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.template.bpmn.model.Task;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;


import java.util.Arrays;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 完成任务
 * @create 2023-06-21
 */
public abstract class CompleteTaskCmd extends AbstractCommand<Void> implements LockCommand{

    protected final Long taskId;

    public CompleteTaskCmd(Long taskId, Map<String, Object> variables) {
        this.taskId = taskId;
        this.variables = variables;
    }

    protected abstract void completeTask(TaskEntity taskEntity,ProcessInstanceExecution processInstanceExecution);

    @Override
    public Void execute() {
        TaskEntity taskEntity = findTaskEntity(taskId);
        validateTaskDoingStatus(taskEntity);
        validateTaskHandler(Context.getUserId(),taskEntity);
        ActivityEntity activity = findActivity(taskEntity.getActivityId());
        ProcessInstanceExecution processInstanceExecution = findProcessInstanceExecution(activity.getExecutionId());
        completeTask(taskEntity,processInstanceExecution);
        return null;
    }

    protected void save(TaskEntity taskEntity) {
        Context.getTaskService().save(taskEntity);
    }

    protected void cancelOtherTaskEntity(TaskEntity taskEntity) {
        //处理其它运行任务为cancel
        Context.getTaskService().cancelOtherTask(taskEntity.getActivityId(),taskEntity.getId());
    }
}
