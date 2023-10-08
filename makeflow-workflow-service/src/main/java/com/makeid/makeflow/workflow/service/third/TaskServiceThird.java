package com.makeid.makeflow.workflow.service.third;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-07
 */
@Service
@AllArgsConstructor
public class TaskServiceThird {


    private final TaskService taskService;

    public List<TaskEntity> listDoingTasks(String handleId) {
       return taskService.findTaskByHandler(handleId, TaskStatusEnum.DOING.status);
    }

}
