package com.makeid.makeflow.workflow.exception;

import com.makeid.makeflow.workflow.constants.ErrCodeEnum;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 任务相关异常
 * @create 2023-06-21
 */
public class TaskException extends RuntimeException{

    private ErrCodeEnum errCodeEnum;

    public TaskException() {
    }

    public TaskException(String message) {
        super(message);
    }

    public TaskException(ErrCodeEnum errCodeEnum) {
        this.errCodeEnum = errCodeEnum;
    }
}
