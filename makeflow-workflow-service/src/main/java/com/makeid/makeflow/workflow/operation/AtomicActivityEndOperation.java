package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.process.ProcessInstancePvmExecution;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class AtomicActivityEndOperation extends AbstractEventAtomicOperation<ProcessInstancePvmExecution> {
    @Override
    public void doExecute(ProcessInstancePvmExecution execution) {
        ActivityImpl currentActivityInst = execution.findActivityInst();
        currentActivityInst.setStatus(ActivityStatusEnum.COMPLETE.status);
        currentActivityInst.setEndTime(new Date());
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
