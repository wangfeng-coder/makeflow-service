package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.workflow.delegate.DelegateExecuteReader;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
@Document(collection = "makeflow_execute")
public interface ExecuteEntity extends Entity, DelegateExecuteReader {

    String getFlowInstId();

    void setFlowInstId(String flowInstId);

    String getParentId();

    void setParentId(String parentId);

    String getStatus();

    void setStatus(String status);

    void setStartTime(Date startTime);

    Date getStartTime();

    void setEndTime(Date endTime);

    Date getEndTime();

    void setActivityCodeId(String activityCodeId);

    String getActivityCodeId();

    String getActivityId();

    void setActivityId(String activityId);

    Map<String,Object> getVariables();

    void setVariables(Map<String,Object> variable);

    String getDefinitionId();

    void setDefinitionId(String definitionId);



}
