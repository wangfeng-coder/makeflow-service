package com.makeid.makeflow.workflow.service.impl;

import com.google.common.eventbus.EventBus;
import com.makeid.makeflow.basic.lazy.LazyProvider;
import com.makeid.makeflow.basic.utils.SpringContextUtils;
import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.service.impl.FlowProcessTemplateServiceImpl;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.context.UserContext;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.event.EventRegister;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;
import com.makeid.makeflow.workflow.service.FlowInstService;
import com.makeid.makeflow.workflow.service.RuntimeService;
import com.makeid.makeflow.workflow.service.config.SpringBootTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = SpringBootTestConfig.class)
@RunWith(SpringRunner.class)
@Slf4j
public class RuntimeServiceImplTest {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FlowProcessTemplateServiceImpl flowProcessTemplateService;

    @Autowired
    private FlowInstService flowInstService;


    @Before
    public void userContextSet() {
        UserContext userContext = new UserContext();
        userContext.setUserId("王峰");
        Context.setCurrentUser(userContext);
    }

    @Test
    public void testStart(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("type",2);

        PvmProcessInstance hiddenDangerOrderCommunity = runtimeService.startDefiniteProcessInstanceById("hiddenDangerOrderCommunity", "", objectObjectHashMap);
        String processInstanceId = hiddenDangerOrderCommunity.getProcessInstanceId();
        Assert.notNull(processInstanceId);
    }

    @Test
    public void testAgreeCmd() {
        //查询我的任务
        List<TaskEntity> taskEntities = Context.getTaskService().findTaskByHandler(Context.getUserId());
        List<String> collect = taskEntities.stream().map(TaskEntity::getId).collect(Collectors.toList());
        log.info("待处理的任务 数量【{}】任务【{}】",taskEntities.size(),collect.stream().collect(Collectors.joining(",")));
        for (TaskEntity taskEntity : taskEntities) {
            runtimeService.agreeTask(taskEntity.getId(),"我；也同意了",new HashMap<>());
        }
       taskEntities = Context.getTaskService().findTaskByHandler(Context.getUserId());

    }

    List<TaskEntity> findMyTodo() {
        return  Context.getTaskService().findTaskByHandler(Context.getUserId());
    }

    @Test
    public void testDisAgreeCmd() {
        //查询我的任务
        List<TaskEntity> taskEntities = Context.getTaskService().findTaskByHandler(Context.getUserId());
        List<String> collect = taskEntities.stream().map(TaskEntity::getId).collect(Collectors.toList());
        log.info("待处理的任务 数量【{}】任务【{}】",taskEntities.size(),collect.stream().collect(Collectors.joining(",")));
        for (TaskEntity taskEntity : taskEntities) {
            runtimeService.disAgreeTask(taskEntity.getId(),"我；我拒绝",new HashMap<>());
        }
    }




    @Test
    public void testLazyProvider(){
        ProcessDefinitionImpl provide = LazyProvider.provide("", new Class[]{FlowProcessTemplate.class},
                new Object[]{null}, id ->
           new ProcessDefinitionImpl(flowProcessTemplateService.getFlowProcessDefinition(id)), ProcessDefinitionImpl.class);
        String codeId = provide.getCodeId();
        FlowNode flowNode = provide.findFlowNode("12");
    }

    @Test
    public void testReturnCmdStartNode() {
        List<TaskEntity> myTodo = findMyTodo();
        for (TaskEntity taskEntity : myTodo) {
            //查询节点codeId
            String flowInstId = taskEntity.getFlowInstId();
            FlowInstEntity byId = flowInstService.findById(flowInstId);
            String definitionId = byId.getDefinitionId();
            FlowProcessTemplate flowProcessDefinition = flowProcessTemplateService.getFlowProcessDefinition(definitionId);
            StartActivity initial = flowProcessDefinition.findInitial();
            String codeId = initial.getCodeId();
            runtimeService.returnTask(taskEntity.getId(),"回去重写",codeId,new HashMap<>());
        }
    }


    public static void main(String[] args) {
       // ProcessDefinitionImpl provide = LazyProvider.provide("1", id -> new ProcessDefinitionImpl(new FlowProcessTemplate()), ProcessDefinitionImpl.class);

    }

    @Test
    public void publish() {
        EventRegister bean = SpringContextUtils.getBean(EventRegister.class);
    }


}