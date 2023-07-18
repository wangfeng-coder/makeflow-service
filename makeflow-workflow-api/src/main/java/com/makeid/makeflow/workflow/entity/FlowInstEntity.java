package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
@Document("makeflow_flowInst")
public interface FlowInstEntity extends Entity {
    String getDefinitionCodeId();

    void setDefinitionCodeId(String templateCodeId);

    String getDefinitionId();

    void setDefinitionId(String definitionId);

    String getStatus();

    void setStatus(String status);

    void setApplyTime(Date supplyTime);

    Date getApplyTime();

    void setStartTime(Date startTime);

    Date getStartTime();

    void setEndTime(Date endTime);

    Date getEndTime();


    void setApply(String userId);

    String getApply();
}