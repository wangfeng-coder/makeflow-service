package com.makeid.makeflow.workflow.delegate;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public interface BasicDataReader {

    String getId();

    Date getCreateTime();



    Date getUpdateTime();

    String getCreator();



    String getUpdator();



}
