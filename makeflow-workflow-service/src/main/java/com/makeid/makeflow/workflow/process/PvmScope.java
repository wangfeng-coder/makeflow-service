package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.workflow.delegate.DelegateScopeWriter;

import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 数据获取
 * @create 2023-06-09
 */
public interface PvmScope extends PvmProcessElement, DelegateScopeWriter {

    Map<String, Object> getVariables();

    void addVariables(Map<String,Object> variables);

    boolean isScope();




}
