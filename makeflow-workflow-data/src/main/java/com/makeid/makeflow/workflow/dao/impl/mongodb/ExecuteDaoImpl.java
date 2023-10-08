package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mongo.BaseDaoImpl;
import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.impl.ExecuteEntityImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Dao
public class ExecuteDaoImpl extends BaseDaoImpl<ExecuteEntity> implements ExecuteEntityDao<ExecuteEntity> {

    @Override
    public List<ExecuteEntity> findByParentId(Long parentId) {
        //为实现
         throw new RuntimeException();
    }

    @Override
    public List<ExecuteEntity> findByIds(List<Long> executionIds) {
        Query query = Query.query(Criteria.where("_id").in(executionIds));
        return mongoTemplate.find(query,ExecuteEntity.class);
    }

    @Override
    public ExecuteEntity doCreate() {
        ExecuteEntityImpl executeEntity = new ExecuteEntityImpl();
        return executeEntity;
    }

    @Override
    public List<? extends ExecuteEntity> findByFlowInstId(Long id) {
        Query query = Query.query(Criteria.where("flowInstId").is(id));
        return mongoTemplate.find(query,ExecuteEntity.class);
    }
}
