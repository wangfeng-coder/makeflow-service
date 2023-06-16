package com.makeid.makeflow.workflow.entity.impl;

import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
@Document(collection = "makeflow_execute")
public class ExecuteEntityImpl extends BaseEntity implements ExecuteEntity {

    protected String flowInstId;

    protected String definitionId;

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
     * 设计时的id
     */
    protected String activityCodeId;

    /**
     * 流程定义id
     */
    protected String processDefinitionId;

    /**
     * 持久化参数
     */
    protected Map<String,Object> variables;
}
