package com.makeid.makeflow.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */
@Configuration
@ConfigurationProperties(prefix = "makeflow.config")
public class FlowProperties {
}
