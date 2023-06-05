package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public enum ActivityStatusEnum {
    NOT_INIT(0),

    RUNNING(1),

    COMPLETE(2),

    REJECT(3),

    CANCEL(99);



    public final int status;

    ActivityStatusEnum(int status) {
        this.status = status;
    }
}
