package com.makeid.makeflow.workflow.entity.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

/**
*@program makeflow-service
*@description 流程实列数据库实体
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
@TableName("makeflow_flow_inst")
public class FlowInstEntityImpl extends BaseEntity implements FlowInstEntity {

    private Long definitionId;

    private String definitionCodeId;

    private String status;

    private Date startTime;

    private Date endTime;

    private Date applyTime;

    private String apply;
}

