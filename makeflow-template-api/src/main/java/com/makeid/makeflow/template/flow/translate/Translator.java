package com.makeid.makeflow.template.flow.translate;

import com.makeid.makeflow.template.bpmn.model.BaseElement;
import com.makeid.makeflow.template.flow.model.base.Element;
/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public interface Translator<T extends BaseElement,R extends Element> {


    R translate(T t);

}
