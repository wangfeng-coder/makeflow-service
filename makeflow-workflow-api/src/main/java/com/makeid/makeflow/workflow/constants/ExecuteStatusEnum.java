package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public enum ExecuteStatusEnum {

    NOT_ACTIVE("not_active"),

    ACTIVE("active"),

    END("end");


    public final String status;

    ExecuteStatusEnum(String status) {
        this.status = status;
    }
}
