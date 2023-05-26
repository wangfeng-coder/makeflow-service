package com.makeid.makeflow.template.flow.activity;


import com.makeid.makeflow.template.flow.base.ElementTypeEnum;

/**
 *@program
 *@description 启动节点
 *@author feng_wf
 *@create 2023/5/23
 */
public class StartActivity extends BaseActivity{

	private static final long serialVersionUID = 1L;

	public StartActivity() {
		setElementType(ElementTypeEnum.ACTIVITYTYPE_START.getContext());
	}
}
