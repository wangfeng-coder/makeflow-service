package com.makeid.makeflow.workflow.operation;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public interface AtomicOperations {

    /**
     * 流程开始操作
     */
    AtomicOperation process_start = new AtomicProcessStartOperation();

    /**
     * 节点开始操作
     */
    AtomicOperation activity_start = new AtomicActivityStartOperation();

    /**
     * 对应节点执行对应行为
     */
    AtomicOperation activity_execute = new AtomicActivityExecuteOperation();

    /**
     * 节点结束操作
     */
    AtomicOperation ACTIVITY_END = new AtomicActivityEndOperation();

    /**
     * 流程结束操作
     */
    AtomicOperation process_end = new AtomicProcessEndOperation() ;

    /**
     * 节点流转操作
     */
    AtomicOperation transition_take = new AtomicOperationTransitionTake();
}
