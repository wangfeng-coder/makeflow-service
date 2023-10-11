package com.makeid.makeflow.workflow.behavior;

import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.OperationGroup;
import com.makeid.makeflow.template.flow.model.activity.PeopleHolder;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.event.EventRegister;
import com.makeid.makeflow.workflow.event.TaskRunningEvent;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public class ApprovalTaskActivityBehavior extends TaskActivityBehavior {

    void doInternalExecute(ActivityPvmExecution execution, Map<String, Object> params) {
        PvmActivity activityInst = execution.findActivityInst();
        String codeId = activityInst.getCodeId();
        ApprovalTaskActivity approvalTaskActivity = (ApprovalTaskActivity) execution.findFlowNode(codeId);
        OperationGroup operationGroup = approvalTaskActivity.getOperationGroup();
        List<PeopleHolder> participants = operationGroup.getParticipants();
        //根据参与人创建任务,
        List<TaskEntity> taskList = Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .createTask(participants, approvalTaskActivity.getApprovalSettings(), execution);
        //任务状态处理
        runningAllTask(taskList);
        //任务自动完成 TODO
        //待完成任务发送代办
        EventRegister.post(new TaskRunningEvent(taskList));
        //保存任务
        Context.getGlobalProcessEngineConfiguration()
                .getTaskService()
                .save(taskList);
        //所有任务都完成
        if (CollectionUtils.isEmpty(taskList) || isComplete(taskList)) {
            // 去下个节点 TODO 后需要改成异步[但事务处理比较复杂]前往下个节点,但注意上下文信息线程传递
            leave(execution);
        } else {
            //存在任务没有完成 当前执行等待
            //TODO 不操作什么 后续可加事件
            execution.stay();
        }


    }



    private boolean isReturn(TaskEntity taskEntity) {
        return TaskStatusEnum.RETURN.status.equals(taskEntity.getStatus());
    }

    private boolean isDisagree(TaskEntity taskEntity) {
        return  TaskStatusEnum.DISAGREE.status.equals(taskEntity.getStatus());
    }
}
