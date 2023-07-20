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


    Long getFlowInstId();



    Long getExecutionId();


    Long getParentId();


    Long getDefinitionId();

    Long getPreActivityId();


    void setName(String name);


    void setActivityType(String type);

    void setActivityCodeId(String activityCodeId);


    void setStartTime(Date date);


    void setEndTime(Date date);


    void setStatus(String status);


    void setFlowInstId(Long flowInstId);

    void setExecutionId(Long executionId);


    void setParentId(Long parentId);


    void setDefinitionId(Long definitionId);


    void setPreActivityId(Long preActivityId);

}
