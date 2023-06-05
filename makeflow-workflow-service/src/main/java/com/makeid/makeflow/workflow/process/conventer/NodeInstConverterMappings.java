package com.makeid.makeflow.workflow.process.conventer;

import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.workflow.process.ProcessElementInst;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public abstract class NodeInstConverterMappings {

    public static final Map< ? extends FlowNode,? extends ProcessElementInst> converters = new HashMap<>();


    /**
     * 将节点的设计模型转换成运行时领域模型
     * @param flowNode
     * @return
     */
    public ProcessElementInst convert(FlowNode flowNode) {

    }
}
