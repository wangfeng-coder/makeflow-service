package com.makeid.makeflow.template.service.impl;

import com.makeid.makeflow.template.bpmn.conventer.BpmnXMLConverter;
import com.makeid.makeflow.template.bpmn.model.BpmnModel;
import com.makeid.makeflow.template.bpmn.model.FlowElement;
import com.makeid.makeflow.template.bpmn.model.Process;
import com.makeid.makeflow.template.flow.model.base.Element;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.flow.translate.TranslatorHelper;
import com.makeid.makeflow.template.flow.translate.Translators;
import com.makeid.makeflow.template.service.FlowProcessTemplateService;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 流程模板获取
 * @create 2023-06-13
 */
@Service
public class FlowProcessTemplateServiceImpl implements FlowProcessTemplateService {

    @Override
    public FlowProcessTemplate getFlowProcessDefinition(String processId) {
        // 获取模型 便于测试 测试代码
        String modelPath = "test.xml";
        FlowProcessTemplate flowProcessTemplate = new FlowProcessTemplate();
        flowProcessTemplate.setFlowTemplateId(processId);
        flowProcessTemplate.setName("test");
        flowProcessTemplate.setFlowTemplateCodeId("");
        flowProcessTemplate.setState("1");
        try (InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream(
                modelPath)) {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in = new InputStreamReader(xmlStream, StandardCharsets.UTF_8);
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
            Process processById = bpmnModel.getProcessById(processId);
            List<FlowElement> flowElements = (List<FlowElement>) processById.getFlowElements();
            List<Element> targetList = TranslatorHelper.toList(flowElements, Translators::translate);
            flowProcessTemplate.setFlowTemplateCodeId(processId);
            flowProcessTemplate.setElements(targetList);
            flowProcessTemplate.setVersion(1);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

        return flowProcessTemplate;
    }
}
