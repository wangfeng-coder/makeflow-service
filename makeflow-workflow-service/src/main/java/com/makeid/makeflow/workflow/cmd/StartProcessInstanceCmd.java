package com.makeid.makeflow.workflow.cmd;


import com.makeid.makeflow.workflow.interceptor.CommandContext;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionBuilder;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.Objects;

abstract class StartProcessInstanceCmd extends AbstractCommand<ProcessInstanceExecution> {

	private static Logger log = LoggerFactory
			.getLogger(StartProcessInstanceCmd.class);

	private static final long serialVersionUID = 1L;
	protected Long processDefinitionId;
	protected Long processInstanceId;

	public StartProcessInstanceCmd() {
		// TODO Auto-generated constructor stub
	}


	public StartProcessInstanceCmd(Long processDefinitionId,
								   Long processInstanceId, Map<String, Object> variables) {
		super(variables);
		if (Objects.isNull(processDefinitionId)) {
			throw new IllegalArgumentException("processDefinitionId is blank");
		}
		this.processDefinitionId = processDefinitionId;
		this.processInstanceId = processInstanceId;
	}

	@Override
	public ProcessInstanceExecution execute() {
		log.info(
				"StartProcessInstanceCmd--processDefinitionId:{},processInstanceId:{}",
				processDefinitionId, processInstanceId);
		//流程已经提交现在 要重新开始  获取流程实列继续流转 TODO
		//获取流程定义
		//传入流程参数
		ProcessDefinitionImpl processDefinition = ProcessDefinitionBuilder.builder()
				.createProcessDefinition(processDefinitionId)
				.build();
		final ProcessInstanceExecution processInstanceExecution = (ProcessInstanceExecution)processDefinition.createProcessInstanceExecution(this.getVariables());
		processInstanceExecution.initialize();
		processInstanceExecution.save();
		processInstanceExecution.start();
		return processInstanceExecution;
	}


}
