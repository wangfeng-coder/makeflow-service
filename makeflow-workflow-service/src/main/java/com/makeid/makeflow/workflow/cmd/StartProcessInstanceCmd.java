package com.makeid.makeflow.workflow.cmd;


import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.constants.TaskStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.interceptor.CommandContext;
import com.makeid.makeflow.workflow.process.ProcessInstanceExecution;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionBuilder;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


import java.util.*;

abstract class StartProcessInstanceCmd extends AbstractCommand<ProcessInstanceExecution> {

    private static Logger log = LoggerFactory
            .getLogger(StartProcessInstanceCmd.class);

    private static final long serialVersionUID = 1L;
    protected Long processDefinitionId;
    protected Long processInstanceId;

    public StartProcessInstanceCmd() {
        // TODO Auto-generated constructor stub
    }


    public StartProcessInstanceCmd(Long processDefinitionId,
                                   Long processInstanceId, Map<String, Object> variables) {
        super(variables);
        if (Objects.isNull(processDefinitionId)) {
            throw new IllegalArgumentException("processDefinitionId is blank");
        }
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
    }

    @Override
    public ProcessInstanceExecution execute() {
        log.info(
                "StartProcessInstanceCmd--processDefinitionId:{},processInstanceId:{}",
                processDefinitionId, processInstanceId);
        if (Objects.nonNull(processInstanceId)) {
            ProcessInstanceExecution rootProcessExecution = findRootProcessExecution();
            //完成重新提交任务
            completeRestartTask(rootProcessExecution);
            continueRun(rootProcessExecution);
            return rootProcessExecution;
        } else {
            //获取流程定义
            //传入流程参数
            ProcessDefinitionImpl processDefinition = ProcessDefinitionBuilder.builder()
                    .createProcessDefinition(processDefinitionId)
                    .build();
            final ProcessInstanceExecution processInstanceExecution = (ProcessInstanceExecution) processDefinition.createProcessInstanceExecution(this.getVariables());
            processInstanceExecution.initialize();
            processInstanceExecution.save();
            processInstanceExecution.start();
            return processInstanceExecution;
        }
    }

    private ProcessInstanceExecution findRootProcessExecution() {
        FlowInstEntity flowInst = Context.getFlowInstService().findById(processInstanceId);
        Assert.notNull(flowInst);
        Assert.isTrue(Objects.equals(flowInst.getStatus(), FlowStatusEnum.UNSUBMIT.status));
        Assert.isTrue(Objects.equals(flowInst.getApply(), Context.getUserId()), "当前用户为原申请人");
        List<ExecuteEntity> executeEntities = Context.getExecutionService().findByFlowInstId(processInstanceId);
        ExecuteEntity rootExecuteEntity = executeEntities.stream()
                .filter(executeEntity -> Objects.isNull(executeEntity.getParentId()))
                .findFirst()
                .get();
        return transferProcessInstanceExecution(rootExecuteEntity);
    }

    private void completeRestartTask(ProcessInstanceExecution processInstanceExecution) {
        List<TaskEntity> taskEntities = Context.getTaskService().findFlowInstIdHandlerStatus(processInstanceId, Context.getUserId(), TaskStatusEnum.DOING.status);
        Context.getTaskService().completeTasks(taskEntities, TaskStatusEnum.DONE.status);
        Context.getTaskService().save(taskEntities);
        processInstanceExecution.getFlowInstEntity().setStatus(FlowStatusEnum.RUNNING.status);
    }
}