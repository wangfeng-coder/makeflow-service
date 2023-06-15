package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.ProcessInstancePvmExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class AtomicOperationTransitionTake extends AbstractEventAtomicOperation<ProcessInstancePvmExecution> {
    @Override
    public void doExecute(ProcessInstancePvmExecution execution) {
        execution.take();
        //继续运转
        execution.performOperation(AtomicOperations.activity_start);
    }
}
