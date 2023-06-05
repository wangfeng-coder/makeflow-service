package com.makeid.makeflow.basic.entity;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface Entity {

    String getId();

    void setId(String id);

    Date getCreateTime();

    void setCreateTime(Date date);

    void setUpdateTime(Date date);

    Date getUpdateTime();

    String getCreator();

    void setCreator(String creator);

    boolean isDelete();

    void setDelete(boolean delete);



}
