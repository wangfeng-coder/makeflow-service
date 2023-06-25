package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.workflow.dao.ActivityDao;
import com.makeid.makeflow.workflow.dao.impl.BaseDaoImpl;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.impl.ActivityEntityImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Repository
public class ActivityDaoImpl extends BaseDaoImpl<ActivityEntity> implements ActivityDao {

    @Override
    public ActivityEntity create() {
        ActivityEntityImpl activityEntity = new ActivityEntityImpl();
        fillBasicProperty(activityEntity);
        return activityEntity;
    }


    @Override
    public List<ActivityEntity> findByIds(List<String> activityIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(activityIds));
        return mongoTemplate.find(query,ActivityEntity.class);
    }
}
