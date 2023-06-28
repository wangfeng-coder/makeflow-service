package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public abstract class AbstractActivityBehavior implements ActivityBehavior{

    protected final JumpActivityBehavior jumpActivityBehavior = new JumpActivityBehavior();


    @Override
    public void execute(ActivityPvmExecution execution) {
        doExecute(execution);
    }

    @Override
    public void completedTask(ActivityPvmExecution execution) {
        leave(execution);
    }

    protected void doExecute(ActivityPvmExecution execution) {
        leave(execution);
    }

    protected void leave(ActivityPvmExecution execution) {
        jumpActivityBehavior.performOutgoingBehavior(execution);
    }

}
