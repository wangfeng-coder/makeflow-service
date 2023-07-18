package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateTaskReader;
import com.makeid.makeflow.workflow.entity.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public class TaskRunningAfterEvent implements Event{

    private List<TaskEntity> taskEntityList;

    public TaskRunningAfterEvent(List<TaskEntity> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }


    public List<DelegateTaskReader> getTasks(){
        return taskEntityList.stream()
                .filter(t-> t instanceof DelegateTaskReader)
                .map(t->(DelegateTaskReader)t)
                .collect(Collectors.toList());
    }
}
