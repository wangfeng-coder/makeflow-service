package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public interface ActivityEntity extends Entity {

    String getName();

    void setName(String name);

    String getActivityType();

    void setActivityType(String type);

    void setActivityCodeId(String activityCodeId);

    Date getStartTime();

    void setStartTime(Date date);

    Date getEndTime();

    void setEndTime(Date date);

    int getStatus();

    void setStatus(int status);

    String getFlowInstId();

    void setFlowInstId(String flowInstId);

    void setExecutionId(String executionId);

    String getExecutionId();

    void setParentId(String parentId);

    String getParentId();

    void setDefinitionId(String definitionId);

    String getDefinitionId();

    String getPreActivityId();

    void setPreActivityId(String preActivityId);



}
