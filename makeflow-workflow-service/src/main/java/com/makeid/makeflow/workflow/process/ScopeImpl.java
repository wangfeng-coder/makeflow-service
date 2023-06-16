package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.process.activity.CoreActivity;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 领域信息（全局参数、流程定义）的设置获取
 * @create 2023-06-12
 */
public abstract class ScopeImpl extends CoreActivity implements PvmScope{


    protected HashMap<String,Object> variables;

    public ScopeImpl() {
    }

    public ScopeImpl(ProcessDefinitionImpl processDefinition) {
        this.processDefinition = processDefinition;
    }

    public ScopeImpl(String codeId, ProcessDefinitionImpl processDefinition) {
        this.codeId = codeId;
        this.processDefinition = processDefinition;
    }

    @Override
    public String getCodeId() {
        return this.codeId;
    }


    @Override
    public Map<String, Object> getVariables() {
        return variables;
    }

    @Override
    public void addVariables(Map<String,Object> variables) {
        if (Objects.isNull(this.variables)) {
            this.variables = new HashMap<>();
        }
        variables.putAll(variables);
    }

    @Override
    public boolean isScope() {
        return false;
    }

    @Override
    public TransitionImpl findTransition(String codeId) {

        return null;
    }

    @Override
    public ActivityImpl findActivity(String codeId) {


        return null;
    }

    @Override
    public FlowNode findFlowNode(String codeId) {
        return  this.processDefinition.findFlowNode(codeId);
    }
}
