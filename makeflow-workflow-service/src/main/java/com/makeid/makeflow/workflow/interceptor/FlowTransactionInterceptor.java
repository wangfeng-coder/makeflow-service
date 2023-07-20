package com.makeid.makeflow.workflow.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-18
 */
@Component
public class FlowTransactionInterceptor extends AbstractCommandInterceptor {
    @Override
    @Transactional
    public <T> T execute(CommandConfig config, Command<T> command) {
        return next.execute(config,command);
    }
}
