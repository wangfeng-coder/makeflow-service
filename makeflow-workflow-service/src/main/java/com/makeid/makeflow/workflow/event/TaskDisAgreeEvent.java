package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateTaskReader;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-09
 */
public class TaskDisAgreeEvent extends AbstractEvent<List<? extends DelegateTaskReader>>{
    public TaskDisAgreeEvent(List<? extends DelegateTaskReader> data) {
        super(EventType.TASK_DISAGREE, data);
    }
}
