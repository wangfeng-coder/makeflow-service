package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateTaskReader;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public class TaskDoneEvent extends AbstractEvent<List<? extends DelegateTaskReader>>{


    public TaskDoneEvent(List<? extends DelegateTaskReader> data) {
        super(EventType.TASK_DONE, data);
    }
}
