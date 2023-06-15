package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface ExecuteEntity extends Entity {

    String getFlowInstId();

    void setFlowInstId(String flowInstId);

    String getParentId();

    void setParentId(String parentId);

    int getStatus();

    void setStatus(int status);

    void setStartTime(Date startTime);

    Date getStartTime();

    void setActivityCodeId(String activityCodeId);

    String getActivityCodeId();

    String getActivityId();





}
