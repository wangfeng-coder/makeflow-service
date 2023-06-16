package com.makeid.makeflow.workflow.service.impl;

import com.makeid.makeflow.workflow.service.RuntimeService;
import com.makeid.makeflow.workflow.service.config.SpringBootTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootTestConfig.class)
@RunWith(SpringRunner.class)
public class RuntimeServiceImplTest {

    @Autowired
    private RuntimeService runtimeService;


    @Test
    public void testEngine(){
        System.out.println();
        runtimeService.startDefiniteProcessInstanceById("hiddenDangerOrderCommunity","", Collections.EMPTY_MAP);
    }
  
}