package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-07
 */

@Document(collection = "makeflow_task")
public interface TaskEntity extends Entity {

    String getName();

    void setName(String name);

    /***
     * 处理人
     * @return
     */
    String getHandler();

    void setHandler(String handler);

    /**
     * 任务类型
     * @return
     */
    String getTaskType();

    void setTaskType(String taskType);

    /**
     * 完成时间
     * @return
     */
    Date getCompleteTime();

    void setCompleteTime(Date time);


    String getStatus();

    /**
     * 任务状态
     */
    void setStatus(String status);

    void setFlowInstId(String flowInstId);

    String getFlowInstId();

    void setActivityCodeId(String activityCodeId);

    String getActivityCodeId();

    void setActivityId(String activityId);

    String getActivityId();

    String getOpinion();

    void setOpinion(String opinion);
}
