package com.makeid.makeflow.workflow.event;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-27
 */
public interface DelegateProcessDefinition {

    FlowProcessTemplate findFlowProcessTemplate();
}
