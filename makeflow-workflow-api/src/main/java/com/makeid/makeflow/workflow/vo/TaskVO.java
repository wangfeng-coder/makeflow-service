package com.makeid.makeflow.workflow.vo;

import com.makeid.makeflow.workflow.entity.TaskEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */

@Getter
@Setter
public class TaskVO {

    private String activitId;

    private String handler;

    private String handTime;

    private String status;

    public static TaskVO from(TaskEntity taskEntity) {
        TaskVO taskVO = new TaskVO();
        taskVO.setStatus(taskEntity.getStatus());
        taskVO.setHandler(taskEntity.getHandler());
        taskVO.setHandTime(taskEntity.getHandler());
        taskVO.setActivitId(taskEntity.getActivityId());
        return taskVO;
    }
}
