package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        FlowInstEntity flowInst = null;
        if (execution instanceof ProcessInstanceExecution) {
            flowInst = ((ProcessInstanceExecution) execution).getFlowInstEntity();
        }
        if (Objects.isNull(flowInst)) {
            Long flowInstId = activityInst.getFlowInstId();
            flowInst = Context.getFlowInstService().findById(flowInstId);
        }
        //这里要是流程创建人
        List<TaskEntity> tasks = Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .createTask(Arrays.asList(flowInst.getApply()), execution);
        runningAllTask(tasks);
        Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .save(tasks);
        //流程状态为待提交状态
        toUnSubmit(flowInst);
        //在重新提交流程时
        execution.stay();
    }

    private void toUnSubmit(FlowInstEntity flowInst) {
        flowInst.setStatus(FlowStatusEnum.UNSUBMIT.status);
        Context.getFlowInstService().save(flowInst);
    }

    @Override
    public void completedTask(ActivityPvmExecution execution) {
        taskCompleteCanLeave(execution);
    }

}
