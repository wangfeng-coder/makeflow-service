package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-31
 */
public enum FlowStatusEnum {
    UNSUBMIT("not_submit"),

    RUNNING("running"),

    DISAGREE("disagree"),

    END("end");;

    public final String status;

    FlowStatusEnum(String status) {
        this.status = status;
    }
}
