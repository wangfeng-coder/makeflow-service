package com.makeid.makeflow.template.util;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description juel表达式解析工具类
 * @create 2023-06-12
 */
public final class ElUtil {

    private ElUtil(){}

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

    public static String extractExpression(String expression) {
        if (expression.startsWith("${") && expression.endsWith("}")) {
            return expression.substring(2, expression.length() - 1);
        }
        return null;
    }

    public static boolean isELExpression(String expression) {
        ExpressionFactory factory = ExpressionFactory.newInstance();
        ELContext context = new SimpleContext(); // 自定义EL上下文
        try {
            if (!expression.startsWith("${")) {
                return false;
            }
            factory.createValueExpression(context, expression, Object.class);
            return true;
        } catch (ELException e) {
            return false;
        }
    }
}
