package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class AtomicActivityEndOperation extends AbstractEventAtomicOperation<ProcessInstanceExecution> {
    @Override
    public void doExecute(ProcessInstanceExecution execution) {
        ActivityImpl currentActivityInst = execution.findActivityInst();
        currentActivityInst.end();
        currentActivityInst.save();
        if (currentActivityInst.isEndActivity()) {
            //结束 流程
            execution.performOperation(AtomicOperations.process_end);
        }else {
            //结束活动
            execution.performOperation(AtomicOperations.transition_take);
        }
    }
}
