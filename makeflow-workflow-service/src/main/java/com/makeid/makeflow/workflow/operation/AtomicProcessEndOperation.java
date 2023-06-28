package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class AtomicProcessEndOperation extends AbstractEventAtomicOperation<ProcessInstanceExecution> {
    @Override
    public void doExecute(ProcessInstanceExecution execution) {
        execution.endProcessInstance(FlowStatusEnum.END);
        execution.end();
    }
}
