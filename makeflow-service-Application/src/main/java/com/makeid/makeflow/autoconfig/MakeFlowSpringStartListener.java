package com.makeid.makeflow.autoconfig;

import com.makeid.makeflow.MakeFlowApplication;
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
        log.info("【make flow 容器已经启动完成】");
    }

}
