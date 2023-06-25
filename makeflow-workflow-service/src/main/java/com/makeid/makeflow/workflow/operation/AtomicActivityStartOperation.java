package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public class AtomicActivityStartOperation extends AbstractEventAtomicOperation<ProcessInstanceExecution> {

    @Override
    public void doExecute(ProcessInstanceExecution execution) {
        ActivityImpl activityInst = execution.findActivityInst();
        activityInst.create(execution);
        //更新活动状态
        activityInst.start();
        //保存到数据库
        activityInst.save();

        //设置activity id到实列
        execution.setActivityId(activityInst.getId());
        //执行活动
        execution.performOperation(AtomicOperations.activity_execute);
    }
}
