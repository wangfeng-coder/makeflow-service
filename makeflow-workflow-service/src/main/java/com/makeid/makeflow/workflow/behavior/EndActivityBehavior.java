package com.makeid.makeflow.workflow.behavior;



import com.makeid.makeflow.workflow.operation.AtomicOperations;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.Map;


public class EndActivityBehavior extends TaskActivityBehavior {

	public EndActivityBehavior() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doInternalExecute(ActivityPvmExecution execution, Map<String,Object> taskParams) {
		//结束节点 没有行为什么都不做
        execution.performOperation(AtomicOperations.ACTIVITY_END);
	}
	@Override
	public void completeTask(ActivityPvmExecution execution) {
		return;
	}
}
