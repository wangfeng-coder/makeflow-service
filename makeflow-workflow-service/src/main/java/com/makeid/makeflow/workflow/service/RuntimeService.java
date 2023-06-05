package com.makeid.makeflow.workflow.service;



import com.makeid.makeflow.workflow.process.ProcessInstance;

import java.util.Map;

/**
 *@program
 *@description 运行时的操作类
 *@author feng_wf
 *@create 2023/5/30
 */
public interface RuntimeService {


	ProcessInstance startDefiniteProcessInstanceById(
			String processDefinitionId, String processInstanceId,
			Map<String, Object> variables);
}
