package com.makeid.makeflow.workflow.process.difinition;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.constants.ErrCodeEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.exception.EngineException;

import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-12
 */
public class ProcessDefinitionBuilder {

    private ProcessDefinitionImpl processDefinition;

    public static ProcessDefinitionBuilder create() {
        return new ProcessDefinitionBuilder();
    }

    public  ProcessDefinitionBuilder createProcessDefinition(FlowProcessTemplate flowProcessTemplate) {
        if (Objects.isNull(flowProcessTemplate)) {
            throw new EngineException(ErrCodeEnum.TEMPLATE_NULL);
        }
        ProcessDefinitionImpl flowProcessDefinition = new ProcessDefinitionImpl(flowProcessTemplate);
        return this;
    }

    public  ProcessDefinitionBuilder createProcessDefinition(String flowProcessTemplateId) {
        FlowProcessTemplate flowProcessTemplate = Context
                .getGlobalProcessEngineConfiguration().getFlowProcessDefinitionService().getFlowProcessDefinition(
                        flowProcessTemplateId);
        return createProcessDefinition(flowProcessTemplate);
    }

    public ProcessDefinitionImpl build() {
        return processDefinition;
    }
}
