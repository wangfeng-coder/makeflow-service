package com.makeid.makeflow.workflow.service;


import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;

import java.util.Map;

/**
 *@program
 *@description 运行时的操作类
 *@author feng_wf
 *@create 2023/5/30
 */
public interface RuntimeService {


	PvmProcessInstance startDefiniteProcessInstanceById(
			String processDefinitionId, String processInstanceId,
			Map<String, Object> variables);

	void agreeTask(String taskId,String opinion,Map<String,Object>variables);

	void disAgreeTask(String taskId,String opinion,Map<String,Object> variables);
}
