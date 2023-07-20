package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mongo.BaseDaoImpl;
import com.makeid.makeflow.workflow.dao.ActivityDao;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.impl.ActivityEntityImpl;
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
public class ActivityDaoImpl extends BaseDaoImpl<ActivityEntity> implements ActivityDao<ActivityEntity> {

    @Override
    public ActivityEntity doCreate() {
        ActivityEntityImpl activityEntity = new ActivityEntityImpl();
        return activityEntity;
    }


    @Override
    public List<ActivityEntity> findByIds(List<Long> activityIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(activityIds));
        return mongoTemplate.find(query,ActivityEntity.class);
    }

    @Override
    public List<ActivityEntity> findByFlowInstId(Long flowInstId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("flowInstId").in(flowInstId));
        return mongoTemplate.find(query,ActivityEntity.class);
    }
}
