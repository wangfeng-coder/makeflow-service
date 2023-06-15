package com.makeid.makeflow.workflow.operation;

import com.makeid.makeflow.workflow.process.PvmExecution;

import java.util.HashMap;
import java.util.Map;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-06
 */
public abstract class AbstractEventAtomicOperation<T extends PvmExecution> implements AtomicOperation<T> {
    @Override
    public void execute(T execution) {
        notifyListener(execution);
        doExecute(execution);
    }

    public abstract void doExecute(T execution);

    public void notifyListener(T execution) {
        Map data = prepareEventData(execution);
        doListener(data);
    }

    public  void doListener(Map data){};

    public Map prepareEventData( T execution) {
        return new HashMap<>();
    }

}
