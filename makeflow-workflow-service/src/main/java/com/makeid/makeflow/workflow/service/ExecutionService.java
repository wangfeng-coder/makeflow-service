package com.makeid.makeflow.workflow.service;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.template.service.impl.FlowProcessTemplateServiceImpl;
import com.makeid.makeflow.workflow.dao.ExecuteEntityDao;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.basic.lazy.LazyProvider;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Service
public class ExecutionService {

    @Resource
    private ExecuteEntityDao executeEntityDao;

    @Resource
    private FlowProcessTemplateServiceImpl flowProcessTemplateService;

    public ExecuteEntity create(String userId){
        return (ExecuteEntity) executeEntityDao.create(userId);
    }

    public void save(ExecuteEntity executeEntity) {
        executeEntityDao.save(executeEntity);
    }

    public ExecuteEntity findById(Long s) {
        return (ExecuteEntity) executeEntityDao.findById(s);
    }

    public List<ExecuteEntity> findByParentId(Long parentId) {
        return executeEntityDao.findByParentId(parentId);
    }

    public List<ExecuteEntity> findByIds(List<Long> executionIds) {
        return executeEntityDao.findByIds(executionIds);
    }

    public List<ProcessInstanceExecution> transferExecution(List<ExecuteEntity> executeEntities) {
        //搜集模板
        List<Long> definitionIds = executeEntities.stream().map(ExecuteEntity::getDefinitionId)
                .collect(Collectors.toList());
        List<FlowProcessTemplate> flowProcessTemplates = flowProcessTemplateService.findFlowProcessTemplate(definitionIds);
        Map<Long, FlowProcessTemplate> flowProcessTemplateMap = flowProcessTemplates.stream().collect(Collectors.toMap(FlowProcessTemplate::getFlowTemplateId, Function.identity(), (k1, k2) -> k2));
        List<ProcessInstanceExecution> processInstanceExecutions = new ArrayList<>();
        for (ExecuteEntity executeEntity : executeEntities) {
            ProcessInstanceExecution processInstanceExecution = new ProcessInstanceExecution(new ProcessDefinitionImpl(flowProcessTemplateMap.get(executeEntity.getDefinitionId())));
            processInstanceExecution.restore(executeEntity);
            processInstanceExecutions.add(processInstanceExecution);
        }
        return processInstanceExecutions;
    }

    public ProcessInstanceExecution transferExecution(ExecuteEntity executeEntity) {
        Long definitionId = executeEntity.getDefinitionId();
        FlowProcessTemplate flowProcessDefinition = flowProcessTemplateService.getFlowProcessDefinition(definitionId);
        ProcessInstanceExecution processInstanceExecution = new ProcessInstanceExecution(new ProcessDefinitionImpl(flowProcessDefinition));
        processInstanceExecution.restore(executeEntity);
        return processInstanceExecution;
    }
}
