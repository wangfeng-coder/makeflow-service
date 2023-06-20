package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
@Document(collection = "makeflow_activity")
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

    String getStatus();

    void setStatus(String status);

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
