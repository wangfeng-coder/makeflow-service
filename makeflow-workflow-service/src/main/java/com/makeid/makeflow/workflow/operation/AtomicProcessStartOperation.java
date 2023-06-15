package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.ProcessInstancePvmExecution;

/**
*@program makeflow-service
*@description 流程启动操作
*@author feng_wf
*@create 2023-06-05
*/
public class AtomicProcessStartOperation implements AtomicOperation<ProcessInstancePvmExecution>{
    @Override
    public void execute(ProcessInstancePvmExecution execution) {
        //正常情况下开始节点已经在在前面设置了,直接执行活动开始
        execution.performOperation(AtomicOperations.activity_start);
    }
}
