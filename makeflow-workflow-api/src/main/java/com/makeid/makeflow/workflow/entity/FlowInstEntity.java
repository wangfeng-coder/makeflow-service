package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface FlowInstEntity extends Entity {
    String getDefinitionCodeId();

    void setDefinitionCodeId(String templateCodeId);

    String getDefinitionId();

    void setDefinitionId(String definitionId);

    int getStatus();

    void setStatus(int status);

    void setStartTime(Date startTime);

    Date getStartTime();

    void setEndTime(Date endTime);

    Date getEndTime();


}