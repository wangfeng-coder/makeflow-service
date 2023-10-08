package com.makeid.makeflow.workflow.dao;

import com.makeid.makeflow.basic.dao.BaseDao;
import com.makeid.makeflow.workflow.entity.ActivityEntity;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-08
 */
public interface ActivityDao<T extends ActivityEntity> extends BaseDao<T> {
    List<T> findByIds(List<Long> activityIds);

    List<T> findByFlowInstId(Long flowInstId);


    List<T> findByFlowInstIdActivityType(Long flowInstId, String activityType);
}
