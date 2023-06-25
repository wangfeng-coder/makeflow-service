package com.makeid.makeflow.workflow.dao.impl;

import com.makeid.makeflow.basic.entity.BaseEntity;
import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.workflow.dao.BaseDao;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
public class BaseDaoImpl<T extends Entity> implements BaseDao<T> {
    @Resource
    protected MongoTemplate mongoTemplate;


    protected Class<T> getEntityClass() {
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
        if (CollectionUtils.isEmpty(collections)) {
            return;
        }
        List<T> inserts = new ArrayList<>();
        List<T> updates = new ArrayList<>();
        for (T collection : collections) {
            String id = collection.getId();
            if(!StringUtils.hasText(id)) {
                inserts.add(collection);
            } else {
                updates.add(collection);
            }
        }
        if (!CollectionUtils.isEmpty(inserts)) {
            mongoTemplate.insertAll(inserts);
        }
        if(!CollectionUtils.isEmpty(updates)) {
            //批量更新
            BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, getEntityClass());
            for (T updateEntity : updates) {
                String id = updateEntity.getId();
                Query query = Query.query(Criteria.where("_id").is(id));
                bulkOperations.replaceOne(query,updateEntity);
            }
            bulkOperations.execute();
        }
    }

    protected void fillBasicProperty(T t) {
        t.setUpdateTime(new Date());
        t.setCreateTime(new Date());
    }


    @Override
    public T create() {
        try {
            Constructor<T> tConstructor = ReflectionUtils.accessibleConstructor(getEntityClass());
            T t = tConstructor.newInstance();
            fillBasicProperty(t);
            return t;
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
        T t = create();
        t.setCreator(creator);
        t.setUpdator(creator);
        return t;
    }


    @Override
    public T findById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,getEntityClass());
    }
}
