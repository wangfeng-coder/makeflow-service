package com.makeid.demo.VO;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-10-07
 */
public class Result<T> {

    private T data;

    private boolean success;

    private int errorCode;

    private String error;

    public Result(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public Result(T data) {
        this.data = data;
        this.success = true;
    }

    public Result() {
    }

    public static <T> Result<T> ok() {
       return  new Result<>(null);
    }
    public static <T> Result<T> ok(T data) {
        return  new Result<>(data);
    }

    public static <T> Result<T> err(String error) {
        Result result = new Result<>();
        result.success = false;
        result.error = error;
        return  result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
