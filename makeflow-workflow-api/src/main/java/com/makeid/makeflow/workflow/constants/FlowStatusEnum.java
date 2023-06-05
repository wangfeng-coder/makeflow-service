package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public enum FlowStatusEnum {
    UNSUBMIT(0),

    RUNNING(1),

    END(99);;

    public final int status;

    FlowStatusEnum(int status) {
        this.status = status;
    }
}
