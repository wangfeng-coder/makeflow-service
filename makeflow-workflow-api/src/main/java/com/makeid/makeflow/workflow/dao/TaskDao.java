package com.makeid.makeflow.workflow.dao;

import com.makeid.makeflow.basic.dao.BaseDao;
import com.makeid.makeflow.workflow.entity.TaskEntity;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
public interface TaskDao<T extends TaskEntity> extends BaseDao<T> {
    List<? extends TaskEntity> findByIds(List<Long> taskIds);

    List<? extends TaskEntity> findByActivityInstId(Long activityInstId);

    void cancelOtherTask(Long activityId,Long id);

    List<? extends  TaskEntity> findTaskByHandler(String handler);

    List<? extends TaskEntity> findByFlowInstId(Long flowInstId);

    List<? extends TaskEntity> findFlowInstIdHandlerStatus(Long flowInstId, String handler, String status);

    void cancelAllTask(Long activityId);

    List<? extends TaskEntity> findTaskByHandler(String handler, String status);
}
