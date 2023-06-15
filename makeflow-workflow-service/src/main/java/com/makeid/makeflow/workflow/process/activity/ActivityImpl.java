package com.makeid.makeflow.workflow.process.activity;

import com.makeid.makeflow.template.flow.model.activity.ActivityTypeEnum;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.sequence.SequenceFlow;
import com.makeid.makeflow.workflow.behavior.ActivityBehavior;
import com.makeid.makeflow.workflow.constants.ActivityTypeBehaviorProvider;
import com.makeid.makeflow.workflow.entity.ActivityEntity;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.process.InitialProData;
import com.makeid.makeflow.workflow.process.PvmActivity;
import com.makeid.makeflow.workflow.process.ScopeImpl;
import com.makeid.makeflow.workflow.process.TransitionImpl;
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


    protected String activityCodeId;

    protected int status;

    protected Date endTime;

    protected Date startTime;

    protected String preCodeId;

    protected String nextCodeId;

    protected String activityType;


    /**
     * 构造的时候会设置对应行为 一定不会为空
     */
    protected ActivityBehavior activityBehavior;

    //通过模板定义拿到
    protected List<TransitionImpl> outGoingTransitions;

    public ActivityImpl(String id,String activityCodeId, ProcessDefinitionImpl processDefinition) {
        super(id,activityCodeId, processDefinition);
        initData();
    }


    @Override
    public TransitionImpl findDefaultOutgoingTransition() {
        throw new EngineException("暂时不用");
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
        return this.activityType.equals(ActivityTypeEnum.START.type);
    }

    @Override
    public void initData() {
        this.outGoingTransitions = new ArrayList<>();
        this.activityBehavior = ActivityTypeBehaviorProvider.get(this.findFlowNode(codeId).getClass());
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
    }


    @Override
    public TransitionImpl findTransition(String codeId) {
        return null;
    }


    public boolean isEndActivity() {
        return this.activityType.equals(ActivityTypeEnum.END);
    }
}
