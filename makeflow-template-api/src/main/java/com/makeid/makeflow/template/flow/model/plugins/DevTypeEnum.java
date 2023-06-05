package com.makeid.makeflow.template.flow.model.plugins;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 开发者选项支持类型
 * @create 2023-05-26
 */
public enum DevTypeEnum {
    SPRING_BEAN_CALL("spring_bean_call","springbean调用"),

    CLASS_CALL("class_call","反射调用");

    private String devType;

   private String  name;

    DevTypeEnum(String devType, String name) {
        this.devType = devType;
        this.name = name;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }
}
