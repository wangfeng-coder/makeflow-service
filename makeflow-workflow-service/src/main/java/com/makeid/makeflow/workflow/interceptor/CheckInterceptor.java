package com.makeid.makeflow.workflow.interceptor;


import com.makeid.makeflow.workflow.cmd.CheckCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckInterceptor extends AbstractCommandInterceptor {

	public CheckInterceptor() {
		// TODO Auto-generated constructor stub
	}

	private static Logger log = LoggerFactory.getLogger(CheckInterceptor.class);

	public <T> T execute(CommandConfig config, Command<T> command) {

		try {
			if (command instanceof CheckCommand) {
				log.info("command instanceof CheckCommand");
				CheckCommand checkCommand = (CheckCommand) command;
				String processInstanceId = checkCommand.getProcessInstanceId();
				FlowInst flowInst = Context.getExecutionService()
						.findFlowInstById(processInstanceId);
				if (flowInst == null) {
					throw new EngineException(ErrCodeEnum.WORKFLOW_INST_NOT_EXIST);
				}
				FlowStatus flowStatus = flowInst.getStatus();
				if(flowStatus == null){
					throw new EngineException(ErrCodeEnum.WORKFLOW_FLOW_STATUS_IS_NULL);
				}
				switch (flowStatus) {
				case RUNNING:
					break;
				default:
					throw new EngineException(ErrCodeEnum.WORKFLOW_FLOW_IS_NOT_RUNNING);
				}
			}
			return next.execute(config, command);
		} finally {
			
			
			
		}
	}

}
