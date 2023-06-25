package com.makeid.makeflow.workflow.dao;

import com.makeid.makeflow.workflow.entity.TaskEntity;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
public interface TaskDao extends BaseDao<TaskEntity> {
    List<TaskEntity> findByIds(List<String> taskIds);

    List<TaskEntity> findByActivityInstId(String activityInstId);

    void cancelOtherTask(List<TaskEntity> taskEntities);

    List<TaskEntity> findTaskByHandler(String handler);

}
