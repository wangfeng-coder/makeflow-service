package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-05
 */
public enum ActivityStatusEnum {
    NOT_INIT("not_init"),

    RUNNING("running"),

    COMPLETE("complete"),

    REJECT("reject"),

    CANCEL("cancel");



    public final String status;

    ActivityStatusEnum(String status) {
        this.status = status;
    }
}
