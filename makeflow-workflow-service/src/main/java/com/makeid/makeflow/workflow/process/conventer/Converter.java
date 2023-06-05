package com.makeid.makeflow.workflow.process.conventer;

import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.workflow.process.ProcessElementInst;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 节点实列转换器
 * @create 2023-06-05
 */
public interface Converter<Node extends FlowNode,Inst extends ProcessElementInst> {

    Inst convert(Node node);
}
