package com.makeid.makeflow.workflow.service.impl;

import com.makeid.makeflow.workflow.config.ProcessEngineConfigurationImpl;
import com.makeid.makeflow.workflow.service.RuntimeService;

import java.util.Map;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-30
*/
public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {
    public RuntimeServiceImpl() {
    }

    public RuntimeServiceImpl(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public ProcessInstance startDefiniteProcessInstanceById(String processDefinitionId, String processInstanceId, Map<String, Object> variables) {
        return commandExecutor.execute(new StartDefiniteProcessInstanceCmd(
                processDefinitionId, processInstanceId, variables));
    }
}
