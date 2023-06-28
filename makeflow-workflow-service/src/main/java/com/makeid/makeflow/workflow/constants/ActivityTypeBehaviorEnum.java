package com.makeid.makeflow.workflow.constants;

import com.makeid.makeflow.template.flow.model.base.ElementTypeEnum;
import com.makeid.makeflow.workflow.behavior.*;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-08
 */
public enum ActivityTypeBehaviorEnum {


    START(ElementTypeEnum.ACTIVITYTYPE_START.getType(),new StartActivityBehavior()),

    /**
     * 模型设计时没有 restart节点，但是我们在退回测回会生成restart实列
     *
     */
    RESTART("restart",new RestartActivityBehavior()),

    END(ElementTypeEnum.ACTIVITYTYPE_END.getType(), new EndActivityBehavior()),

    APPROVAL_USERTASK(ElementTypeEnum.ACTIVITYTYPE_MULTIAPPROVAL.getType(), new ApprovalTaskActivityBehavior()),

    EXCLUSIVE_GATEWAY(ElementTypeEnum.EXCLUSIVE_GATEWAY.getType(),new ExclusiveGatewayActivityBehavior());

    public final String activityType;

    public final ActivityBehavior activityBehavior;

    ActivityTypeBehaviorEnum(String activityType, ActivityBehavior activityBehavior) {
        this.activityType = activityType;
        this.activityBehavior = activityBehavior;
    }
}
