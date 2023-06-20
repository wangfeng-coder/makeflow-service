package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.template.flow.model.settings.ApprovalSettings;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.dao.TaskDao;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.task.PersonHolderCollector;
import com.makeid.makeflow.workflow.task.PersonHolderCollectors;
import com.makeid.makeflow.workflow.task.TaskTypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
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
            TaskEntity taskEntity = taskDao.create(Context.getUserId());
            taskEntity.setHandler(handler);
            taskEntity.setTaskType(TaskTypeMap.map(activityType));
            taskEntity.setActivityCodeId(activity.getCodeId());
            taskEntity.setActivityId(activity.getId());
            taskEntity.setName(activity.getName());
            taskEntity.setFlowInstId(activity.getFlowInstId());
            return taskEntity;
        }).collect(Collectors.toList());
    }

    public List<TaskEntity> completeTasks(List<TaskEntity> taskEntities) {
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setStatus(TaskStatusEnum.DONE.status);
            taskEntity.setCompleteTime(new Date());
        }
        return taskEntities;
    }

    public void save(List<TaskEntity> tasks) {
        taskDao.save(tasks);
    }
}
