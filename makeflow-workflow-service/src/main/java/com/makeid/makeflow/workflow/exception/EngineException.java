package com.makeid.makeflow.workflow.exception;

import com.makeid.makeflow.workflow.constants.ErrCodeEnum;

/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-30
*/
public class EngineException extends RuntimeException{

    private ErrCodeEnum errCodeEnum;

    public EngineException(String message) {
        super(message);
    }

    public EngineException(ErrCodeEnum errCodeEnum) {
        super(errCodeEnum.getMessage());
        this.errCodeEnum = errCodeEnum;
    }
}
