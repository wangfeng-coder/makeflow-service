package com.makeid.makeflow.workflow.dao.impl.mysql;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mysql.BaseDaoImpl;
import com.makeid.makeflow.basic.entity.Entity;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.dao.TaskDao;
import com.makeid.makeflow.workflow.dao.impl.mysql.mapper.TaskMapper;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.entity.impl.TaskEntityImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-17
 */
@Dao
public class TaskDaoImpl extends BaseDaoImpl<TaskEntityImpl, TaskMapper> implements TaskDao<TaskEntityImpl> {

    @Resource
    private TaskMapper mapper;
    @Override
    public TaskEntityImpl create() {
        TaskEntityImpl taskEntity = new TaskEntityImpl();
        return taskEntity;
    }

    @Override
    public List<? extends TaskEntity> findByIds(List<String> taskIds) {
        return mapper.selectBatchIds(taskIds);
    }

    @Override
    public List<? extends TaskEntity> findByActivityInstId(String activityInstId) {
        return mapper.selectList(Wrappers.lambdaQuery(TaskEntityImpl.class)
                .eq(TaskEntityImpl::getActivityId,activityInstId));
    }

    @Override
    public void cancelOtherTask(String activityId,String id) {
        mapper.update(null, Wrappers.lambdaUpdate(TaskEntityImpl.class)
                .eq(TaskEntityImpl::getActivityId,activityId)
                .ne(Entity::getId,id)
                .set(TaskEntityImpl::getStatus, TaskStatusEnum.CANCEL.status));
    }

    @Override
    public List<? extends TaskEntity> findTaskByHandler(String handler) {
        return mapper.selectList(Wrappers.lambdaQuery(TaskEntityImpl.class)
                .eq(TaskEntityImpl::getHandler,handler));
    }

    @Override
    public List<? extends TaskEntity> findByFlowInstId(String flowInstId) {
        return mapper.selectList(Wrappers.lambdaQuery(TaskEntityImpl.class)
                .eq(TaskEntityImpl::getFlowInstId,flowInstId));
    }

    @Override
    public List<? extends TaskEntity> findFlowInstIdHandlerStatus(String flowInstId, String handler, String status) {
        return mapper.selectList(Wrappers.lambdaQuery(TaskEntityImpl.class).eq(TaskEntityImpl::getFlowInstId,flowInstId)
                .eq(TaskEntityImpl::getHandler,handler)
                .eq(TaskEntityImpl::getStatus,status));
    }

    @Override
    protected TaskMapper getMapper() {
        return mapper;
    }
}
