package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.process.ProcessInstancePvmExecution;

import java.util.Date;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public class AtomicActivityStartOperation extends AbstractEventAtomicOperation<ProcessInstancePvmExecution> {

    @Override
    public void doExecute(ProcessInstancePvmExecution execution) {
        ActivityImpl activityInst = execution.findActivityInst();
        activityInst.create(execution);
        //更新活动状态
        activityInst.start();
        //保存到数据库
        activityInst.save();
        //执行活动
        execution.performOperation(AtomicOperations.activity_execute);
    }
}
