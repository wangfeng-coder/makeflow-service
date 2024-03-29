package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateExecuteReader;
import com.makeid.makeflow.workflow.delegate.DelegateScopeWriter;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-06
 */
public class ProcessStartedEvent extends AbstractEvent<DelegateExecuteReader>{


    public ProcessStartedEvent(DelegateExecuteReader data) {
        super(EventType.PROCESS_STARTED, data);
    }

}
