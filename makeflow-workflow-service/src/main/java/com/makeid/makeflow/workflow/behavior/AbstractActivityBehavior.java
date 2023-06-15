package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class AbstractActivityBehavior implements ActivityBehavior{

    protected final JumpActivityBehavior jumpActivityBehavior = new JumpActivityBehavior();


    @Override
    public void execute(ActivityPvmExecution execution) {
        doExecute(execution);
    }

    protected void doExecute(ActivityPvmExecution execution) {
        leave(execution);
    }

    protected void leave(ActivityPvmExecution execution) {
        jumpActivityBehavior.performOutgoingBehavior(execution);
    }

}
