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
import com.makeid.makeflow.workflow.runtime.IdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
public class ProcessInstancePvmExecution extends CoreExecution implements  InitialProData,ExecuteEntity {


    protected Date createTime;

    protected Date updateTime;

    protected Date startTime;

    protected Date endTime;

    protected String flowInstId;

    protected String rootFlowInstId;

    protected int status;

    protected String activityCodeId;

    protected String parentId;

    protected String activityId;

    protected String processDefinitionId;

    protected boolean delete;

    protected String creator;

    ///////////////////////////////
    private IdGenerator idGenerator;

    private FlowInstEntity flowInstEntity;


    private List<ProcessInstancePvmExecution> children;

    /**
     * 当前正在流转的线，没有流转就为空
     */
    protected TransitionImpl transitionImpl;

    protected ActivityImpl currentActivity;

    public ProcessInstancePvmExecution(ProcessDefinitionImpl processDefinition) {
        super(null, null, processDefinition);
    }



    @Override
    public void initData() {
    }

    @Override
    public String getName() {
        return null;
    }


    @Override
    public void start() {
        validateProviderNecessaryData();
        initialize();
        super.start();
    }

    @Override
    public void take() {
        //获取当前节点
        ActivityImpl preActivity = this.findActivityInst();
        this.currentActivity = transitionImpl.findDestination();
        //保存更新节点信息
        preActivity.setNextCodeId(currentActivity.getCodeId());
        currentActivity.setPreCodeId(preActivity.getCodeId());
        preActivity.save();
        currentActivity.save();
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

    }


    /**
     * 执行开始前 提供必要数据校验并设置
     */
    private void validateProviderNecessaryData() {
        if (StringUtils.isNotEmpty(getParentId())) {
        }
    }






    public void save() {
        //保存流程实列到数据库
        createFlowInst();
        //保存执行实列
        saveExecutionInst();
    }


    /**
     * 保存执行实列
     *
     * @param
     * @return
     * @author feng_wf
     * @create 2023-05-31
     */
    private void saveExecutionInst() {
        Context.getExecutionService().save(this);
    }

    /**
     * 保存流程实列到数据库
     *
     * @param
     * @return
     * @author feng_wf
     * @create 2023-05-31
     */
    private void createFlowInst() {
        FlowInstEntity flowInstEntity = Context.getFlowInstService().create();
        FlowProcessTemplate flowProcessTemplate = processDefinition.findFlowProcessTemplate();
        flowInstEntity.setId(idGenerator.getNextId());
        flowInstEntity.setTemplateCodeId(flowProcessTemplate.getFlowTemplateCodeId());
        flowInstEntity.setTemplateId(flowProcessTemplate.getFlowTemplateId());
        flowInstEntity.setStatus(FlowStatusEnum.UNSUBMIT.status);
        flowInstEntity.setCreateTime(new Date());
        flowInstEntity.setUpdateTime(new Date());
        Context.getFlowInstService().save(flowInstEntity);
        this.flowInstId = flowInstEntity.getId();
        this.rootFlowInstId = flowInstEntity.getId();
        this.flowInstEntity = flowInstEntity;
    }

    public ActivityImpl findActivityInst() {
        if (Objects.nonNull(currentActivity)) {
            return currentActivity;
        }
        ActivityImpl activity = new ActivityImpl(this.activityId, activityCodeId, processDefinition);
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
    public String getProcessInstanceId() {
        return null;
    }


    private void setTransition(TransitionImpl outgoingTransition) {
        this.transitionImpl = outgoingTransition;
    }




    @Override
    public String rootProcessInstanceId() {
        return rootFlowInstId;
    }

    @Override
    public boolean suspended() {
        return false;
    }

    public void end() {
        //更新流程状态 并保存
        this.status = ExecuteStatusEnum.END.status;
        this.endTime = new Date();
        Context.getExecutionService().save(this);
        flowInstEntity.setStatus(FlowStatusEnum.END.status);
        //保存流程实列
        Context.getFlowInstService().save(flowInstEntity);
    }

    @Override
    public Map<String, Object> getVariables() {
        return this.variables;
    }
}
