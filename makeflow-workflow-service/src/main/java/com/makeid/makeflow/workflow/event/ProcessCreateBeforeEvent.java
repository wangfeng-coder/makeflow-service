package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.delegate.DelegateExecuteReader;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.PvmProcessDefinition;
import com.makeid.makeflow.workflow.process.ScopeImpl;
import lombok.Getter;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-26
 */
@Getter
public class ProcessCreateBeforeEvent extends AbstractEvent<DelegateExecuteReader>{
    public ProcessCreateBeforeEvent(DelegateExecuteReader data) {
        super(EventType.PROCESS_CREATED, data);
    }

    public DelegateExecuteReader getDelegateExecuteReader(){
        return  getData();
    }


}
