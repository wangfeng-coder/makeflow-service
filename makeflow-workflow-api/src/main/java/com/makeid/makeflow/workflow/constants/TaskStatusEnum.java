package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 任务枚举
 * @create 2023-06-07
 */
public enum TaskStatusEnum {

    //未初始化
    NOT_INIT(0),

    //正在进行待处理
    DOING(1),

    //已完成
    DONE(99);

    public final int status;

    TaskStatusEnum(int status) {
        this.status = status;
    }
}
