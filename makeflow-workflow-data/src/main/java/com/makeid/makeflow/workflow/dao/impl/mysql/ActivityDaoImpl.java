package com.makeid.makeflow.workflow.dao.impl.mysql;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mysql.BaseDaoImpl;
import com.makeid.makeflow.workflow.dao.ActivityDao;
import com.makeid.makeflow.workflow.dao.impl.mysql.mapper.ActivityMapper;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.impl.ActivityEntityImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Dao
public class ActivityDaoImpl extends BaseDaoImpl<ActivityEntityImpl,ActivityMapper> implements ActivityDao<ActivityEntityImpl> {

    @Resource
    private ActivityMapper mapper;

    @Override
    public ActivityEntityImpl create() {
        ActivityEntityImpl activityEntity = new ActivityEntityImpl();
        fillBasicProperty(activityEntity);
        return activityEntity;
    }


    @Override
    public List<ActivityEntityImpl> findByIds(List<String> activityIds) {
        return mapper.selectBatchIds(activityIds);
    }

    @Override
    public List<ActivityEntityImpl> findByFlowInstId(String flowInstId) {
        List<ActivityEntityImpl> activityEntities = mapper.selectList(Wrappers.lambdaQuery(ActivityEntityImpl.class)
                .eq(ActivityEntity::getFlowInstId, flowInstId));
        return activityEntities;
    }

    @Override
    protected ActivityMapper getMapper() {
        return mapper;
    }
}
