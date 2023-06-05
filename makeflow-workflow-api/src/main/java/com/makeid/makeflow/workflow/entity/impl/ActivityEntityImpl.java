package com.makeid.makeflow.workflow.entity.impl;

import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
@Setter
@Getter
public class ActivityEntityImpl extends BaseEntity implements ActivityEntity {

    protected String name;


    protected String activityType;


    protected String activityCodeId;

    protected Date startDate;

    protected Date endDate;



}
