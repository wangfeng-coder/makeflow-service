package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateTaskReader;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public class TaskRunningEvent extends AbstractEvent<List<? extends DelegateTaskReader>>{


    public TaskRunningEvent(List<? extends DelegateTaskReader> data) {
        super(EventType.TASK_RUNNING, data);
    }
}
