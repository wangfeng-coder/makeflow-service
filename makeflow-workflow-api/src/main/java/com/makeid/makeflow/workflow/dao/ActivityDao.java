package com.makeid.makeflow.workflow.dao;

import com.makeid.makeflow.workflow.entity.ActivityEntity;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-08
 */
public interface ActivityDao extends BaseDao<ActivityEntity>{
    List<ActivityEntity> findByIds(List<String> activityIds);


}
