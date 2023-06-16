package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.workflow.dao.ActivityDao;
import com.makeid.makeflow.workflow.dao.impl.BaseDaoImpl;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.impl.ActivityEntityImpl;
import org.springframework.stereotype.Repository;

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
        return activityEntity;
    }
}
