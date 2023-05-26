package com.makeid.makeflow.template.flow.plugins;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 插件类型
 * @create 2023-05-26
 */
public enum PlugTypeEnum {
    DEVOPTION("devOption","开发者选项");

    private String type;

    private String name;

    PlugTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
