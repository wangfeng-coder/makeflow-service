package com.makeid.makeflow.template.service.impl;

import com.makeid.makeflow.template.bpmn.conventer.BpmnXMLConverter;
import com.makeid.makeflow.template.bpmn.model.*;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        processId = "simple";
        String modelPath = "simple.bpmn20.xml";
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
            //处理元素 节点填充线条
            processFlowElement(flowElements);
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

    private void processFlowElement(List<FlowElement> flowElements) {
        Map<String, FlowElement> flowElementMap = flowElements
                .stream()
                .collect(Collectors.toMap(FlowElement::getId, Function.identity(), (k1, k2) -> k2));
        for (FlowElement flowElement : flowElements) {
            if(flowElement instanceof SequenceFlow) {
                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                String sourceRef = sequenceFlow.getSourceRef();
                String targetRef = sequenceFlow.getTargetRef();
                //出
                FlowElement out = flowElementMap.get(sourceRef);
                if(out instanceof FlowNode) {
                    FlowNode flowNode = ((FlowNode) out);
                    if(Objects.isNull(flowNode.getOutgoingFlows())) {
                        flowNode.setOutgoingFlows(new ArrayList<>());
                    }
                    flowNode.getOutgoingFlows().add(sequenceFlow);
                }
                //进 我们流程其实并不关心进
                FlowElement in  = flowElementMap.get(targetRef);
                if(in instanceof FlowNode) {
                    FlowNode flowNode = ((FlowNode) in);
                    if(Objects.isNull(flowNode.getIncomingFlows())) {
                        flowNode.setIncomingFlows(new ArrayList<>());
                    }
                    flowNode.getIncomingFlows().add(sequenceFlow);
                }
            }
        }
    }

    public List<FlowProcessTemplate> findFlowProcessTemplate(List<String> definitionIds) {
        // todo
        return  Arrays.asList(getFlowProcessDefinition(definitionIds.get(0)));

    }
}
