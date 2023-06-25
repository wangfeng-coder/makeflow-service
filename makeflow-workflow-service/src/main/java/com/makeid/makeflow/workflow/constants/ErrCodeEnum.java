package com.makeid.makeflow.workflow.constants;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-30
 */
public enum ErrCodeEnum {

    //流程
    FLOW_OPERATEINT("","流程正在处理中"),

    FLOW_NOT_EXIST("","流程不存在"),

    //-----------------------分割线-----------------
    //模板
    TEMPLATE_NULL("","模板信息为空"),


    /**
     * 任务
     */
    HANDLER_INCONSISTENT("","任务处理人不一致"),

    TASK_STATUS_NOT_DOING("","当前任务状态不处于处理中");


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

