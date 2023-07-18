package com.makeid.makeflow.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(MakeFlowSpringStartListener.class)
public @interface ImportMakeFlow {
}
