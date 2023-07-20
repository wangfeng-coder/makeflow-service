package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;


import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-25
 */
public class DisAgreeCmd extends CompleteTaskCmd {


    protected String opinion;

    public DisAgreeCmd(Map<String, Object> variables, Long taskId, String opinion) {
        super(taskId,variables);
        this.opinion = opinion;
    }

    private void disAgree(TaskEntity taskEntity) {
        taskEntity.setOpinion(opinion);
        taskEntity.setStatus(TaskStatusEnum.DISAGREE.status);
        taskEntity.setCompleteTime(new Date());
        save(taskEntity);
    }

    @Override
    protected void completeTask(TaskEntity taskEntity, ProcessInstanceExecution processInstanceExecution) {
        disAgree(taskEntity);
        cancelOtherTaskEntity(taskEntity);
        //更新当前节点
        ActivityImpl currentActivity = processInstanceExecution.getCurrentActivity();
        currentActivity.disAgree();
        processInstanceExecution.end();
        processInstanceExecution.endProcessInstance(FlowStatusEnum.DISAGREE);
    }



    private void disAgreeActivity(ActivityEntity activity) {
        activity.setStatus(ActivityStatusEnum.DISAGREE.status);
        activity.setEndTime(new Date());
        Context.getActivityService().save(activity);
    }
}
