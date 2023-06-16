package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public enum ExecuteStatusEnum {

    IN_ACTIVEE(0),

    ACTIVE(1),

    END(99);


    public final int status;

    ExecuteStatusEnum(int status) {
        this.status = status;
    }
}
