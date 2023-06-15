package com.makeid.makeflow.workflow.entity.impl;

import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
*@program makeflow-service
*@description 流程实列数据库实体
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
@Document("makeflow_flowInst")
public class FlowInstEntityImpl extends BaseEntity implements FlowInstEntity {


    private String templateId;

    private String templateCodeId;

    private int status;


}
