package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.sequence.SequenceFlow;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.process.condition.UelExpressionCondition;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public class TransitionImpl extends ProcessElementImpl implements PvmTransition{

    protected Condition condition;

    protected String sourceCodeId;

    protected String targetCodeId;

    protected SequenceFlow sequenceFlow;

    protected ActivityImpl sourceActivity;

    protected ActivityImpl targetActivity;


    public TransitionImpl(ProcessDefinitionImpl processDefinition) {
        super(processDefinition);
    }

    public TransitionImpl(String codeId, ProcessDefinitionImpl processDefinition) {
        super(codeId, processDefinition);
    }

    @Override
    public boolean isActivity() {
        return false;
    }

    @Override
    public boolean isTransition() {
        return true;
    }

    @Override
    public ActivityImpl findSource() {
        if(Objects.isNull(sourceActivity)) {
            this.sourceActivity = new ActivityImpl( sourceCodeId, processDefinition);
        }
        return sourceActivity;
    }

    @Override
    public ActivityImpl findDestination() {
        if(Objects.isNull(targetActivity)) {
            this.targetActivity = new ActivityImpl(targetCodeId, processDefinition);
        }
        return targetActivity;
    }

    @Override
    public Condition findCondition() {
        return condition;
    }


    public static TransitionImpl transferFrom(SequenceFlow sequenceFlow,ProcessDefinitionImpl processDefinition) {
        TransitionImpl transition = new TransitionImpl(sequenceFlow.getCodeId(),processDefinition);
        transition.setSequenceFlow(sequenceFlow);
        transition.setSourceCodeId(sequenceFlow.getSourceCodeId());
        transition.setTargetCodeId(sequenceFlow.getTargetCodeId());
        //TODO  目前只有UEL表达式的条件 直接new
        if(StringUtils.isNotEmpty(sequenceFlow.getConditionExpression())) {
            transition.condition = new UelExpressionCondition(sequenceFlow.getConditionExpression());
        }
        return transition;
    }

    public String getSourceCodeId() {
        return sourceCodeId;
    }

    public void setSourceCodeId(String sourceCodeId) {
        this.sourceCodeId = sourceCodeId;
    }

    public String getTargetCodeId() {
        return targetCodeId;
    }

    public void setTargetCodeId(String targetCodeId) {
        this.targetCodeId = targetCodeId;
    }

    public SequenceFlow getSequenceFlow() {
        return sequenceFlow;
    }

    public void setSequenceFlow(SequenceFlow sequenceFlow) {
        this.sequenceFlow = sequenceFlow;
    }
}
