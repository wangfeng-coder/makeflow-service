package com.makeid.makeflow.template.flow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-14
 */
public enum PeopleTypeEnum {


    /**
     * 通过el表达式来获取 当前参数中的人
     *
     */
    EL("el"),

    /**
     * 指定人
     */
    PERSON("person"),


    DEPARTMENT("department"),

    /**
     * 按角色
     */
    ROLE("role");



    PeopleTypeEnum(String type) {
        this.type = type;
    }

    public final String type;
}
