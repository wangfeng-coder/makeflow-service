package com.makeid.makeflow.template.exception;

import com.makeid.makeflow.basic.exception.MakeFlowException;
import com.makeid.makeflow.template.constants.ErrCodeEnum;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-14
 */
public class TemplateException extends MakeFlowException {

    public TemplateException(ErrCodeEnum errCodeEnum) {
        super(errCodeEnum.getMessage());
        this.errCodeEnum = errCodeEnum;
    }

    private ErrCodeEnum errCodeEnum;

    public TemplateException(String message) {
        super(message);
    }

}
