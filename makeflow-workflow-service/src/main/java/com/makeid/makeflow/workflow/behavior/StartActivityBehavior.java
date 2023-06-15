package com.makeid.makeflow.workflow.behavior;


import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import com.makeid.makeflow.workflow.task.StartTaskInst;

import java.util.Date;
import java.util.Map;


public class StartActivityBehavior extends TaskActivityBehavior {


	public StartActivityBehavior() {
	}


	@Override
	public void doInternalExecute(ActivityPvmExecution execution, Map<String, Object> taskParams) {
		//创建任务
		StartTaskInst startTaskInst = new StartTaskInst();
		//从当前线程 或者参数集合中 获取当前用户或者申请人
		startTaskInst.setHandler("");
		//开始节点直接过
		startTaskInst.setStatus(TaskStatusEnum.DONE.status);
		startTaskInst.setCompleteTime(new Date());
		//保存入库
		startTaskInst.save();
		completeTask(execution);
	}

	@Override
	public void completeTask(ActivityPvmExecution execution) {
		//当前任务都完成了，可以Leave
		leave(execution);
	}


}
