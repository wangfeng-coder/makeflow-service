package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.constants.ExecuteStatusEnum;
import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.operation.AtomicOperations;
import com.makeid.makeflow.workflow.process.activity.ActivityImpl;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
@Getter
@Setter
public class ProcessInstanceExecution extends CoreExecution implements  InitialProData {

    ///////////////////////////////
    //当前codeId
    protected String startCodeId;

    protected FlowInstEntity flowInstEntity;

    protected ExecuteEntity executeEntity;

    protected List<ProcessInstanceExecution> children;

    /**
     * 当前正在流转的线，没有流转就为空
     */
    protected TransitionImpl transitionImpl;

    protected ActivityImpl currentActivity;

    public ProcessInstanceExecution(ProcessDefinitionImpl processDefinition) {
        super(processDefinition);
        initData();
    }


    @Override
    public void initData() {
    }

    @Override
    public void start() {
        validateProviderNecessaryData();
        setStartTimeNow();
        activate();
        super.start();
    }

    @Override
    public void take() {
        //获取当前节点
        ActivityImpl preActivity = this.findActivityInst();
        Long id = preActivity.getId();
        ActivityImpl destination = transitionImpl.findDestination();
        setActivityCodeId(destination.getCodeId());
        this.currentActivity = destination;
        currentActivity.setPreActivityId(id);
    }

    private void setStartTimeNow() {
        if(Objects.isNull(this.executeEntity.getStartTime())){
            this.executeEntity.setStartTime(new Date());
        }
    }

    protected void setActivityCodeId(String activityCodeId) {
        this.executeEntity.setActivityCodeId(activityCodeId);
    }

    @Override
    public void take(PvmTransition outgoingTransition) {
        if (outgoingTransition == null) {
            throw new RuntimeException("transition is null");
        }
        this.transitionImpl = (TransitionImpl) outgoingTransition;
        performOperation(AtomicOperations.ACTIVITY_END);
    }

    /**
     * 1、将一些参数放入上下文，后续非流程引擎的数据
     * 都从上下文 Variable中取
     * <p>
     * TODO
     */
    public void initialize() {
        this.executeEntity.setActivityCodeId(startCodeId);
    }


    /**
     * 执行开始前 提供必要数据校验并设置
     */
    private void validateProviderNecessaryData() {
    }








    public ExecuteEntity createExecuteEntity() {
        ExecuteEntity executeEntity = Context.getExecutionService().create(Context.getUserId());
        executeEntity.setStatus(ExecuteStatusEnum.NOT_ACTIVE.status);
        executeEntity.setFlowInstId(this.getFlowInstEntity().getId());
        executeEntity.setVariables(variables);
        executeEntity.setDefinitionId(getProcessDefinitionId());
        executeEntity.setRootFlowInstId(this.getFlowInstEntity().getId());
        this.executeEntity = executeEntity;
        return executeEntity;
    }


    public void saveFlowInstEntity() {
        Context.getFlowInstService().save(flowInstEntity);
    }


    /**
     * 保存执行实列
     *
     * @param
     * @return
     * @author feng_wf
     * @create 2023-05-31
     */
    public void saveExecuteEntity() {
        executeEntity.setVariables(variables);
        executeEntity.setFlowInstId(this.flowInstEntity.getId());
        Context.getExecutionService().save(executeEntity);
    }

    /**
     * 保存流程实列到数据库
     *
     * @param
     * @return
     * @author feng_wf
     * @create 2023-05-31
     */
    public FlowInstEntity createFlowInstEntity() {
        FlowInstEntity flowInstEntity = Context.getFlowInstService().create(Context.getUserId());
        FlowProcessTemplate flowProcessTemplate = processDefinition.findFlowProcessTemplate();
        flowInstEntity.setDefinitionId(flowProcessTemplate.getFlowTemplateId());
        flowInstEntity.setDefinitionCodeId(flowProcessTemplate.getFlowTemplateCodeId());
        flowInstEntity.setStatus(FlowStatusEnum.UNSUBMIT.status);
        flowInstEntity.setApplyTime(new Date());
        flowInstEntity.setApply(Context.getUserId());
        this.flowInstEntity = flowInstEntity;
        return flowInstEntity;
    }

    public ActivityImpl findActivityInst() {
        if (Objects.nonNull(currentActivity)) {
            return currentActivity;
        }
        ActivityImpl activity = new ActivityImpl(executeEntity.getActivityCodeId(), processDefinition);
        this.currentActivity = activity;
        return activity;
    }

    @Override
    public void saveActivityInst() {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public boolean isEnded() {
        return false;
    }

    @Override
    public Long getProcessInstanceId() {
        return this.flowInstEntity.getId();
    }


    private void setTransition(TransitionImpl outgoingTransition) {
        this.transitionImpl = outgoingTransition;
    }


    @Override
    public Long getProcessDefinitionId() {
        return this.getProcessDefinition().getDefinitionId();
    }

    @Override
    public Long rootProcessInstanceId() {

        return null;
    }

    @Override
    public boolean suspended() {
        return false;
    }

    public void end() {
        //更新流程状态 并保存
        this.executeEntity.setStatus(ExecuteStatusEnum.END.status);
        this.executeEntity.setEndTime(new Date());
        this.executeEntity.setVariables(this.getVariables());
        Context.getExecutionService().save(executeEntity);
    }

    @Override
    public void stay() {
        this.executeEntity.setStatus(ExecuteStatusEnum.STAY.status);
        this.executeEntity.setVariables(this.getVariables());
        Context.getExecutionService().save(executeEntity);
    }

    @Override
    public void suspend(String message) {
        super.suspend(message);

    }

    @Override
    public void activate() {
        this.executeEntity.setStatus(ExecuteStatusEnum.ACTIVE.status);
    }

    @Override
    public Long getId() {
        return this.executeEntity.getId();
    }


    @Override
    public Map<String, Object> getVariables() {
        return this.variables;
    }


    public void save() {
        saveFlowInstEntity();
        saveExecuteEntity();
    }

    public void runFlowInst() {
        this.flowInstEntity.setStatus(FlowStatusEnum.RUNNING.status);
        this.flowInstEntity.setStartTime(new Date());
    }

    public Long getFlowInstId() {
        return this.flowInstEntity.getId();
    }

    public void restore(ExecuteEntity executeEntity) {
        this.executeEntity = executeEntity;
        this.currentActivity = findActivityInst();
        this.flowInstEntity = findFlowInst();
        this.addVariables(executeEntity.getVariables());
        this.currentActivity.restore(executeEntity.getActivityId());

    }

    private FlowInstEntity findFlowInst() {
        return Context.getFlowInstService().findById(this.executeEntity.getFlowInstId());
    }

    public void setCurrentActivityId(Long activityId) {
        this.executeEntity.setActivityId(activityId);
    }

    public void fillRunParam() {
        //暂时没有父子关系

    }

    public void endProcessInstance(FlowStatusEnum flowStatusEnum) {
        flowInstEntity.setStatus(flowStatusEnum.status);
        saveFlowInstEntity();
    }
}
