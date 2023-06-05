package com.makeid.makeflow.workflow.entity.impl;

import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
@Document
public class ExecuteEntityImpl extends BaseEntity implements ExecuteEntity {

    protected String flowInstId;

    protected String rootFlowInstId;

    protected String parentId;

    protected int status;

    protected Date startTime;

    protected Date endTime;

    /**
     * 当前活动id
     */
    protected String activityId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;


}
