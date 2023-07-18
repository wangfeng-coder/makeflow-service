package com.makeid.makeflow.basic.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 将注解的类选择性加入到容器中
 * @create 2023-07-13
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
@Documented
@SelectDaoCondition
@Repository
public @interface Dao {

    @AliasFor("value")
    String dataType() default "";

    @AliasFor("dataType")
    String value() default "";
}
