package com.makeid.makeflow.template.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-30
 */
public enum ErrCodeEnum {

    //流程
    TEMPLATE_NOT_EXIST("","模板不存在");


    ErrCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

