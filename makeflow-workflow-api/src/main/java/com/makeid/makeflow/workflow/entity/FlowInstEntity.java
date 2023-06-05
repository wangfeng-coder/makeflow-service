package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface FlowInstEntity extends Entity {
    String getTemplateCodeId();

    void setTemplateCodeId(String templateCodeId);

    String getTemplateId();

    void setTemplateId(String templateId);

    int getStatus();

    void setStatus(int status);


}