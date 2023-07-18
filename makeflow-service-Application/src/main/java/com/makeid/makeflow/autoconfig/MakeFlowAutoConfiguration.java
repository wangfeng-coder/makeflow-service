package com.makeid.makeflow.autoconfig;

import com.makeid.makeflow.MakeFlowApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-10
 */
@Configuration
@ComponentScan(value = {"com.makeid.makeflow"})
public class MakeFlowAutoConfiguration {

}
