package com.makeid.makeflow.workflow.interceptor;


import com.makeid.makeflow.workflow.cmd.CheckCommand;
import com.makeid.makeflow.workflow.constants.ErrCodeEnum;
import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.exception.EngineException;
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
				Long processInstanceId = checkCommand.getProcessInstanceId();
				FlowInstEntity flowInst = Context.getFlowInstService()
						.findById(processInstanceId);
				if (flowInst == null) {
					throw new EngineException(ErrCodeEnum.FLOW_NOT_EXIST);
				}
				if(flowInst.getStatus() != FlowStatusEnum.RUNNING.status) {
					throw new EngineException("流程不在运行中");
				}

			}
			return next.execute(config, command);
		} finally {
			
			
			
		}
	}

}
