package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.PvmExecution;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description 操作
 * @create 2023-06-05
 */
public interface AtomicOperation <T extends PvmExecution> {

    void execute(T execution);
}
