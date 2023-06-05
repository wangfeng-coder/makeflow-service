package com.makeid.makeflow.workflow.runtime;

import com.makeid.makeflow.workflow.process.Execution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface ProcessInstance extends Execution {

    String getProcessDefinitionId();


    String rootProcessInstanceId();

    boolean suspended();

}
