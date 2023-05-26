
package com.makeid.makeflow.template.flow.base;



import com.makeid.makeflow.template.flow.sequence.SequenceMakeFlow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@program
 *@description 流程节点
 *@author feng_wf
 *@create 2023/5/23
 */
public abstract class MakeFlowNode extends MakeFlowElement {

  protected Date arriveTime;

  protected Date completeTime;

  protected Date leaveTime;

  protected List<SequenceMakeFlow> incomingFlows = new ArrayList<SequenceMakeFlow>();
  protected List<SequenceMakeFlow> outgoingFlows = new ArrayList<SequenceMakeFlow>();


  public MakeFlowNode() {

  }

    public List<SequenceMakeFlow> getIncomingFlows() {
        return incomingFlows;
    }

    public void setIncomingFlows(List<SequenceMakeFlow> incomingFlows) {
        this.incomingFlows = incomingFlows;
    }

    public List<SequenceMakeFlow> getOutgoingFlows() {
        return outgoingFlows;
    }

    public void setOutgoingFlows(List<SequenceMakeFlow> outgoingFlows) {
        this.outgoingFlows = outgoingFlows;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public void setValues(MakeFlowNode flowNode) {
        super.setValues(flowNode);
        this.setArriveTime(flowNode.getArriveTime());
        this.setCompleteTime(flowNode.getCompleteTime());
        this.setLeaveTime(flowNode.getLeaveTime());
        this.setIncomingFlows(flowNode.getIncomingFlows());
        this.setOutgoingFlows(flowNode.getOutgoingFlows());
    }
}
