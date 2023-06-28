package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.FlowNode;
import com.makeid.makeflow.template.bpmn.model.SequenceFlow;
import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public abstract class FlowNodePropertyHandler {

    public static void fillFlowNodeProperty(FlowNode source, com.makeid.makeflow.template.flow.model.base.FlowNode target) {
        target.setCodeId(source.getId());
        target.setName(source.getName());
        SequenceFlowTranslator translator = (SequenceFlowTranslator) Translators.findTranslator(SequenceFlow.class);
        target.setIncomingFlows(TranslatorHelper.toList(source.getIncomingFlows(), translator::translate));
        target.setOutgoingFlows(TranslatorHelper.toList(source.getOutgoingFlows(), translator::translate));
        Arrays.stream(ElementTypeEnum.values())
                .forEach(elementTypeEnum -> {
                    if (target.getClass().equals(elementTypeEnum.getClazz())) {
                        target.setElementType(elementTypeEnum.getType());
                    }
                });
        Assert.notNull(target.getElementType());
    }
}
