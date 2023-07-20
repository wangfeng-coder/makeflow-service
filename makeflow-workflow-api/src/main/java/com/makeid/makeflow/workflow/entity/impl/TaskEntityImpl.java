package com.makeid.makeflow.workflow.entity.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-07
 */
@Getter
@Setter
@TableName("makeflow_task")
@Document(collection = "makeflow_task")
public class TaskEntityImpl extends BaseEntity implements TaskEntity {
    
    protected String name;
    
    protected String handler;

    protected Long flowInstId;

    protected Long activityId;

    protected String activityCodeId;

    protected String status;

    protected Date completeTime;

    protected String taskType;

    protected String opinion;


}
