package com.makeid.makeflow.workflow.cmd;


import com.makeid.makeflow.workflow.interceptor.CommandContext;
import com.makeid.makeflow.workflow.process.ProcessInstancePvmExecution;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionBuilder;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

abstract class StartProcessInstanceCmd extends AbstractCommand<ProcessInstancePvmExecution> {

	private static Logger log = LoggerFactory
			.getLogger(StartProcessInstanceCmd.class);

	private static final long serialVersionUID = 1L;
	protected String processDefinitionId;
	protected String processInstanceId;

	public StartProcessInstanceCmd() {
		// TODO Auto-generated constructor stub
	}


	public StartProcessInstanceCmd(String processDefinitionId,
                                   String processInstanceId, Map<String, Object> variables) {
		super(variables);
		if (StringUtils.isBlank(processDefinitionId)) {
			throw new IllegalArgumentException("processDefinitionId is blank");
		}
		this.processDefinitionId = processDefinitionId;
		this.processInstanceId = processInstanceId;
	}

	@Override
	public ProcessInstancePvmExecution execute(CommandContext commandContext) {
		log.info(
				"StartProcessInstanceCmd--processDefinitionId:{},processInstanceId:{}",
				processDefinitionId, processInstanceId);
		//流程已经提交现在 要重新开始  获取流程实列继续流转 TODO
		//获取流程定义
		ProcessDefinitionImpl processDefinition = ProcessDefinitionBuilder.create().createProcessDefinition(processDefinitionId)
				.build();
		final ProcessInstancePvmExecution processInstanceExecution = (ProcessInstancePvmExecution)processDefinition.createProcessInstanceExecution();
		processInstanceExecution.save();
		processInstanceExecution.start();
		return processInstanceExecution;
	}


}
