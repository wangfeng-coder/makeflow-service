package com.makeid.makeflow.workflow.config;

import com.makeid.makeflow.workflow.context.Context;
import com.makeid.makeflow.workflow.context.UserContext;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-26
 */
@Configuration
public class ExecutorConfig {

     private static int cpuNum = Runtime.getRuntime().availableProcessors();

    /**
     * 流程的异步操作
     * @return
     */
    @Bean
    public Executor AsyncFlowExecutor() {
         return initExecutor(cpuNum*2,
                 cpuNum*2,
                 1024*cpuNum,
                 new ThreadPoolExecutor.CallerRunsPolicy(),
                 "makeflow-thread-"
                 );
     }


    /**
     * 异步事件处理线程池
     * @return
     */
    @Bean
   public Executor AsyncEventBusExecutor() {
        return initExecutor(cpuNum+1
                ,cpuNum+1
                ,1024*cpuNum
                ,new ThreadPoolExecutor.CallerRunsPolicy()
                ,"flowEventBus-thread-");
    }

    private Executor initExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, RejectedExecutionHandler rejectedExecutionHandler, String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        executor.setTaskDecorator(new ContextCopyDecorator());
        return executor;
    }

    class ContextCopyDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            UserContext currentUser = Context.getCurrentUser();
            MDCAdapter mdcAdapter = MDC.getMDCAdapter();
            Map<String, String> copyOfContextMap = mdcAdapter.getCopyOfContextMap();
            return ()->{
                try{
                    Context.setCurrentUser(currentUser);
                    MDC.setContextMap(copyOfContextMap);
                    runnable.run();
                }finally {
                    Context.removeCurrentUser();
                    MDC.clear();
                }
            };
        }
    }
}
