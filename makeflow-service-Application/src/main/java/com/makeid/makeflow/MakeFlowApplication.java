package com.makeid.makeflow;

import com.makeid.makeflow.autoconfig.MakeFlowAutoConfiguration;
import com.makeid.makeflow.autoconfig.MakeFlowSpringStartListener;
import com.makeid.makeflow.template.service.third.FlowTemplateServiceThird;
import com.makeid.makeflow.workflow.service.third.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */
@Slf4j
public class MakeFlowApplication {

    private static ApplicationContext flowContext;

    /**
     *
     * @return 外部操作流程对象
     */
    public static WorkflowService getWorkFlowService() {
        return flowContext.getBean(WorkflowService.class);
    }

    /**
     *
     * @return 外部操作流程模板对象
     */
    public static FlowTemplateServiceThird getFlowProcessTemplateService() {
        return flowContext.getBean(FlowTemplateServiceThird.class);
    }

   public static void setFlowContext(ApplicationContext applicationContext) throws BeansException {
        if(Objects.isNull(flowContext)) {
            flowContext = applicationContext;
        }
    }
}
