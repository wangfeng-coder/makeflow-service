package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.dao.TaskDao;
import com.makeid.makeflow.workflow.dao.impl.BaseDaoImpl;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.entity.impl.TaskEntityImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        fillBasicProperty(taskEntity);
        taskEntity.setStatus(TaskStatusEnum.NOT_INIT.status);
        return taskEntity;
    }
    @Override
    public List<TaskEntity> findByIds(List<String> taskIds) {
        Query query = Query.query(Criteria.where("_id").in(taskIds));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, TaskEntity.class);
        return taskEntities;
    }

    @Override
    public List<TaskEntity> findByActivityInstId(String activityInstId) {
        Query activityId = Query.query(Criteria.where("activityId").is(activityInstId))
                .addCriteria(Criteria.where("delete").is(false));
        return mongoTemplate.find(activityId,TaskEntity.class);
    }

    @Override
    public void cancelOtherTask(List<TaskEntity> taskEntities) {
        List<String> activityIds = taskEntities.stream().map(TaskEntity::getActivityId).collect(Collectors.toList());
        List<String> ids = taskEntities.stream().map(TaskEntity::getId).collect(Collectors.toList());
        Query query = Query.query(Criteria.where("activityId").in(activityIds))
                .addCriteria(Criteria.where("_id").nin(ids))
                .addCriteria(Criteria.where("delete").is(false));
        Update update = new Update();
        update.set("status",TaskStatusEnum.DISAGREE.status);
        mongoTemplate.updateMulti(query,update,TaskEntity.class);
    }

    @Override
    public List<TaskEntity> findTaskByHandler(String handler) {
        Query query = Query.query(Criteria.where("handler").is(handler))
                .addCriteria(Criteria.where("status").is(TaskStatusEnum.DOING.status))
                .addCriteria(Criteria.where("delete").is(false));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, getEntityClass());
        return taskEntities;
    }
}
