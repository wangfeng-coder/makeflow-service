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

    /**
     * 注册类型：0-同时注册到同步，异步总线，1-只注册到同步总线，2-只注册到异步总线
     * @return
     */
   default int registerType(){
       return 0;
   }

}
