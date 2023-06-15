package com.makeid.makeflow.workflow.utils;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description juel表达式解析工具类
 * @create 2023-06-12
 */
public final class ConditionElUtil {

    private ConditionElUtil(){}

    public static Boolean checkFormDataByRuleEl(String el, Map<String, Object> formData) {

        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (Map.Entry<String,Object> entry : formData.entrySet())  {
            Object value = entry.getValue();
            if (value != null) {
                context.setVariable(entry.getKey(), factory.createValueExpression(value, value.getClass()));
            }
        }
        ValueExpression e = factory.createValueExpression(context, el, Boolean.class);
        return (Boolean) e.getValue(context);
    }
}
