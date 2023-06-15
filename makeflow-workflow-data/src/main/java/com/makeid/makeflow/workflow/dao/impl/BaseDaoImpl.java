package com.makeid.makeflow.workflow.dao.impl;

import com.makeid.makeflow.workflow.dao.BaseDao;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
public class BaseDaoImpl<T> implements BaseDao<T> {
    @Resource
    protected MongoTemplate mongoTemplate;


    private Class<T> getEntityClass() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }

    @Override
    public void save(T t) {
        mongoTemplate.save(t);
    }

    @Override
    public T create() {
        return null;
    }

    @Override
    public T findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,getEntityClass());
    }
}
