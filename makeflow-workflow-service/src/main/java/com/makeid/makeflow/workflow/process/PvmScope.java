package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.base.FlowNode;

import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 数据获取
 * @create 2023-06-09
 */
public interface PvmScope extends PvmProcessElement {

    Map<String, Object> getVariables();

    boolean isScope();

    PvmActivity findActivity(String codeId);

    FlowNode findFlowNode(String codeId);

    PvmTransition findTransition(String codeId);
}
