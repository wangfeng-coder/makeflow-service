package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.EndEvent;
import com.makeid.makeflow.template.bpmn.model.SequenceFlow;
import com.makeid.makeflow.template.flow.model.activity.EndActivity;
import com.makeid.makeflow.template.flow.model.base.Element;
import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class EndEventTranslator implements Translator<EndEvent, EndActivity>{
    @Override
    public EndActivity translate(EndEvent endEvent) {
        EndActivity endActivity = new EndActivity();
        FlowNodePropertyHandler.fillFlowNodeProperty(endEvent,endActivity);
        return endActivity;
    }



}
