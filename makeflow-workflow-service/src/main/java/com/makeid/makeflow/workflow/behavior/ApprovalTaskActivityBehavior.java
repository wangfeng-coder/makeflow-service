package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.OperationGroup;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class ApprovalTaskActivityBehavior extends AbstractActivityBehavior {
    @Override
    protected void doExecute(ActivityPvmExecution execution) {
        PvmActivity activityInst = execution.findActivityInst();
        String codeId = activityInst.getCodeId();
        ApprovalTaskActivity approvalTaskActivity = (ApprovalTaskActivity) execution.findFlowNode(codeId);
        OperationGroup operationGroup = approvalTaskActivity.getOperationGroup();
        List<PeopleHolder> participants = operationGroup.getParticipants();
        //根据参与人创建任务,
        List<TaskEntity> taskList = Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .createTask(participants, approvalTaskActivity.getApprovalSettings(), activityInst);
        //任务状态处理
        runningAllTask(taskList);
        //任务自动完成 TODO

        //保存任务
        Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .save(taskList);
        //所有任务都完成
        if (CollectionUtils.isEmpty(taskList) || isComplete(taskList)) {
            // 去下个节点 TODO 后需要改成异步前往下个节点,但注意上下文信息线程传递
            leave(execution);
        } else {
            //存在任务没有完成 当前执行等待
            //TODO 不操作什么 后续可加事件
            execution.stay();
        }


    }

    @Override
    public void completeTask(ActivityPvmExecution execution,List<TaskEntity> taskEntities) {
        //判断任务是否都完成了
        PvmActivity activityInst = execution.findActivityInst();
        String activityInstId = activityInst.getId();
        if (isDisagree(taskEntities)) {
            execution.end(FlowStatusEnum.DISAGREE.status);
        }
        //如果当前任务是
        if (Context.getTaskService().isCompleteSkipTask(activityInstId)) {
            //都完成了 可以继续流转
            leave(execution);
        }
    }

    private boolean isDisagree(List<TaskEntity> taskEntities) {
        if(CollectionUtils.isEmpty(taskEntities)) {
            return false;
        }
        TaskEntity taskEntity = taskEntities.get(0);
        return  TaskStatusEnum.DISAGREE.status.equals(taskEntity.getStatus());
    }
}
