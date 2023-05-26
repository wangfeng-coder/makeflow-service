package com.makeid.makeflow.template.flow.base;


import com.makeid.makeflow.template.flow.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.activity.EndActivity;
import com.makeid.makeflow.template.flow.activity.OperationGroup;
import com.makeid.makeflow.template.flow.activity.StartActivity;
import com.makeid.makeflow.template.flow.sequence.SequenceMakeFlow;

/**
 * 流程模板节点参数
 * 
 * @author jianguo_ran
 *
 */
public enum ElementTypeEnum {

	/**
	 * 线条节点
	 */
	ACTIVITYTYPE_LINE("sequenseFlow", SequenceMakeFlow.class),

	/**
	 * 开始节点
	 */
	ACTIVITYTYPE_START("start", StartActivity.class),


	ACTIVITYTYPE_MULTIAPPROVAL("multiApproval", ApprovalTaskActivity.class),

	ACTIVITYTYPE_OPERATIONGROUP("operationGroup", OperationGroup.class),

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
