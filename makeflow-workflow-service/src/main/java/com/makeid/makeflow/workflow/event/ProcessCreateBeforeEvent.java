package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.workflow.process.PvmProcessDefinition;
import lombok.Getter;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-26
 */
@Getter
public class ProcessCreateBeforeEvent implements Event{

    protected DelegateProcessDefinition delegateProcessDefinition;
}
