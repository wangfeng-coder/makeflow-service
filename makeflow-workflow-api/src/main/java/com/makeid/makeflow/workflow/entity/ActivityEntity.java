package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.workflow.delegate.DelegateActivityReader;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
@Document(collection = "makeflow_activity")
public interface ActivityEntity extends Entity, DelegateActivityReader {

    String getName();


    String getActivityType();



    Date getStartTime();


    Date getEndTime();


    String getStatus();


    String getFlowInstId();



    String getExecutionId();


    String getParentId();


    String getDefinitionId();

    String getPreActivityId();


    void setName(String name);


    void setActivityType(String type);

    void setActivityCodeId(String activityCodeId);


    void setStartTime(Date date);


    void setEndTime(Date date);


    void setStatus(String status);


    void setFlowInstId(String flowInstId);

    void setExecutionId(String executionId);


    void setParentId(String parentId);


    void setDefinitionId(String definitionId);


    void setPreActivityId(String preActivityId);

}
