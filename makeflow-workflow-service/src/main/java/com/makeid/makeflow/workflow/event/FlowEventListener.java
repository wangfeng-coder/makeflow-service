package com.makeid.makeflow.workflow.event;

import com.google.common.eventbus.Subscribe;
import org.springframework.core.Ordered;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-26
 */
public interface FlowEventListener extends Ordered {

    /**
     * 注册顺序
     * @return
     */
    @Override
    default int getOrder() {
        return 0;
    }


}
