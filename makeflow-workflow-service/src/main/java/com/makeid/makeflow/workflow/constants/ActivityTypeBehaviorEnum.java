package com.makeid.makeflow.workflow.constants;

import com.makeid.makeflow.workflow.behavior.*;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-08
 */
public enum ActivityTypeBehaviorEnum {
    START("start",new StartActivityBehavior()),

    END("end",new EndActivityBehavior()),

    APPROVAL_USERTASK("approvalUserTask",new ApprovalTaskActivityBehavior()),

    EXCLUSIVE_GATEWAY("exclusivegateway",new ExclusiveGatewayActivityBehavior());

    public final String activityType;

    public final ActivityBehavior activityBehavior;

    ActivityTypeBehaviorEnum(String activityType, ActivityBehavior activityBehavior) {
        this.activityType = activityType;
        this.activityBehavior = activityBehavior;
    }
}
