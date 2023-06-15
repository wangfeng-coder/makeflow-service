package com.makeid.makeflow.workflow.entity.impl;

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
@Document(collection = "makeflow_task")
public class TaskEntityImpl extends BaseEntity implements TaskEntity {
    
    protected String name;
    
    protected String handler;

    protected String flowInstId;

    protected String activityId;

    protected String activityCodeId;

    protected int status;

    protected Date completeTime;

    protected int taskType;


}
