/*
package com.makeid.makeflow.workflow.process.conventer;

import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.workflow.behavior.StartActivityBehavior;
import com.makeid.makeflow.workflow.constants.ActivityStatusEnum;
import com.makeid.makeflow.workflow.constants.ActivityTypeBehaviorEnum;
import com.makeid.makeflow.workflow.process.activity.StartActivityInst;

*/
/**
 * @author feng_wf
 * @program makeflow-service
 * @description 开始节点转换器
 * @create 2023-06-05
 *//*

public class StartActivityInstConverter implements ActivityConverter<StartActivity, StartActivityInst> {
    @Override
    public StartActivityInst convert(StartActivity node) {
        StartActivityInst startActivityInst = new StartActivityInst();
        //实列id重新生成
       // startActivityInst.setId();
        startActivityInst.setName(node.getName());
        startActivityInst.setActivityCodeId(node.getCodeId());
        startActivityInst.setStatus(ActivityStatusEnum.NOT_INIT.status);
        startActivityInst.setFlowNode(node);
        startActivityInst.setActivityBehavior(ActivityTypeBehaviorEnum.START.activityBehavior);
        return startActivityInst;
    }
}
*/
