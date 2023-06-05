package com.makeid.makeflow.template.flow.model.rule;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 规则操作符类型
 * @create 2023-05-23
 */
public enum RuleOperatorTypeEnum {
    GTE("gte","大于等于"),
    LTE("lte","小于等于"),
    EQ("eq","等于"),
    GT("gt","大于"),
    CONTAINS("contains","包含"),
    CONTAIN("contain","包含");

    RuleOperatorTypeEnum(String operate, String name) {
        this.operate = operate;
        this.name = name;
    }

    private String operate;

    private String name;


    public String getOperate() {
        return operate;
    }

    public String getName() {
        return name;
    }
}
