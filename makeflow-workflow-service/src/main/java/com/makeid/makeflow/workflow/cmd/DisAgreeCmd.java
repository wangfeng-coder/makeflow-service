package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-25
 */
public class DisAgreeCmd extends CompleteTasksCmd{


    protected String opinion;

    public DisAgreeCmd(Map<String, Object> variables, String taskId, String opinion) {
        super(Arrays.asList(taskId),variables);
        this.opinion = opinion;
    }

    @Override
    protected void batchDoTask(List<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setOpinion(opinion);
            taskEntity.setStatus(TaskStatusEnum.DISAGREE.status);
            taskEntity.setCompleteTime(new Date());
        }
        save(taskEntities);
        //处理其它运行任务为cancel
        Context.getTaskService().cancelOtherTask(taskEntities);
    }

    @Override
    protected void batchDealActivity(List<ActivityEntity> activities) {
        for (ActivityEntity activity : activities) {
            activity.setStatus(ActivityStatusEnum.DISAGREE.status);
            activity.setEndTime(new Date());
        }
        Context.getActivityService().save(activities);
    }
}
