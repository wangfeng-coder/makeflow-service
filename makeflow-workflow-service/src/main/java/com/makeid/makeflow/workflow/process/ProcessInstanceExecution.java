package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.template.flow.model.activity.BaseActivity;
import com.makeid.makeflow.template.flow.model.base.FlowElement;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.constants.ExecuteStatusEnum;
import com.makeid.makeflow.workflow.constants.FlowStatusEnum;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.entity.FlowInstEntity;
import com.makeid.makeflow.workflow.entity.impl.ExecuteEntityImpl;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.operation.AtomicOperation;
import com.makeid.makeflow.workflow.operation.AtomicOperations;
import com.makeid.makeflow.workflow.runtime.ActivityExecution;
import com.makeid.makeflow.workflow.runtime.IdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Getter
@Setter
public class ProcessInstanceExecution extends ExecuteEntityImpl implements ActivityExecution ,InitialProData{


    private String currentElementCodeId;

    /**
     * 流程定义实列
     */
    private ProcessDefinitionInst processDefinitionInst;

    /**
     * 数据库实流程实列
     */
    private FlowInstEntity flowInstEntity;

    /**
     * 数据库实列
     */
    private ExecuteEntity executeEntity;

    private ProcessInstanceExecution parentExecution;

    /**
     * 设计时
     */
    private FlowElement curentFlowElement;

    private IdGenerator idGenerator;

    private List<ProcessInstanceExecution> children;

    private BaseActivityInst currentActivityInst;

    public ProcessInstanceExecution() {
    }

    @Override
    public void initData() {
        this.children = new LinkedList<>();
    }

    @Override
    public String getName() {
        return null;
    }


    @Override
    public void start() {
        validateProviderNecessaryData();

        initialize();

        fireProcessStartEvent();

        //执行流程运转操作
        performOperation(AtomicOperations.process_start);


    }

    private void performOperation(AtomicOperation processStart) {
        processStart.execute(this);
    }

    /**
     * 1、将一些参数放入上下文，后续非流程引擎的数据
     * 都从上下文 Variable中取
     *
     * TODO
     */
    public void initialize() {

    }

    /**
     * 发出流程开始时间
     */

    public void fireProcessStartEvent() {

    }


    /**
     * 执行开始前 提供必要数据校验并设置
     */
    private void validateProviderNecessaryData() {
        if (StringUtils.isNotEmpty(getParentId())) {
            setRootFlowInstId(getParentExecution().getRootFlowInstId());
        }
    }


    @Override
    public void execute() {

    }

    @Override
    public void end() {

    }

    @Override
    public void stay() {

    }

    @Override
    public void suspend(String message) {

    }

    @Override
    public void activate() {

    }

    @Override
    public void inactivate() {

    }


    public ProcessInstanceExecution createChildrenExecution() {
        ProcessInstanceExecution children = new ProcessInstanceExecution();
        children.setId(idGenerator.getNextId());
        children.setStartTime(new Date());
        children.setParentId(this.id);
        children.setStatus(ExecuteStatusEnum.ACTIVE.status);
        children.setProcessDefinitionId(this.getProcessDefinitionId());
        children.setRootFlowInstId(this.getRootFlowInstId());
        children.setFlowInstId(this.getFlowInstId());
        this.children.add(children);
        return children;
    }

    public void setCurrentElement(FlowElement flowElement) {
        this.setCurrentElementCodeId(flowElement.getCodeId());
        this.setCurrentElement(flowElement);
    }



    public void save() {
        //保存流程实列到数据库
        saveFlowInst();
        //保存执行实列
        saveExecutionInst();
    }



    /**
     * 保存执行实列
     * @param
     * @return
     * @author feng_wf
     * @create 2023-05-31
     */
    private void saveExecutionInst() {
        ExecuteEntity executeEntity = Context.getExecutionService().create();
        executeEntity.setFlowInstId(flowInstEntity.getId());
        executeEntity.setStatus(ExecuteStatusEnum.ACTIVE.status);
        Context.getExecutionService().save(executeEntity);
        this.executeEntity = executeEntity;
    }

    /**
     * 保存流程实列到数据库
     * @param
     * @return
     * @author feng_wf
     * @create 2023-05-31
     */
    private void saveFlowInst() {
        FlowInstEntity flowInstEntity = Context.getFlowInstService().create();
        FlowProcessTemplate flowProcessTemplate = processDefinitionInst.getFlowProcessTemplate();
        flowInstEntity.setId(idGenerator.getNextId());
        flowInstEntity.setTemplateCodeId(flowProcessTemplate.getFlowTemplateCodeId());
        flowInstEntity.setTemplateId(flowProcessTemplate.getFlowTemplateId());
        flowInstEntity.setStatus(FlowStatusEnum.RUNNING.status);
        flowInstEntity.setCreateTime(new Date());
        flowInstEntity.setUpdateTime(new Date());
        Context.getFlowInstService().save(flowInstEntity);
        this.flowInstId = flowInstEntity.getId();
        this.rootFlowInstId = flowInstEntity.getId();
        this.flowInstEntity = flowInstEntity;
    }

    @Override
    public BaseActivityInst findActivityInst() {
        if(Objects.nonNull(currentActivityInst)) {
            return currentActivityInst;
        }
        if (Objects.nonNull(curentFlowElement)) {
            if (!(curentFlowElement instanceof BaseActivity)) {
                throw new EngineException("");
                //类型异常
            }
        }
        curentFlowElement.
        return null;
    }

    @Override
    public void saveActivityInst() {

    }
}
