package com.makeid.makeflow.workflow.dao.impl.mongodb;

import com.makeid.makeflow.basic.annotation.Dao;
import com.makeid.makeflow.basic.dao.impl.mongo.BaseDaoImpl;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.dao.TaskDao;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.entity.impl.TaskEntityImpl;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Dao
public class TaskDaoImpl extends BaseDaoImpl<TaskEntity> implements TaskDao<TaskEntity> {
    @Override
    public TaskEntity doCreate() {
        TaskEntityImpl taskEntity = new TaskEntityImpl();
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
                .addCriteria(Criteria.where("delFlag").is(false));
        return mongoTemplate.find(activityId,TaskEntity.class);
    }

    @Override
    public void cancelOtherTask(String activityId, String id) {
        Query query = Query.query(Criteria.where("activityId").is(activityId))
                .addCriteria(Criteria.where("_id").is(id))
                .addCriteria(Criteria.where("delFlag").is(false));
        Update update = new Update();
        update.set("status",TaskStatusEnum.CANCEL.status);
        mongoTemplate.updateMulti(query,update,TaskEntity.class);
    }

    @Override
    public List<TaskEntity> findTaskByHandler(String handler) {
        Query query = Query.query(Criteria.where("handler").is(handler))
                .addCriteria(Criteria.where("delFlag").is(false));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, getEntityClass());
        return taskEntities;
    }

    @Override
    public List<TaskEntity> findByFlowInstId(String flowInstId) {
        Query query = Query.query(Criteria.where("flowInstId").is(flowInstId))
                .addCriteria(Criteria.where("delFlag").is(false));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, getEntityClass());
        return taskEntities;
    }

    @Override
    public List<TaskEntity> findFlowInstIdHandlerStatus(String flowInstId, String handler, String status) {
        Query query = Query.query(Criteria.where("flowInstId").is(flowInstId))
                .addCriteria(Criteria.where("handler").is(handler))
                .addCriteria(Criteria.where("status").is(status))
                .addCriteria(Criteria.where("delFlag").is(false));
        List<TaskEntity> taskEntities = mongoTemplate.find(query, getEntityClass());
        return taskEntities;
    }


}
