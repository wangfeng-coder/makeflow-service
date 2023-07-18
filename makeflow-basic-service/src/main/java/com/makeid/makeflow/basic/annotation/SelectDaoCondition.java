package com.makeid.makeflow.basic.annotation;

import com.makeid.makeflow.basic.config.condition.OnSelectDaoCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnSelectDaoCondition.class)
public @interface SelectDaoCondition {
}
