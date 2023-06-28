package com.makeid.makeflow.workflow.event.listener;

import com.google.common.eventbus.Subscribe;
import com.makeid.makeflow.template.flow.model.definition.FlowProcessTemplate;
import com.makeid.makeflow.workflow.event.DelegateProcessDefinition;
import com.makeid.makeflow.workflow.event.FlowEventListener;
import com.makeid.makeflow.workflow.event.ProcessCreateBeforeEvent;
import org.springframework.stereotype.Component;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 流程相关
 * @create 2023-06-26
 */
@Component
public class ProcessCreateBeforeEventListener implements FlowEventListener {

    /**
     * 创建流程前 可修改模板定义
     * @param event
     */
    @Subscribe
    public void onEvent(ProcessCreateBeforeEvent event) {
        DelegateProcessDefinition delegateProcessDefinition = event.getDelegateProcessDefinition();
    }

    @Subscribe
    public void onEvent2(ProcessCreateBeforeEvent event) {

    }
}
