package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.*;
import com.makeid.makeflow.template.flow.model.base.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class Translators {

    private final static Map<Class<? extends BaseElement>,Translator> translateMap = new HashMap<>();
    static {
        translateMap.put(StartEvent.class,new StartEventTranslator());
        translateMap.put(EndEvent.class,new EndEventTranslator());
        translateMap.put(UserTask.class,new UserTaskTranslator());
        translateMap.put(SequenceFlow.class,new SequenceFlowTranslator());
        translateMap.put(ExclusiveGateway.class,new ExclusiveGatewayTranslator());

    }
    public static Element translate(BaseElement baseElement) {
        Translator translate = translateMap.get(baseElement.getClass());
        return translate.translate(baseElement);
    }

    public static Translator findTranslator(Class clazz) {
        Translator translator = translateMap.get(clazz);
        return translator;
    }
}
