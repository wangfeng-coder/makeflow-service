package com.makeid.makeflow.workflow.cmd;


import com.makeid.makeflow.workflow.constants.FlowMapParamKeys;
import com.makeid.makeflow.workflow.interceptor.Command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommand<T> implements Command<T>, CheckCommand,
		LockCommand, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Map<String, Object> variables;


	
	public AbstractCommand() {
		// TODO Auto-generated constructor stub
	}

	protected AbstractCommand(Map<String, Object> variables) {
		this.variables = variables;
	}


	protected Map<String, Object> getVariables() {
		if (this.variables == null) {
			this.variables = new HashMap<>();
		}
		return this.variables;
	}

	@Override
	public String getProcessInstanceId() {
		return (String) this.getVariables().get(FlowMapParamKeys.FLOW_INST_ID_KEY);
	}
	
	@Override
	public String getOpLockKey() {
		return (String) this.getVariables().get(FlowMapParamKeys.FLOW_OP_LOCK_KEY);
	}
	

	


}
