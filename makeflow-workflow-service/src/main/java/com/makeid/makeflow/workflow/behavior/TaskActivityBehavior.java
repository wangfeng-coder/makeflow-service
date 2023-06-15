package com.makeid.makeflow.workflow.behavior;


import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.HashMap;
import java.util.Map;


public abstract class TaskActivityBehavior extends AbstractActivityBehavior{

	@Override
	public void doExecute(ActivityPvmExecution execution)  {
		
		// 构造给创建任务是需要的参数, 数据不一定有
		Map<String, Object> params = new HashMap<>();
		//可将上下文参数统一提取出来 并设置到params
		doInternalExecute(execution, params);
	}
	
	abstract void doInternalExecute(ActivityPvmExecution execution, Map<String, Object> params);
	
	abstract public void completeTask(ActivityPvmExecution execution);

}
