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
        //将开始节点codeId设置
        execution.getExecuteEntity().setActivityCodeId(execution.getStartCodeId());
        execution.runFlowInst();
        execution.saveFlowInstEntity();
        execution.saveExecuteEntity();
        execution.performOperation(AtomicOperations.activity_start);
    }
}
