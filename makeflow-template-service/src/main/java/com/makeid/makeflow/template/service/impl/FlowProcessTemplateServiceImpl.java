package com.makeid.makeflow.template.service.impl;

import com.makeid.makeflow.basic.utils.RedisUtils;
import com.makeid.makeflow.template.bpmn.conventer.BpmnXMLConverter;
import com.makeid.makeflow.template.bpmn.model.Process;
import com.makeid.makeflow.template.bpmn.model.*;
import com.makeid.makeflow.template.constants.ErrCodeEnum;
import com.makeid.makeflow.template.dao.FlowProcessTemplateDao;
import com.makeid.makeflow.template.entity.FlowProcessTemplateEntity;
import com.makeid.makeflow.template.exception.TemplateException;
import com.makeid.makeflow.template.flow.model.base.Element;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.flow.translate.TranslatorHelper;
import com.makeid.makeflow.template.flow.translate.Translators;
import com.makeid.makeflow.template.service.FlowProcessTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
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
@Slf4j
@Service
public class FlowProcessTemplateServiceImpl implements FlowProcessTemplateService {

    @Resource
    private FlowProcessTemplateDao flowProcessTemplateDao;

    @Override
    public FlowProcessTemplate getFlowProcessDefinition(String processId) {
        FlowProcessTemplateEntity flowProcessTemplateDO= (FlowProcessTemplateEntity) flowProcessTemplateDao.findById(processId);
        return from(flowProcessTemplateDO);
    }

    @Override
    public FlowProcessTemplate findFlowProcessTemplateLastly(String codeId) {
        FlowProcessTemplateEntity flowProcessTemplateEntity = flowProcessTemplateDao.findLatestByCodeId(codeId);
        return from(flowProcessTemplateEntity);
    }

    @Override
    public FlowProcessTemplateEntity publish(String xml, String templateCodeId, String templateName,String userId) {
        String lockKey = "makeflow:flowProcessTemplate:lock:".concat(templateCodeId);
        if(RedisUtils.setIfAbsent(templateCodeId,lockKey,5)){
            try {
                FlowProcessTemplateEntity flowProcessTemplateEntity =  flowProcessTemplateDao.findLatestByCodeId(templateCodeId);
                FlowProcessTemplateEntity newTemplate = (FlowProcessTemplateEntity) flowProcessTemplateDao.create(userId);
                newTemplate.setName(templateName);
                newTemplate.setTemplateData(xml);
                newTemplate.setDataType("xml");
                newTemplate.setFlowProcessCodeId(templateCodeId);
                newTemplate.setState("1");
                newTemplate.setMax(true);
                newTemplate.setVersion(Objects.isNull(flowProcessTemplateEntity) ? 1 : flowProcessTemplateEntity.getVersion()+1);
                if (Objects.nonNull(flowProcessTemplateEntity)) {
                    flowProcessTemplateEntity.setMax(false);
                    flowProcessTemplateDao.save(flowProcessTemplateEntity);
                }
                flowProcessTemplateDao.save(newTemplate);
                return newTemplate;
            }finally {
                RedisUtils.delete(lockKey);
            }
        }else {
            //TODO
            throw new RuntimeException();
        }

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
        return  Arrays.asList(getFlowProcessDefinition(definitionIds.get(0)));
    }

    private FlowProcessTemplate from(FlowProcessTemplateEntity flowProcessTemplateDO) {
        if(Objects.isNull(flowProcessTemplateDO)) {
            throw new TemplateException(ErrCodeEnum.TEMPLATE_NOT_EXIST);
        }
        String templateData = flowProcessTemplateDO.getTemplateData();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(templateData.getBytes());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8);
        XMLStreamReader xtr = null;
        try {
            xtr = xif.createXMLStreamReader(in);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
        Process processById = bpmnModel.getOneProcess();
        List<FlowElement> flowElements = (List<FlowElement>) processById.getFlowElements();
        //处理元素 节点填充线条
        processFlowElement(flowElements);
        List<Element> targetList = TranslatorHelper.toList(flowElements, Translators::translate);
        FlowProcessTemplate flowProcessTemplate = new FlowProcessTemplate();
        flowProcessTemplate.setFlowTemplateCodeId(flowProcessTemplateDO.getId());
        flowProcessTemplate.setFlowTemplateId(flowProcessTemplateDO.getId());
        flowProcessTemplate.setVersion(flowProcessTemplateDO.getVersion());
        flowProcessTemplate.setState(flowProcessTemplateDO.getState());
        flowProcessTemplate.setElements(targetList);
        return flowProcessTemplate;
    }

}
