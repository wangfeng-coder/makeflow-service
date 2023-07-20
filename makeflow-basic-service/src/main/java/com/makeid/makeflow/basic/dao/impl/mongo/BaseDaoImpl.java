package com.makeid.makeflow.basic.dao.impl.mongo;

import com.makeid.makeflow.basic.dao.BaseDao;
import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.basic.utils.SnowflakeIdGenerator;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
public abstract class BaseDaoImpl<T extends Entity> implements BaseDao<T> {

    private static SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1,1);

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
            Long id = collection.getId();
            if(!Objects.isNull(id)) {
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
                Long id = updateEntity.getId();
                Query query = Query.query(Criteria.where("_id").is(id));
                bulkOperations.replaceOne(query,updateEntity);
            }
            bulkOperations.execute();
        }
    }

    protected void fillBasicProperty(T t) {
        t.setUpdateTime(new Date());
        t.setCreateTime(new Date());
        t.setId(snowflakeIdGenerator.nextId());
    }

    public T create() {
        T t = doCreate();
        fillBasicProperty(t);
        return t;
    }

    @Override
    public T create(String creator) {
        T t = create();
        t.setCreator(creator);
        t.setUpdator(creator);
        return t;
    }


    @Override
    public T findById(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findById(id,getEntityClass());
    }
}
