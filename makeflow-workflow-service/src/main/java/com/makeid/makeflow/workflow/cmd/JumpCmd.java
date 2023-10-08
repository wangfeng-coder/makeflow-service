package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-27
 */
public class JumpCmd extends AbstractCommand<Void>{

    /**
     * 目标活动的codeId
     */
    private String targetActivityCodeId;

    /**
     * 当前节点的activityId
     */
    private Long sourceActivityId;

    /**
     * 处理意见
     */
    private String opinion;


    public JumpCmd(String targetActivityCodeId, Long sourceActivityId, String opinion,Map<String, Object> variables) {
        super(variables);
        this.targetActivityCodeId = targetActivityCodeId;
        this.sourceActivityId = sourceActivityId;
        this.opinion = opinion;
    }

    @Override
    public Void execute() {
        //查找当前节点待处理任务
        ActivityEntity activity = findActivity(sourceActivityId);
        Assert.notNull(activity);
        Assert.isTrue(Objects.equals(activity.getStatus(), ActivityStatusEnum.RUNNING.status),"当前节点未在运行时");
        //将当前节点任务都置于取消状态
        Context.getTaskService().cancelAllTask(activity.getId());
        //为当前节点新增一个跳转状态的任务
        ProcessInstanceExecution processInstanceExecution = findProcessInstanceExecution(activity.getExecutionId());
        createSaveJumpTask(processInstanceExecution);
        ActivityImpl currentActivity = processInstanceExecution.getCurrentActivity();
        currentActivity.jump();
        freeLinkNextActivity(currentActivity, targetActivityCodeId, processInstanceExecution.getProcessDefinition());
        continueRun(processInstanceExecution);
        return null;
    }

    private TaskEntity createSaveJumpTask(ProcessInstanceExecution processInstanceExecution) {
        List<TaskEntity> tasks = Context.getTaskService().createTask(Arrays.asList(Context.getUserId()), processInstanceExecution);
        for (TaskEntity taskEntity : tasks) {
            taskEntity.setStatus(TaskStatusEnum.JUMP.status);
            taskEntity.setOpinion(opinion);
        }
        Context.getTaskService().save(tasks);
        return tasks.get(0);
    }
}
