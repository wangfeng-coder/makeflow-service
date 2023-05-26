package com.makeid.makeflow.template.flow.activity;


import com.makeid.makeflow.template.flow.base.ElementTypeEnum;
import com.makeid.makeflow.template.flow.base.MakeFlowElement;

/**
 * 结束节点
 * @author Administrator
 *
 */
public class EndActivity  extends BaseActivity{

	private static final long serialVersionUID = 1L;

	
	public EndActivity(){
		setElementType(ElementTypeEnum.ACTIVITYTYPE_END.getContext());
	}

	@Override
	public MakeFlowElement clone() {
		EndActivity endActivity = new EndActivity();
		super.setValues(endActivity);
		return null;
	}
}
