package com.makeid.makeflow.workflow.service.impl;

import com.makeid.makeflow.basic.lazy.LazyProvider;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.service.impl.FlowProcessTemplateServiceImpl;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.context.UserContext;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;
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


    @Before
    public void userContextSet() {
        UserContext userContext = new UserContext();
        userContext.setUserId("王峰2");
        userContext.setUserName("王f");
        Context.setCurrentUser(userContext);
    }

    @Test
    public void testStart(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("type",1);

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
    }



    @Test
    public void testLazyProvider(){
        ProcessDefinitionImpl provide = LazyProvider.provide("", new Class[]{FlowProcessTemplate.class},
                new Object[]{null}, id ->
           new ProcessDefinitionImpl(flowProcessTemplateService.getFlowProcessDefinition(id)), ProcessDefinitionImpl.class);
        String codeId = provide.getCodeId();
        FlowNode flowNode = provide.findFlowNode("12");
    }



    public static void main(String[] args) {
       // ProcessDefinitionImpl provide = LazyProvider.provide("1", id -> new ProcessDefinitionImpl(new FlowProcessTemplate()), ProcessDefinitionImpl.class);

    }

    public void ElUtilTest() {

    }


}