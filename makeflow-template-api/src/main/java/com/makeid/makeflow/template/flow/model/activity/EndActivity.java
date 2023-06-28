package com.makeid.makeflow.template.flow.model.activity;


import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import com.makeid.makeflow.template.flow.model.base.FlowElement;

/**
 * 结束节点
 * @author Administrator
 *
 */
public class EndActivity  extends BaseActivity{

	private static final long serialVersionUID = 1L;

	
	public EndActivity(){
		setElementType(ElementTypeEnum.ACTIVITYTYPE_END.getType());
	}

	@Override
	public EndActivity clone() {
		EndActivity endActivity = new EndActivity();
		super.setValues(endActivity);
		return endActivity;
	}

}
