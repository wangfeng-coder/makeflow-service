package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public enum ExecuteStatusEnum {
    ACTIVE(1),

    SUSPEND(2),

    END(99);


    public final int status;

    ExecuteStatusEnum(int status) {
        this.status = status;
    }
}
