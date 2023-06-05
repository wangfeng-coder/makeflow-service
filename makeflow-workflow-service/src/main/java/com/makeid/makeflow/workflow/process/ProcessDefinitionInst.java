package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.basic.utils.SpringContextUtils;
import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.constants.ErrCodeEnum;
import com.makeid.makeflow.workflow.constants.ExecuteStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.runtime.IdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
public class ProcessDefinitionInst extends ProcessElementInst implements PvmProcessDefinition{

    private String templateId;

    private String name;

    private FlowProcessTemplate flowProcessTemplate;

    private StartActivityInst initial;

    private IdGenerator idGenerator;

    public static ProcessDefinitionInst createProcessDefinitionInst(FlowProcessTemplate flowProcessTemplate) {
        if (Objects.isNull(flowProcessTemplate)) {
            throw new EngineException(ErrCodeEnum.TEMPLATE_NULL);
        }
        ProcessDefinitionInst flowProcessDefinition = new ProcessDefinitionInst();
        flowProcessDefinition.setName(flowProcessTemplate.getName());
        flowProcessDefinition.setTemplateId(flowProcessTemplate.getFlowTemplateId());
        flowProcessDefinition.setFlowProcessTemplate(flowProcessTemplate);
        flowProcessDefinition.setIdGenerator(SpringContextUtils.getBean(IdGenerator.class));
        return flowProcessDefinition;
    }

    public static ProcessDefinitionInst createProcessDefinitionInst(String flowProcessTemplateId) {
        FlowProcessTemplate flowProcessTemplate = Context
                .getGlobalProcessEngineConfiguration().getFlowProcessDefinitionService().getFlowProcessDefinition(
                        flowProcessTemplateId);
        return createProcessDefinitionInst(flowProcessTemplate);
    }

    @Override
    public ProcessInstanceExecution createProcessInstanceExecution() {
        ProcessInstanceExecution processInstance = new ProcessInstanceExecution();
        processInstance.setId(idGenerator.getNextId());
        processInstance.setIdGenerator(idGenerator);
        processInstance.setProcessDefinitionInst(this);
        processInstance.setStartTime(new Date());
        processInstance.setStatus(ExecuteStatusEnum.ACTIVE.status);
        processInstance.setCurrentElement(flowProcessTemplate.findInitial());
        processInstance.initData();
        return processInstance;
    }

    public StartActivityInst getInitial() {
        return findInitial();
    }

    public StartActivityInst findInitial() {
        if(Objects.nonNull(initial)) {
            return initial;
        }
        StartActivity initial = flowProcessTemplate.findInitial();
        StartActivityInst startActivityInst = StartActivityInst.transfer(initial);
        this.initial = startActivityInst;
        return startActivityInst;
    }

}
