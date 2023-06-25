package com.makeid.makeflow.workflow.process.difinition;

import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.base.Element;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.PvmProcessDefinition;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-12
 */
public class ProcessDefinitionImpl  implements PvmProcessDefinition {

    protected FlowProcessTemplate flowProcessTemplate;

    protected ActivityImpl initial;

    public ProcessDefinitionImpl(FlowProcessTemplate flowProcessTemplate) {
        this.flowProcessTemplate = flowProcessTemplate;
    }


    public PvmProcessInstance createProcessInstanceExecution(Map<String,Object> variables) {
        ProcessInstanceExecution processInstance = new ProcessInstanceExecution(this);
        StartActivity startActivity = flowProcessTemplate.findInitial();
        processInstance.setStartCodeId(startActivity.getCodeId());
        processInstance.addVariables(variables);
        return processInstance;
    }




    @Override
    public FlowProcessTemplate findFlowProcessTemplate() {
        Assert.notNull(flowProcessTemplate);
        return flowProcessTemplate;
    }


    public ActivityImpl findInitial() {
        if(Objects.nonNull(initial)) {
            return initial;
        }
        StartActivity initial = flowProcessTemplate.findInitial();
        ActivityImpl activity = new ActivityImpl(initial.getCodeId(), this);
        this.initial = activity;
        return activity;
    }

    @Override
    public FlowNode findFlowNode(String codeId) {
        List<Element> elements = findFlowProcessTemplate().getElements();
        for (Element element : elements) {
            if(element.getCodeId().equals(codeId)) {
                return (FlowNode) element;
            }
        }
        throw new EngineException("未找到节点");
    }

    public String getCodeId() {
        return flowProcessTemplate.getFlowTemplateCodeId();
    }


    public String getName() {
        return flowProcessTemplate.getName();
    }

    public String getDefinitionId() {
        return flowProcessTemplate.getFlowTemplateId();
    }

}
