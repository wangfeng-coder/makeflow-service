package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.SequenceFlow;
import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class SequenceFlowTranslator implements Translator<SequenceFlow, com.makeid.makeflow.template.flow.model.sequence.SequenceFlow> {
    @Override
    public com.makeid.makeflow.template.flow.model.sequence.SequenceFlow translate(SequenceFlow sequenceFlow) {
        com.makeid.makeflow.template.flow.model.sequence.SequenceFlow target = new com.makeid.makeflow.template.flow.model.sequence.SequenceFlow();
        target.setTargetCodeId(sequenceFlow.getTargetRef());
        target.setSourceCodeId(sequenceFlow.getSourceRef());
        target.setCodeId(sequenceFlow.getId());
        target.setConditionExpression(sequenceFlow.getConditionExpression());
        target.setElementType(ElementTypeEnum.ACTIVITYTYPE_LINE.getContext());
        return target;
    }
}
