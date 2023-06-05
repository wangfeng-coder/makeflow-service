package com.makeid.makeflow.workflow.interceptor;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-05-29
 */
public interface Command<T> {

    T execute(CommandContext commandContext);

}