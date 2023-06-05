package com.makeid.makeflow.workflow.entity;

import com.makeid.makeflow.basic.entity.Entity;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public interface ActivityEntity extends Entity {

    String getName();

    void setName(String name);

    String getActivityType();

    void setActivityType(String type);

    void setActivityCodeId(String activityCodeId);

    Date getStartDate();

    void setStartDate(Date date);

    Date getEndDate();

    void setEndDate(Date date);

    int getStatus();

    void setStatus(int status);


}
