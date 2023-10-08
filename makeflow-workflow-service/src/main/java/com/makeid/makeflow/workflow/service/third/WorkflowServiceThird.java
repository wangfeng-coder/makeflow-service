package com.makeid.makeflow.workflow.service.third;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.service.FlowProcessTemplateService;
import com.makeid.makeflow.workflow.constants.ActivityTypeBehaviorEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.context.UserContext;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;
import com.makeid.makeflow.workflow.service.ActivityService;
import com.makeid.makeflow.workflow.service.FlowInstService;
import com.makeid.makeflow.workflow.service.RuntimeService;
import com.makeid.makeflow.workflow.service.TaskService;
import com.makeid.makeflow.workflow.vo.FlowDetailBuilder;
import com.makeid.makeflow.workflow.vo.FlowDetailVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-29
*/
@Component
@AllArgsConstructor
public class WorkflowServiceThird {

    private final FlowInstService flowInstService;

    private final ActivityService activityService;

    private final TaskService taskService;

    private final FlowProcessTemplateService flowProcessTemplateService;

    private final RuntimeService runtimeService;

    /**
     * 流程详情
     * @param flowInstId
     * @return
     */
    public FlowDetailVO findFlowDetail(Long flowInstId,String userId){
        Context.setCurrentUser(new UserContext(userId));
        FlowInstEntity flowInst = flowInstService.findById(flowInstId);
        List<ActivityEntity> activityEntities = activityService.findByFlowInstId(flowInstId);
        activityEntities = activityEntities.stream()
                .filter(activity ->!Objects.equals(activity.getActivityType(),ActivityTypeBehaviorEnum.EXCLUSIVE_GATEWAY.activityType))
                .collect(Collectors.toList());
        List<TaskEntity> taskEntities = taskService.findByFlowInstId(flowInstId);
        FlowDetailBuilder flowDetailBuilder = new FlowDetailBuilder();
        return flowDetailBuilder.setFlowInst(flowInst)
                .setActivityEntityList(activityEntities)
                .setTaskEntityList(taskEntities)
                .buildFlowDetailVO();
    }

    /**
     * 发起流程
     * @param templateCodeId 模板codeId
     * @param variables
     * @return 流程id
     */
    public Long submitFlow(String templateCodeId,Long flowInstId ,String userId, Map<String,Object> variables) {
        Context.setCurrentUser(new UserContext(userId));
        FlowProcessTemplate flowProcessTemplate = flowProcessTemplateService.findFlowProcessTemplateLastly(templateCodeId);
        Assert.notNull(flowProcessTemplate,"未发布这个流程");
        PvmProcessInstance pvmProcessInstance = runtimeService.startDefiniteProcessInstanceById(flowProcessTemplate.getFlowTemplateId(), flowInstId, variables);
        return pvmProcessInstance.getProcessInstanceId();
    }

    /**
     *  同意流程
     * @param flowInstId
     * @param handler
     * @param opinion
     * @return
     */
    public void agreeFlow(Long flowInstId,String handler,String opinion,String currentUserId,Map<String,Object> variables) {
        Context.setCurrentUser(new UserContext(currentUserId));
        List<TaskEntity> taskEntities = taskService.findFlowInstIdHandlerStatus(flowInstId, handler, TaskStatusEnum.DOING.status);
        taskEntities.forEach(task->runtimeService.agreeTask(task.getId(),opinion, variables));
    }

    /**
     * 拒绝流程
     * @param flowInstId
     * @param handler
     * @param opinion
     */
    public void disAgreeFlow(Long flowInstId,String handler,String opinion,String currentUserId,Map<String,Object> variables) {
        Context.setCurrentUser(new UserContext(currentUserId));
        List<TaskEntity> taskEntities = taskService.findFlowInstIdHandlerStatus(flowInstId, handler, TaskStatusEnum.DOING.status);
        taskEntities.forEach(task->runtimeService.disAgreeTask(task.getId(),opinion, variables));
    }

    /**
     * 退回
     * @param targetCodeId
     * @param taskId
     * @param handler
     * @param opinion
     * @param currentUserId
     */
    public void returnTask(String targetCodeId,Long taskId,String handler,String opinion,String currentUserId,Map<String,Object> variables) {
        Context.setCurrentUser(new UserContext(currentUserId));
        TaskEntity taskEntity = taskService.findTaskById(taskId);
        Assert.notNull(taskEntity,"任务不存在");
        Assert.isTrue(Objects.equals(taskEntity.getHandler(),handler),"处理人不匹配");
        runtimeService.returnTask(taskId,opinion,targetCodeId,variables);
    }

    /**
     * 从当前节点跳到任意节点
     * @param targetCodeId 目标节点codeId
     * @param sourceActivityId 当前节点id
     * @param opinion
     * @param currentUserId
     * @param variables
     */
    public void freeJump(String targetCodeId,Long sourceActivityId,String opinion,String currentUserId,Map<String,Object> variables) {
        Context.setCurrentUser(new UserContext(currentUserId));
        runtimeService.freeJump(targetCodeId,sourceActivityId,opinion,variables);
    }

}
