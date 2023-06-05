package com.makeid.makeflow.workflow.process.conventer;

import com.makeid.makeflow.template.flow.model.activity.StartActivity;
import com.makeid.makeflow.workflow.process.StartActivityInst;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 开始节点转换器
 * @create 2023-06-05
 */
public class StartActivityInstConverter implements Converter<StartActivity, StartActivityInst> {
    @Override
    public StartActivityInst convert(StartActivity node) {
        StartActivityInst startActivityInst = new StartActivityInst();
        startActivityInst.setName(node.getName());
        startActivityInst.setStartActivity(node);
        startActivityInst.setId();

        return startActivityInst;
    }
}
