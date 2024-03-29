package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.event.EventRegister;
import com.makeid.makeflow.workflow.event.TaskDoneEvent;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;


import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-25
 */
public class AgreeTaskCmd extends CompleteTaskCmd{

    /**
     * 审批意见
     */
    private  String opinion;

    public AgreeTaskCmd(Long taskId, String opinion, Map<String, Object> variables) {
        super(taskId,variables);
        this.opinion = opinion;
    }

    /**
     * 完成前 设置审批意见
     * @param
     */
    private void agreeTask(TaskEntity taskEntity) {
        taskEntity.setOpinion(this.opinion);
        taskEntity.setStatus(TaskStatusEnum.DONE.status);
        taskEntity.setCompleteTime(new Date());
        save(taskEntity);
        EventRegister.post(new TaskDoneEvent(Arrays.asList(taskEntity)));
        //TODO 处理其他任务 如果是或签 还需取消其他任务
    }

    @Override
    protected void completeTask(TaskEntity taskEntity, ProcessInstanceExecution processInstanceExecution) {
        agreeTask(taskEntity);
        continueRun(processInstanceExecution);
    }
}
