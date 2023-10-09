package com.makeid.makeflow.autoconfig;

import com.makeid.makeflow.MakeFlowApplication;
import com.makeid.makeflow.workflow.event.FlowEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-11
 */
@Slf4j
public class MakeFlowSpringStartListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        SpringApplication springApplication = new SpringApplication(MakeFlowAutoConfiguration.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext applicationContext = springApplication.run();
        MakeFlowApplication.setFlowContext(applicationContext);
        ConfigurableApplicationContext mainSpring = event.getApplicationContext();
        Map<String, FlowEventListener> listenerMap = mainSpring.getBeansOfType(FlowEventListener.class);
        List<FlowEventListener> flowEventListenerList = listenerMap.values()
                .stream()
                .sorted(Comparator.comparingInt(FlowEventListener::getOrder).reversed())
                .collect(Collectors.toList());
        log.info("扫描到外部flowEventListener 【{}】",flowEventListenerList.size());
        MakeFlowApplication.registerMakeFLowEventListener(flowEventListenerList);
        log.info("【make flow 容器已经启动完成】");
    }

}
