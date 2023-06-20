package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.dao.TaskDao;
import com.makeid.makeflow.workflow.dao.impl.BaseDaoImpl;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.entity.impl.TaskEntityImpl;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Repository
public class TaskDaoImpl extends BaseDaoImpl<TaskEntity> implements TaskDao {
    @Override
    public TaskEntity create() {
        TaskEntityImpl taskEntity = new TaskEntityImpl();
        taskEntity.setCreateTime(new Date());
        taskEntity.setStatus(TaskStatusEnum.NOT_INIT.status);
        taskEntity.setUpdateTime(new Date());
        return taskEntity;
    }

    @Override
    public TaskEntity create(String creator) {
        TaskEntity taskEntity = create();
        taskEntity.setCreator(creator);
        taskEntity.setUpdator(creator);
        return taskEntity;
    }
}
