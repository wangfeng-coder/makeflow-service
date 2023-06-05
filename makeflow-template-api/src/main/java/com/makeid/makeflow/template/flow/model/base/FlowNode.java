
package com.makeid.makeflow.template.flow.model.base;


import com.makeid.makeflow.template.flow.model.sequence.SequenceFlow;

import java.util.ArrayList;
import java.util.List;

/**
 *@program
 *@description 流程节点
 *@author feng_wf
 *@create 2023/5/23
 */
public abstract class FlowNode extends FlowElement {


  protected List<SequenceFlow> incomingFlows = new ArrayList<SequenceFlow>();
  protected List<SequenceFlow> outgoingFlows = new ArrayList<SequenceFlow>();


  public FlowNode() {

  }

    public List<SequenceFlow> getIncomingFlows() {
        return incomingFlows;
    }

    public void setIncomingFlows(List<SequenceFlow> incomingFlows) {
        this.incomingFlows = incomingFlows;
    }

    public List<SequenceFlow> getOutgoingFlows() {
        return outgoingFlows;
    }

    public void setOutgoingFlows(List<SequenceFlow> outgoingFlows) {
        this.outgoingFlows = outgoingFlows;
    }

    public void setValues(FlowNode flowNode) {
        super.setValues(flowNode);
        this.setIncomingFlows(flowNode.getIncomingFlows());
        this.setOutgoingFlows(flowNode.getOutgoingFlows());
    }

    public void setValues(com.makeid.makeflow.template.bpmn.model.FlowNode flowNode) {
        super.setValues(flowNode);
    }
}
