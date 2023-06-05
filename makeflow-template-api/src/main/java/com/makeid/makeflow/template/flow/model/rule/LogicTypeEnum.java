package com.makeid.makeflow.template.flow.model.rule;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-23
 */
public enum LogicTypeEnum {
    OR("or","或"),
    AND("and","且");


    LogicTypeEnum(String logicType, String name) {
        this.logicType = logicType;
        this.name = name;
    }

    private String logicType;

    private String  name;

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
