package com.makeid.makeflow.workflow.dao.impl;

import com.makeid.makeflow.workflow.dao.BaseDao;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
public class BaseDaoImpl<T> implements BaseDao<T> {
    @Resource
    protected MongoTemplate mongoTemplate;
    @Override
    public void save(T t) {
        mongoTemplate.save(t);
    }
}
