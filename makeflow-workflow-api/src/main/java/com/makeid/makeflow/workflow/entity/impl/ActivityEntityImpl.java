package com.makeid.makeflow.workflow.entity.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
@Setter
@Getter
@TableName("makeflow_activity")
public class  ActivityEntityImpl extends BaseEntity implements ActivityEntity {

    protected String name;


    protected String activityType;


    protected String activityCodeId;

    protected Date startTime;

    protected Date endTime;

    protected String status;

    protected String flowInstId;

    protected String executionId;

    protected String definitionId;

    protected String parentId;

    /**
     * 当前节点的前一个节点
     */
    protected String preActivityId;


}
