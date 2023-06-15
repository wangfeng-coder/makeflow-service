package com.makeid.makeflow.workflow.process.difinition;

import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.base.Element;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.constants.ExecuteStatusEnum;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.process.ProcessInstancePvmExecution;
import com.makeid.makeflow.workflow.process.PvmProcessDefinition;
import com.makeid.makeflow.workflow.process.ScopeImpl;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-12
 */
public class ProcessDefinitionImpl extends ScopeImpl implements PvmProcessDefinition {

    protected FlowProcessTemplate flowProcessTemplate;

    protected ActivityImpl initial;

    public ProcessDefinitionImpl(FlowProcessTemplate flowProcessTemplate) {
        super();
        this.id = processDefinition.getId();
        this.codeId = processDefinition.getCodeId();
        this.processDefinition = this;
        this.flowProcessTemplate = flowProcessTemplate;
    }


    public PvmProcessInstance createProcessInstanceExecution() {
        ProcessInstancePvmExecution processInstance = new ProcessInstancePvmExecution(this);
        StartActivity startActivity = flowProcessTemplate.findInitial();
        processInstance.setIdGenerator(idGenerator);
        processInstance.setStartTime(new Date());
        processInstance.setStatus(ExecuteStatusEnum.ACTIVE.status);
        processInstance.setCreateTime(new Date());
        processInstance.setUpdateTime(new Date());
        processInstance.setActivityCodeId(startActivity.getCodeId());
        processInstance.setProcessDefinitionId(this.getProcessDefinition().id);
        processInstance.setProcessDefinition(this.getProcessDefinition());
        return processInstance;
    }



    public FlowNode findFlowNode(String codeId) {
        List<Element> elements = this.flowProcessTemplate.getElements();
        for (Element element : elements) {
            if(element.getCodeId().equals(codeId)) {
                return (FlowNode) element;
            }
        }
        throw new EngineException("未找到对应FlowNode");
    }

    @Override
    public FlowProcessTemplate findFlowProcessTemplate() {
        return this.processDefinition.findFlowProcessTemplate();
    }

    public ActivityImpl findInitial() {
        if(Objects.nonNull(initial)) {
            return initial;
        }
        StartActivity initial = flowProcessTemplate.findInitial();
        ActivityImpl activity = new ActivityImpl(null, initial.getCodeId(), processDefinition);
        this.initial = activity;
        return activity;
    }


    @Override
    public String getCodeId() {
        return flowProcessTemplate.getFlowTemplateCodeId();
    }
}
