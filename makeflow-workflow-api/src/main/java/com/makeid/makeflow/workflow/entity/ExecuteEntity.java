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

    Long getFlowInstId();

    void setFlowInstId(Long flowInstId);

    Long getRootFlowInstId();

    void setRootFlowInstId(Long flowInstId);

    Long getParentId();

    void setParentId(Long parentId);

    String getStatus();

    void setStatus(String status);

    void setStartTime(Date startTime);

    Date getStartTime();

    void setEndTime(Date endTime);

    Date getEndTime();

    void setActivityCodeId(String activityCodeId);

    String getActivityCodeId();

    Long getActivityId();

    void setActivityId(Long activityId);

    Map<String,Object> getVariables();

    void setVariables(Map<String,Object> variable);

    Long getDefinitionId();

    void setDefinitionId(Long definitionId);



}
