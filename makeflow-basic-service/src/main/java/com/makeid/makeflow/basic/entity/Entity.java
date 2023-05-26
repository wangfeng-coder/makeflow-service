package com.makeid.makeflow.basic.entity;

import java.io.Serializable;
import java.util.Date;

/**
*@program makeflow-service
*@description 数据库通用实体类
*@author feng_wf
*@create 2023-05-19
*/
public class Entity implements Serializable {

    private String id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者id
     */
    private String creator;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新者id
     */
    private String updator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }
}
