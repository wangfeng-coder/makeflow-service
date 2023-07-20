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
			Long processDefinitionId, Long processInstanceId,
			Map<String, Object> variables);

	void agreeTask(Long taskId,String opinion,Map<String,Object>variables);

	void disAgreeTask(Long taskId, String opinion, Map<String,Object> variables);


	void returnTask(Long taskId,String opinion,String targetCodeId,Map<String,Object> variables);


}
