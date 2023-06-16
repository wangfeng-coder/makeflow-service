package com.makeid.makeflow.template.flow.model.base;


import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.EndActivity;
import com.makeid.makeflow.template.flow.model.activity.OperationGroup;
import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.gateway.ExclusiveGateway;
import com.makeid.makeflow.template.flow.model.sequence.SequenceFlow;

/**
 * 流程模板节点参数
 * 
 *
 *
 */
public enum ElementTypeEnum {

	/**
	 * 线条节点
	 */
	ACTIVITYTYPE_LINE("sequenseFlow", SequenceFlow.class),

	/**
	 * 开始节点
	 */
	ACTIVITYTYPE_START("start", StartActivity.class),


	ACTIVITYTYPE_MULTIAPPROVAL("multiApproval", ApprovalTaskActivity.class),

	ACTIVITYTYPE_OPERATIONGROUP("operationGroup", OperationGroup.class),


	EXCLUSIVE_GATEWAY("exclusiveGateWay", ExclusiveGateway.class),

	/**
	 *  结束节点
	 */
	ACTIVITYTYPE_END("end", EndActivity.class),

	;


	private String context;

	private Class clazz;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	private ElementTypeEnum(String context){
		this.context=context;
	}


	private ElementTypeEnum(String context, Class clazz) {
		this.context = context;
		this.clazz = clazz;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	

}
