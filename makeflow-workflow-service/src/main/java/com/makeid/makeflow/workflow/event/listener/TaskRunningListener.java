package com.makeid.makeflow.workflow.event.listener;

import com.makeid.makeflow.workflow.delegate.DelegateTaskReader;
import com.makeid.makeflow.workflow.event.FlowEventListener;
import com.makeid.makeflow.workflow.event.TaskRunningEvent;
import com.makeid.makeflow.workflow.eventbus.FlowSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
@Component
@Slf4j
public class TaskRunningListener implements FlowEventListener {

    @FlowSubscribe(sync = false)
    public void sendToDo(TaskRunningEvent taskRunningEvent) {
        List<? extends DelegateTaskReader> data = taskRunningEvent.getData();
        log.info("taskRunning sendToDo ");
    }
}

