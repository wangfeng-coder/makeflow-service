package com.makeid.makeflow.workflow.behavior;


import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import com.makeid.makeflow.workflow.service.TaskService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class StartActivityBehavior extends TaskActivityBehavior {

	public StartActivityBehavior() {

	}


	@Override
	public void doInternalExecute(ActivityPvmExecution execution, Map<String, Object> taskParams) {
		PvmActivity activityInst = execution.findActivityInst();
		//这里要是流程创建人
		List<TaskEntity> tasks = Context.getGlobalProcessEngineConfiguration()
				.getTaskService()
				.createTask(Arrays.asList(Context.getUserId()), activityInst);
		Context.getGlobalProcessEngineConfiguration()
				.getTaskService()
						.completeTasks(tasks,TaskStatusEnum.DONE.status);
		//保存入库;
		Context.getGlobalProcessEngineConfiguration()
						.getTaskService()
								.save(tasks);
		//任务已完成 往下个地方
		completedTask(execution);
	}

	@Override
	public void completedTask(ActivityPvmExecution execution) {
		//当前任务都完成了，可以Leave
		leave(execution);
	}


}
