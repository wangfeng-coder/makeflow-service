package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 任务枚举
 * @create 2023-06-07
 */
public enum TaskStatusEnum {

    //未初始化
    NOT_INIT("not_init"),

    //正在进行待处理
    DOING("doing"),

    DISAGREE("disAgree"),

    CANCEL("cancel"),

    RETURN("return"),

    JUMP("jump"),



    //已完成
    DONE("done");

    public final String status;

    TaskStatusEnum(String status) {
        this.status = status;
    }
}
