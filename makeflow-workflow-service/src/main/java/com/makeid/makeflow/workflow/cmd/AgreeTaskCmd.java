package com.makeid.makeflow.workflow.cmd;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
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
public class AgreeTaskCmd extends CompleteTasksCmd{

    /**
     * 审批意见
     */
    private  String opinion;

    public AgreeTaskCmd(String taskId,String opinion, Map<String, Object> variables) {
        super(Arrays.asList(taskId), variables);
        this.opinion = opinion;
    }

    /**
     * 完成前 设置审批意见
     * @param taskEntities
     */
    @Override
    protected void batchDoTask(List<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setOpinion(this.opinion);
            taskEntity.setStatus(TaskStatusEnum.DONE.status);
            taskEntity.setCompleteTime(new Date());
        }
        save(taskEntities);
    }
}
