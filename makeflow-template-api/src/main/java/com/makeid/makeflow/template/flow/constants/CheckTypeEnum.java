package com.makeid.makeflow.template.flow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public enum CheckTypeEnum {

    AND("and"),

    OR("or"),

    TURN("turn");

    public final String type;

    CheckTypeEnum(String type) {
        this.type = type;
    }
}
