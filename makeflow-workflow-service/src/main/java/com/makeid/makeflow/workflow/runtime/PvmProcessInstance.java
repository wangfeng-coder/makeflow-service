package com.makeid.makeflow.workflow.runtime;

import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.process.PvmExecution;



/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-01
 */
public interface PvmProcessInstance extends PvmExecution {

    Long getProcessDefinitionId();


    Long rootProcessInstanceId();

    boolean suspended();

    void endProcessInstance(FlowStatusEnum flowStatusEnum);

}
