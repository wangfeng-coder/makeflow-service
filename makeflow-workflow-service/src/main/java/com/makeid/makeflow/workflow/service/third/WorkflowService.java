package com.makeid.makeflow.workflow.service.third;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.service.FlowProcessTemplateService;
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

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-29
*/
@Component
@AllArgsConstructor
public class WorkflowService {

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
    public FlowDetailVO findFlowDetail(String flowInstId,String userId){
        Context.setCurrentUser(new UserContext(userId));
        FlowInstEntity flowInst = flowInstService.findById(flowInstId);
        List<ActivityEntity> activityEntities = activityService.findByFlowInstId(flowInstId);
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
    public String submitFlow(String templateCodeId,String userId, Map<String,Object> variables) {
        Context.setCurrentUser(new UserContext(userId));
        FlowProcessTemplate flowProcessTemplate = flowProcessTemplateService.findFlowProcessTemplateLastly(templateCodeId);
        Assert.notNull(flowProcessTemplate,"未发布这个流程");
        PvmProcessInstance pvmProcessInstance = runtimeService.startDefiniteProcessInstanceById(flowProcessTemplate.getFlowTemplateId(), null, variables);
        return pvmProcessInstance.getProcessInstanceId();
    }

    /**
     *  同意流程
     * @param flowInstId
     * @param handler
     * @param opionin
     * @return
     */
    public void agreeFlow(String flowInstId,String handler,String opionin,String currentUserId) {
        Context.setCurrentUser(new UserContext(currentUserId));
        List<TaskEntity> taskEntities = taskService.findFlowInstIdHandlerStatus(flowInstId, handler, TaskStatusEnum.DOING.status);
        taskEntities.forEach(task->runtimeService.agreeTask(task.getId(),opionin, Collections.EMPTY_MAP));
    }

    /**
     * 拒绝流程
     * @param flowInstId
     * @param handler
     * @param opionin
     */
    public void disAgreeFlow(String flowInstId,String handler,String opionin,String currentUserId) {
        Context.setCurrentUser(new UserContext(currentUserId));
        List<TaskEntity> taskEntities = taskService.findFlowInstIdHandlerStatus(flowInstId, handler, TaskStatusEnum.DOING.status);
        taskEntities.forEach(task->runtimeService.disAgreeTask(task.getId(),opionin, Collections.EMPTY_MAP));
    }


}
