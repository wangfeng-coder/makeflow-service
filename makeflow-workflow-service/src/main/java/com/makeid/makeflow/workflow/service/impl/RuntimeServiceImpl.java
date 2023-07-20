package com.makeid.makeflow.workflow.service.impl;

import com.makeid.makeflow.workflow.cmd.AgreeTaskCmd;
import com.makeid.makeflow.workflow.cmd.DisAgreeCmd;
import com.makeid.makeflow.workflow.cmd.ReturnCmd;
import com.makeid.makeflow.workflow.cmd.StartDefiniteProcessInstanceCmd;
import com.makeid.makeflow.workflow.config.ProcessEngineConfigurationImpl;
import com.makeid.makeflow.workflow.runtime.PvmProcessInstance;
import com.makeid.makeflow.workflow.service.RuntimeService;
import org.springframework.stereotype.Service;


import java.util.Map;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-30
*/
@Service
public class RuntimeServiceImpl extends ServiceImpl implements RuntimeService {
    public RuntimeServiceImpl() {
    }

    public RuntimeServiceImpl(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public PvmProcessInstance startDefiniteProcessInstanceById(Long processDefinitionId, Long processInstanceId, Map<String, Object> variables) {
        return commandExecutor.execute(new StartDefiniteProcessInstanceCmd(
                processDefinitionId, processInstanceId, variables));
    }

    @Override
    public void agreeTask(Long taskId,String opinion ,Map<String, Object> variables) {
        commandExecutor.execute(new AgreeTaskCmd(taskId,opinion,variables));
    }

    @Override
    public void disAgreeTask(Long taskId, String opinion, Map<String, Object> variables) {
        commandExecutor.execute(new DisAgreeCmd(variables,taskId,opinion));
    }

    @Override
    public void returnTask(Long taskId, String opinion, String targetCodeId, Map<String, Object> variables) {
        commandExecutor.execute(new ReturnCmd(taskId,variables,targetCodeId,opinion));
    }
}
