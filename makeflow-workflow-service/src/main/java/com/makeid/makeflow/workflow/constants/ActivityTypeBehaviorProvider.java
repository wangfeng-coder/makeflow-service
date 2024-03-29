package com.makeid.makeflow.workflow.constants;

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

 public final static Map<String, ActivityBehavior> activityBehaviorMap = new HashMap<>();

 static {
     activityBehaviorMap.put(ActivityTypeBehaviorEnum.START.activityType
             , ActivityTypeBehaviorEnum.START.activityBehavior);
     activityBehaviorMap.put(ActivityTypeBehaviorEnum.RESTART.activityType
             , ActivityTypeBehaviorEnum.RESTART.activityBehavior);
     activityBehaviorMap.put(ActivityTypeBehaviorEnum.END.activityType
             , ActivityTypeBehaviorEnum.END.activityBehavior);
     activityBehaviorMap.put(ActivityTypeBehaviorEnum.EXCLUSIVE_GATEWAY.activityType
             ,ActivityTypeBehaviorEnum.EXCLUSIVE_GATEWAY.activityBehavior);
     activityBehaviorMap.put(ActivityTypeBehaviorEnum.APPROVAL_USERTASK.activityType
             ,ActivityTypeBehaviorEnum.APPROVAL_USERTASK.activityBehavior);
 }
 public static ActivityBehavior get(String type){
     return activityBehaviorMap.get(type);
 }
}
