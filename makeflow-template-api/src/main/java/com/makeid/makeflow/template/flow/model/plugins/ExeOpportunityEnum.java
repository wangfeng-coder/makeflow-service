package com.makeid.makeflow.template.flow.model.plugins;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 开发者选型执行时机
 * @create 2023-05-26
 */
public enum ExeOpportunityEnum {

    NODE_INIT_BEFORE("nodeInitBefore"),

    NODE_INIT_AFTER("nodeInitAfter"),

    NODE_ARRIVED("nodeArrived"),

    NODE_LEAVED("nodeLeaved"),

    PROCESS_STARTED("processStarted"),

    PROCESS_END("processEnded");

    private String exeOpportunity;

    ExeOpportunityEnum(String exeOpportunity) {
        this.exeOpportunity = exeOpportunity;
    }

    public String getExeOpportunity() {
        return exeOpportunity;
    }

    public void setExeOpportunity(String exeOpportunity) {
        this.exeOpportunity = exeOpportunity;
    }
}
