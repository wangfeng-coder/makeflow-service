package com.makeid.makeflow.workflow.process.activity;

import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.sequence.SequenceFlow;
import com.makeid.makeflow.workflow.behavior.ActivityBehavior;
import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.constants.ActivityTypeBehaviorProvider;
import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.entity.ExecuteEntity;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.process.*;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.makeid.makeflow.workflow.process.TransitionImpl.transferFrom;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-31
*/
@Setter
@Getter
public class ActivityImpl extends ScopeImpl implements PvmActivity, InitialProData {

    protected String activityType;

    protected String preActivityId;

    protected FlowNode flowNode;

    /**
     * 构造的时候会设置对应行为 一定不会为空
     */
    protected ActivityBehavior activityBehavior;

    //通过模板定义拿到
    protected List<TransitionImpl> outGoingTransitions;

    //对应的数据库实例
    protected ActivityEntity activity;

    public ActivityImpl(String activityCodeId, ProcessDefinitionImpl processDefinition) {
        super(activityCodeId, processDefinition);
        initData();
    }


    @Override
    public TransitionImpl findDefaultOutgoingTransition() {
        throw new EngineException("暂时没支持默认路线");
    }

    @Override
    public List<TransitionImpl> findOutgoingTransitions() {
        if(CollectionUtils.isEmpty(outGoingTransitions)) {
            //通过定义寻找出线
            String activityCodeId = getCodeId();
            FlowNode flowNode = processDefinition.findFlowNode(activityCodeId);
            List<SequenceFlow> outgoingFlows = flowNode.getOutgoingFlows();
            List<TransitionImpl> outGoingTransitions = outgoingFlows.stream().map(seq -> transferFrom(seq, processDefinition))
                    .collect(Collectors.toList());
            if (Objects.nonNull(this.outGoingTransitions)) {
                this.outGoingTransitions.addAll(outGoingTransitions);
            }
        }
        //转换成对应实列
        return outGoingTransitions;
    }

    @Override
    public ActivityBehavior findActivityBehavior() {
        return activityBehavior;
    }

    @Override
    public boolean isStartActivity() {
        return this.activityType.equals(ElementTypeEnum.ACTIVITYTYPE_START.getContext());
    }

    @Override
    public String getId() {
        return this.activity.getId();
    }

    @Override
    public void initData() {
        this.flowNode = findFlowNode(this.codeId);
        this.outGoingTransitions = new ArrayList<>();
        this.activityType = flowNode.getElementType();
        this.activityBehavior = ActivityTypeBehaviorProvider.get(flowNode.getClass());
    }



    @Override
    public boolean isActivity() {
        return true;
    }

    @Override
    public boolean isTransition() {
        return false;
    }

    public void save() {
        // TODO 保存到数据库
        Context.getActivityService().save(activity);
    }

    public ActivityEntity create(ProcessInstancePvmExecution execution) {
         ActivityEntity activityEntity = Context.getGlobalProcessEngineConfiguration().getActivityService()
                .create();
         activityEntity.setActivityCodeId(this.getCodeId());
         activityEntity.setActivityType(this.activityType);
         activityEntity.setDefinitionId(getProcessDefinition().getDefinitionId());
         activityEntity.setPreActivityId(getPreActivityId());
         activityEntity.setName(flowNode.getName());
         activityEntity.setStatus(ActivityStatusEnum.NOT_INIT.status);
         activityEntity.setFlowInstId(execution.getFLowInstId());
         activityEntity.setExecutionId(execution.getId());
         this.activity = activityEntity;
         return activityEntity;
    }

    public void start() {
        activity.setStartTime(new Date());
        activity.setStatus(ActivityStatusEnum.RUNNING.status);
    }

    public void end() {
        activity.setEndTime(new Date());
        activity.setStatus(ActivityStatusEnum.COMPLETE.status);
    }


    @Override
    public TransitionImpl findTransition(String codeId) {
        return null;
    }


    public boolean isEndActivity() {
        return this.activityType.equals(ElementTypeEnum.ACTIVITYTYPE_END.getContext());
    }
}
