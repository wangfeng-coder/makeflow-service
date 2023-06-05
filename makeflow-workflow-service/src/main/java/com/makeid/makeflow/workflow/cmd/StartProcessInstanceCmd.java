package com.makeid.makeflow.workflow.cmd;


import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.interceptor.CommandContext;
import com.makeid.makeflow.workflow.process.ProcessDefinitionInst;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

abstract class StartProcessInstanceCmd extends AbstractCommand<ProcessInstanceExecution> {

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
	public ProcessInstanceExecution execute(CommandContext commandContext) {
		log.info(
				"StartProcessInstanceCmd--processDefinitionId:{},processInstanceId:{}",
				processDefinitionId, processInstanceId);
		//流程已经提交现在 要重新开始  获取流程实列继续流转 TODO
		//获取流程定义
		final ProcessDefinitionInst flowProcessDefinition = ProcessDefinitionInst.createProcessDefinitionInst(processDefinitionId);
		final ProcessInstanceExecution processInstanceExecution = flowProcessDefinition.createProcessInstanceExecution();
		processInstanceExecution.save();
		processInstanceExecution.start();
		return processInstanceExecution;
	}

	/**
	 * 若存在草稿，草稿转为实例需处理草稿的内容 
	 * @param processInstanceId
	 */
	private void removeDraft(String processInstanceId, String newFlowInstId) {
		Draft draft = Context.getDraftService().getById(processInstanceId);	//以草稿启动流程
		if(draft!=null) {
			//删除流程 
			Context.getDraftService().deleteById(draft.getId());
//			this.processInstanceId = flowInst.getId();
			//发送事件..
			ProcessProducer.draftDeleted(draft,null, newFlowInstId);
		}
	}

	abstract void initial(ExecutionImpl processInstance,
			ProcessDefinitionImpl processDefinition);

}
