package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;

/**
*@program makeflow-service
*@description 流程启动操作
*@author feng_wf
*@create 2023-06-05
*/
public class AtomicProcessStartOperation implements AtomicOperation<ProcessInstanceExecution>{
    @Override
    public void execute(ProcessInstanceExecution execution) {
        //正常情况下开始节点已经在在前面设置了,直接执行活动开始
        //更新流程状态、执行状态
        execution.runFlowInst();
        execution.saveExecuteEntity();
        execution.performOperation(AtomicOperations.activity_start);
    }
}
