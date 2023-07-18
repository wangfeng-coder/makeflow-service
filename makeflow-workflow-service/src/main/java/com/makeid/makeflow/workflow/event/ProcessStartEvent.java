package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateScopeWriter;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public class ProcessStartEvent implements Event{

    private ProcessInstanceExecution processInstanceExecution;


    public ProcessStartEvent(ProcessInstanceExecution processInstanceExecution) {
        this.processInstanceExecution = processInstanceExecution;
    }

    public DelegateScopeWriter getScopeWriter() {
        return processInstanceExecution;
    }
}
