package com.makeid.makeflow.template.flow.model.activity;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-23
 */
public enum ActivityTypeEnum {
    START("start"),

    END("end");

    public final String type;

    ActivityTypeEnum(String type) {
        this.type = type;
    }
}
