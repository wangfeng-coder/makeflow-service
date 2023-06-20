package com.makeid.makeflow.workflow.dao.impl;

import com.makeid.makeflow.workflow.dao.BaseDao;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

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
    public void save(List<T> collections) {
        mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED,getEntityClass())
                .insert(collections)
                .execute();
    }

    @Override
    public T create() {
        try {
            Constructor<T> tConstructor = ReflectionUtils.accessibleConstructor(getEntityClass());
            return tConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T create(String creator) {
        return create();
    }


    @Override
    public T findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,getEntityClass());
    }
}
