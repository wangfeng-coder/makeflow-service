package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;

import java.util.Date;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 退回
 * @create 2023-06-27
 */
public class ReturnCmd extends CompleteTaskCmd {


    private String targetActivityCodeId;

    private String opinion;

    public ReturnCmd(String taskId, Map<String, Object> variables, String targetActivityCodeId, String opinion) {
        super(taskId, variables);
        this.targetActivityCodeId = targetActivityCodeId;
        this.opinion = opinion;
    }


    @Override
    protected void completeTask(TaskEntity taskEntity, ProcessInstanceExecution processInstanceExecution) {
        returnTask(taskEntity);
        cancelOtherTaskEntity(taskEntity);
        //修改当前节点为退回
        ActivityImpl currentActivity = processInstanceExecution.getCurrentActivity();
        currentActivity.returnBack();
        freeLinkNextActivity(currentActivity, targetActivityCodeId, processInstanceExecution.getProcessDefinition());
        continueRun(processInstanceExecution);
    }

    private void returnTask(TaskEntity taskEntity) {
        taskEntity.setStatus(TaskStatusEnum.RETURN.status);
        taskEntity.setOpinion(this.opinion);
        taskEntity.setCompleteTime(new Date());
        save(taskEntity);
    }
}
