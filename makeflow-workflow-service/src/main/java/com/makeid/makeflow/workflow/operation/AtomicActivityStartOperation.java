package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.BaseActivityInst;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public class AtomicActivityStartOperation implements AtomicOperation<ProcessInstanceExecution> {
    @Override
    public void execute(ProcessInstanceExecution execution) {
        BaseActivityInst activityInst = execution.findActivityInst();

    }
}
