package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.StartEvent;
import com.makeid.makeflow.template.flow.model.activity.StartActivity;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class StartEventTranslator implements Translator<StartEvent, StartActivity> {

    @Override
    public StartActivity translate(StartEvent startEvent) {
        StartActivity startActivity = new StartActivity();
        FlowNodePropertyHandler.fillFlowNodeProperty(startEvent,startActivity);
        startActivity.setInitiator(startActivity.getInitiator());
        return startActivity;
    }
}
