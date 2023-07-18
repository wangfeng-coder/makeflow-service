package com.makeid.makeflow.basic.exception;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-14
 */
public class MakeFlowException extends RuntimeException{

    public MakeFlowException(String message) {
        super(message);
    }
}
