package com.makeid.makeflow.workflow.entity.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import lombok.Getter;
import lombok.Setter;


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
@TableName(value = "makeflow_execute",autoResultMap = true)
public class ExecuteEntityImpl extends BaseEntity implements ExecuteEntity {

    protected Long flowInstId;

    protected Long definitionId;

    protected Long rootFlowInstId;

    protected Long parentId;

    protected String status;

    protected Date startTime;

    protected Date endTime;

    /**
     * 当前活动id
     */
    protected Long activityId;

    /**
     * 设计时的id
     */
    protected String activityCodeId;

    /**
     * 流程定义id
     */

    /**
     * 持久化参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    protected Map<String,Object> variables;
}
