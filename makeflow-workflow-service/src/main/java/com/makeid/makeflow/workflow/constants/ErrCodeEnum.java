package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-30
 */
public enum ErrCodeEnum {
    FLOW_OPERATEINT("","流程正在处理中"),

    TEMPLATE_NULL("","模板信息为空");

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

