package com.makeid.makeflow.template.flow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public enum ParticipantTypeEnum {

    /**
     * 指定人
     */
    PERSON("person"),

    /**
     * 按角色
     */
    ROLE("role");

    ParticipantTypeEnum(String type) {
        this.type = type;
    }

    public final String type;
}
