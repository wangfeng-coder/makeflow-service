package com.makeid.makeflow.workflow.service.impl;

import com.makeid.makeflow.template.util.ElUtil;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.context.UserContext;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;
import com.makeid.makeflow.workflow.service.RuntimeService;
import com.makeid.makeflow.workflow.service.config.SpringBootTestConfig;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootTestConfig.class)
@RunWith(SpringRunner.class)
public class RuntimeServiceImplTest {

    @Autowired
    private RuntimeService runtimeService;


    @Test
    public void testEngine(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("type",1);
        UserContext userContext = new UserContext();
        userContext.setUserId("wangfeng");
        userContext.setUserName("王f");
        Context.setCurrentUser(userContext);
        PvmProcessInstance hiddenDangerOrderCommunity = runtimeService.startDefiniteProcessInstanceById("hiddenDangerOrderCommunity", "", objectObjectHashMap);
        String processInstanceId = hiddenDangerOrderCommunity.getProcessInstanceId();
        System.out.println(processInstanceId);
    }

    public static void main(String[] args) {
        String el = "${user}";
        ElUtil.isELExpression(el);
    }

    public void ElUtilTest() {

    }


}