package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-07
 */
public enum TaskTypeEnum {

    /**
     * 开始任务
     */
    START_TASK(1);



    public final int taskType;

    TaskTypeEnum(int type) {
        this.taskType = type;
    }
}
