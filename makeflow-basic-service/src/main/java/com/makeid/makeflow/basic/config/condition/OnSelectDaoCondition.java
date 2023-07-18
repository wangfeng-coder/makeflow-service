package com.makeid.makeflow.basic.config.condition;

import com.makeid.makeflow.basic.annotation.Dao;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.ClassMetadata;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-13
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OnSelectDaoCondition extends SpringBootCondition {

    private final String KEY = "makeflow.data.dataType";

    private final String DATA_TYPE_MYSQL = "mysql";

    private final String DATA_TYPE_MONGDB = "mongodb";

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String dataType =(String) Optional.ofNullable(metadata.getAnnotationAttributes(Dao.class.getName()))
                .orElse(Collections.EMPTY_MAP)
                .get("value");
        Environment environment = context.getEnvironment();
        String envProperty = getEnvProperty(environment);
        if(Objects.equals(dataType,envProperty)) {
            return ConditionOutcome.match("通过直接选择注入对应DAO");
        }
        if (metadata instanceof ClassMetadata) {
            ClassMetadata classMetadata = (ClassMetadata) metadata;
            String className = classMetadata.getClassName();
            try {
                Class<?> aClass = Class.forName(className);
                List<Class<?>> allSuperclasses = ClassUtils.getAllSuperclasses(aClass);
                for (Class<?> superclass : allSuperclasses) {
                    if (DATA_TYPE_MYSQL.equals(envProperty) && com.makeid.makeflow.basic.dao.impl.mysql.BaseDaoImpl.class.equals(superclass)) {
                        return ConditionOutcome.match("父类是mysql的基类");
                    }
                    if(DATA_TYPE_MONGDB.equals(envProperty) && com.makeid.makeflow.basic.dao.impl.mongo.BaseDaoImpl.class.equals(superclass)) {
                        return ConditionOutcome.match("父类是mongo的基类");
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return ConditionOutcome.noMatch("");
    }

    private String getEnvProperty(Environment environment) {
       return environment.getProperty(KEY,"");
    }


}
