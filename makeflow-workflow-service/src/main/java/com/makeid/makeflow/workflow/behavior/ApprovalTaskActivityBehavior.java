package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.OperationGroup;
import com.makeid.makeflow.template.flow.model.activity.Participant;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import com.makeid.makeflow.workflow.task.TaskInst;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class ApprovalTaskActivityBehavior extends AbstractActivityBehavior{
    @Override
    protected void doExecute(ActivityPvmExecution execution) {
        PvmActivity activityInst = execution.findActivityInst();
        String codeId = activityInst.getCodeId();
        ApprovalTaskActivity approvalTaskActivity = (ApprovalTaskActivity)execution.findFlowNode(codeId);
        OperationGroup operationGroup = approvalTaskActivity.getOperationGroup();
        List<Participant> participants = operationGroup.getParticipants();
        //根据参与人创建任务,
        List<TaskInst> taskList = Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .createTask(participants, approvalTaskActivity.getApprovalSettings(), activityInst);
        //所有任务都完成
        if(CollectionUtils.isEmpty(taskList) || isComplete(taskList)){
            // 去下个节点 TODO 后需要改成异步前往下个节点
            leave(execution);
        }else {
            //存在任务没有完成 当前执行等待
            //TODO 不操作什么 后续可加事件
        }

    }

    boolean isComplete(List<TaskInst> taskInstList) {
        return taskInstList.stream().allMatch(taskInst -> TaskStatusEnum.DONE.status == taskInst.getStatus());
    }
}
