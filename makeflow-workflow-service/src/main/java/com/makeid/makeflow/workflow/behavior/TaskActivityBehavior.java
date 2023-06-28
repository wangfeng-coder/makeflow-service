package com.makeid.makeflow.workflow.behavior;


import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class TaskActivityBehavior extends AbstractActivityBehavior{

	@Override
	public void doExecute(ActivityPvmExecution execution)  {
		
		// 构造给创建任务是需要的参数, 数据不一定有
		Map<String, Object> params = new HashMap<>();
		//可将上下文参数统一提取出来 并设置到params
		doInternalExecute(execution, params);
	}
	
	abstract  void doInternalExecute(ActivityPvmExecution execution, Map<String, Object> params);

	@Override
	public void completedTask(ActivityPvmExecution execution) {
		taskCompleteCanLeave(execution);
	}

	protected void taskCompleteCanLeave(ActivityPvmExecution execution) {
		//判断任务是否都完成了
		PvmActivity activityInst = execution.findActivityInst();
		String activityInstId = activityInst.getId();
		//如果当前任务是
		if (Context.getTaskService().isCompleteSkipTask(activityInstId)) {
			//都完成了 可以继续流转
			leave(execution);
		}
	}



	protected boolean isComplete(List<TaskEntity> taskInstList) {
		return taskInstList.stream().allMatch(taskInst -> TaskStatusEnum.DONE.status == taskInst.getStatus());
	}

	/**
	 * 任务为运行
	 * @param taskEntities
	 */
	protected void runningAllTask(List<TaskEntity> taskEntities) {
		for (TaskEntity taskEntity : taskEntities) {
			taskEntity.setStatus(TaskStatusEnum.DOING.status);
		}
	}
}
