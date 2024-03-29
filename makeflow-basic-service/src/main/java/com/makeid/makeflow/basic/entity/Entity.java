package com.makeid.makeflow.basic.entity;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface Entity {

    Long getId();

    void setId(Long id);

    Date getCreateTime();

    void setCreateTime(Date date);

    void setUpdateTime(Date date);

    Date getUpdateTime();

    String getCreator();

    void setCreator(String creator);


    String getUpdator();

    void setUpdator(String updator);

    public boolean isDelFlag();

    public void setDelFlag(boolean delFlag);



}
