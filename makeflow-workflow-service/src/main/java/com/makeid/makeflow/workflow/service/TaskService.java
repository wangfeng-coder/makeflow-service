package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.template.flow.model.settings.ApprovalSettings;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskTypeEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.dao.TaskDao;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.task.PersonHolderCollector;
import com.makeid.makeflow.workflow.task.PersonHolderCollectors;
import com.makeid.makeflow.workflow.task.TaskTypeMap;
import com.makeid.makeflow.workflow.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Service
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private PersonHolderCollectors personHolderCollectors;


    public List<TaskEntity> createTask(List<PeopleHolder> participants, ApprovalSettings approvalSettings, PvmActivity pvmActivity) {
        List<String> userIds = personHolderCollectors.collect(participants, pvmActivity);
        return createTask(userIds, pvmActivity);
    }

    public List<TaskEntity> createTask(List<String> handlers, PvmActivity activity) {
        String activityType = activity.getActivityType();
        return handlers.stream().map(handler -> {
            TaskEntity taskEntity = (TaskEntity) taskDao.create(Context.getUserId());
            taskEntity.setHandler(handler);
            taskEntity.setTaskType(TaskTypeMap.map(activityType));
            taskEntity.setActivityCodeId(activity.getCodeId());
            taskEntity.setActivityId(activity.getId());
            taskEntity.setName(activity.getName());
            taskEntity.setFlowInstId(activity.getFlowInstId());
            return taskEntity;
        }).collect(Collectors.toList());
    }

    public List<TaskEntity> completeTasks(List<TaskEntity> taskEntities,String status) {
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setStatus(status);
            taskEntity.setCompleteTime(new Date());
        }
        return taskEntities;
    }

    public void save(List<TaskEntity> tasks) {
        taskDao.save(tasks);
    }

    public void save(TaskEntity taskEntity) {
        taskDao.save(taskEntity);
    }

    public List<TaskEntity> findTaskByIds(List<String> taskIds) {
        return taskDao.findByIds(taskIds);
    }

    /**
     * 任务是否都完成或者可忽略
     *
     * @param activityInstId
     * @return
     */
    public boolean isCompleteSkipTask(String activityInstId) {
        List<TaskEntity> taskEntities = taskDao.findByActivityInstId(activityInstId);
        //存在doing状态 就不能过
        boolean notPass = Optional.ofNullable(taskEntities).orElse(Collections.emptyList())
                .stream()
                .anyMatch(taskEntity -> TaskStatusEnum.DOING.status.equals(taskEntity.getStatus()));
        return !notPass;
    }

    public void cancelOtherTask(String activityId,String id) {
        taskDao.cancelOtherTask(activityId,id);
    }

    public List<TaskEntity> findTaskByHandler(String handler) {
       return taskDao.findTaskByHandler(handler);
    }

    public TaskEntity findTaskById(String taskId) {
        return (TaskEntity) taskDao.findById(taskId);
    }

    public List<TaskEntity> findByFlowInstId(String flowInstId) {
        return taskDao.findByFlowInstId(flowInstId);
    }

    public List<TaskEntity> findFlowInstIdHandlerStatus(String flowInstId, String handler, String status) {
        return taskDao.findFlowInstIdHandlerStatus(flowInstId,handler,status);
    }
}
