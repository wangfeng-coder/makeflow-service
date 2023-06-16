package com.makeid.makeflow.workflow.service.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-13
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(value = {"com.makeid.makeflow"})
public class SpringBootTestConfig {

}
