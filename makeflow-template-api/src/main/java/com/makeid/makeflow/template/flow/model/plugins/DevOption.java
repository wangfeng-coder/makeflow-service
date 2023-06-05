package com.makeid.makeflow.template.flow.model.plugins;

import java.util.List;

/**
*@program makeflow-service
*@description 开发者选项 可以绑定到任何节点上 在节点对应时机执行
*@author feng_wf
*@create 2023-05-26
*/
public class DevOption extends BasePlug {

    /**
     * 开发者类型
     * {@link DevTypeEnum}
     */
    protected String devType;

    protected String interfaceUrl;

    /**
     * {@link ExeOpportunityEnum}
     */
    protected String exeOpportunity;

    protected boolean async;

    protected List<FieldExtension> fieldExtensions;

    public DevOption() {
        this.type = PlugTypeEnum.DEVOPTION.getType();
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getInterfaceUrl() {
        return interfaceUrl;
    }

    public void setInterfaceUrl(String interfaceUrl) {
        this.interfaceUrl = interfaceUrl;
    }

    public String getExeOpportunity() {
        return exeOpportunity;
    }

    public void setExeOpportunity(String exeOpportunity) {
        this.exeOpportunity = exeOpportunity;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public List<FieldExtension> getFieldExtensions() {
        return fieldExtensions;
    }

    public void setFieldExtensions(List<FieldExtension> fieldExtensions) {
        this.fieldExtensions = fieldExtensions;
    }
}
