package com.makeid.makeflow.workflow.constants;

import com.makeid.makeflow.template.flow.model.activity.ActivityTypeEnum;
import com.makeid.makeflow.template.flow.model.activity.ApprovalTaskActivity;
import com.makeid.makeflow.template.flow.model.activity.EndActivity;
import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.template.flow.model.base.FlowNode;
import com.makeid.makeflow.template.flow.model.gateway.ExclusiveGateway;
import com.makeid.makeflow.workflow.behavior.ActivityBehavior;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-12
 */
public abstract class ActivityTypeBehaviorProvider {

 public final static Map<Class<? extends FlowNode>, ActivityBehavior> activityBehaviorMap = new HashMap<>();

 static {
     activityBehaviorMap.put(StartActivity.class
             , ActivityTypeBehaviorEnum.START.activityBehavior);
     activityBehaviorMap.put(EndActivity.class
             , ActivityTypeBehaviorEnum.END.activityBehavior);
     activityBehaviorMap.put(ExclusiveGateway.class
             ,ActivityTypeBehaviorEnum.EXCLUSIVE_GATEWAY.activityBehavior);
     activityBehaviorMap.put(ApprovalTaskActivity.class
             ,ActivityTypeBehaviorEnum.APPROVAL_USERTASK.activityBehavior);
 }
 public static ActivityBehavior get(Class<? extends FlowNode> type){
     return activityBehaviorMap.get(type);
 }
}
