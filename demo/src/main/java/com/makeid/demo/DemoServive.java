package com.makeid.demo;

import com.makeid.makeflow.MakeFlowApplication;
import com.makeid.makeflow.template.service.third.FlowTemplateServiceThird;
import com.makeid.makeflow.workflow.service.third.WorkflowServiceThird;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-11
 */
@Service
public class DemoServive {

    String codeId = "质差333";

    public Long publish() {
        String codeId = "质差333";
        String xml = xml();
        String name = "模板名字";
        String userId = "当前用户";
        FlowTemplateServiceThird flowProcessTemplateService = MakeFlowApplication.getFlowProcessTemplateService();
        Long publish = flowProcessTemplateService.publish(xml, codeId, name, userId);
        return publish;
    }

    public Long apply(){
        WorkflowServiceThird workFlowService = MakeFlowApplication.getWorkFlowService();
        Long s = workFlowService.submitFlow(codeId, "wangfeng", new HashMap<>());
        return s;
    }


    private String xml(){
        InputStream resourceAsStream = DemoServive.class.getClassLoader().getResourceAsStream("test.xml");
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        try(BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            Stream<String> lines = bufferedReader.lines();
            StringBuilder stringBuilder =new StringBuilder();
            lines.forEach(stringBuilder::append);
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
