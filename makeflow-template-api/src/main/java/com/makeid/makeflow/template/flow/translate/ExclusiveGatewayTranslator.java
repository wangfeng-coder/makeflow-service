package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.ExclusiveGateway;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class ExclusiveGatewayTranslator implements Translator<ExclusiveGateway, com.makeid.makeflow.template.flow.model.gateway.ExclusiveGateway> {
    @Override
    public com.makeid.makeflow.template.flow.model.gateway.ExclusiveGateway translate(ExclusiveGateway exclusiveGateway) {
        com.makeid.makeflow.template.flow.model.gateway.ExclusiveGateway target
                = new com.makeid.makeflow.template.flow.model.gateway.ExclusiveGateway();
        FlowNodePropertyHandler.fillFlowNodeProperty(exclusiveGateway,target);
        return target;
    }
}
