package com.makeid.makeflow.workflow.process.condition;

import com.makeid.makeflow.workflow.process.Condition;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;
import com.makeid.makeflow.template.util.ElUtil;

import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description uel表达式
 * @create 2023-06-09
 */
public class UelExpressionCondition implements Condition {

    private String expression;

    public UelExpressionCondition(String expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate(ActivityPvmExecution activityExecution) {
        Map<String, Object> variables = activityExecution.getVariables();
        Boolean aBoolean = ElUtil.checkFormDataByRuleEl(expression, variables);
        return aBoolean;
    }
}
