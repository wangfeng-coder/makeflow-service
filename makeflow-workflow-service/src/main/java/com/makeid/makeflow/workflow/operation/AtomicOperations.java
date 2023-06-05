package com.makeid.makeflow.workflow.operation;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public interface AtomicOperations {

    AtomicOperation process_start = new AtomicProcessStartOperation();

    AtomicOperation activity_start = new AtomicActivityStartOperation();
}
