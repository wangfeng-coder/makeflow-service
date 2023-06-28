package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-27
 */
public class RestartActivityBehavior extends StartActivityBehavior {


    @Override
    public void doInternalExecute(ActivityPvmExecution execution, Map<String, Object> taskParams) {
        //
        PvmActivity activityInst = execution.findActivityInst();
        //这里要是流程创建人
        List<TaskEntity> tasks = Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .createTask(Arrays.asList(Context.getUserId()), activityInst);
        runningAllTask(tasks);
        Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .save(tasks);
        //在重新提交流程时
        execution.stay();
    }

    @Override
    public void completedTask(ActivityPvmExecution execution) {
        taskCompleteCanLeave(execution);
    }

}
