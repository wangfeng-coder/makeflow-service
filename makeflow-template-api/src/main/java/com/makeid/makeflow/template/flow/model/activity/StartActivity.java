package com.makeid.makeflow.template.flow.model.activity;


import com.makeid.makeflow.template.bpmn.model.StartEvent;
import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 *@program
 *@description 启动节点
 *@author feng_wf
 *@create 2023/5/23
 */
@Getter
@Setter
public class StartActivity extends BaseActivity{

	private String initiator;

	private static final long serialVersionUID = 1L;

	public StartActivity() {
		setElementType(ElementTypeEnum.ACTIVITYTYPE_START.getType());
	}

	public static StartActivity transferFrom(StartEvent startEvent) {
		StartActivity startActivity = new StartActivity();
		startActivity.initiator = startEvent.getInitiator();
		startActivity.setCodeId(startEvent.getId());
		startActivity.setName(startActivity.name);
		return startActivity;
	}
}
