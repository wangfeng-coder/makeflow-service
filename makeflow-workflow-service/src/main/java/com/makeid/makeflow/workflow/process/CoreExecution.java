package com.makeid.makeflow.workflow.process;

import com.makeid.makeflow.workflow.behavior.ActivityBehavior;
import com.makeid.makeflow.workflow.entity.TaskEntity;
import com.makeid.makeflow.workflow.exception.EngineException;
import com.makeid.makeflow.workflow.operation.AtomicOperation;
import com.makeid.makeflow.workflow.operation.AtomicOperations;
import com.makeid.makeflow.workflow.process.difinition.ProcessDefinitionImpl;
import com.makeid.makeflow.workflow.runtime.ActivityPvmExecution;

import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-12
 */
public abstract class CoreExecution extends ScopeImpl implements ActivityPvmExecution {


    /**
     * 到达这个节点就应该设置
     */

    public CoreExecution(String codeId, ProcessDefinitionImpl processDefinition) {
        super(codeId, processDefinition);
    }

    public CoreExecution(ProcessDefinitionImpl processDefinition) {
        super(processDefinition);
    }

    @Override
    public void start() {
        if (findActivityInst().isStartActivity()) {
            //执行流程运转操作
            performOperation(AtomicOperations.process_start);
        } else {
            performOperation(AtomicOperations.activity_start);
        }
    }


    @Override
    public void take(List<? extends PvmTransition> transitionsToTake) {
        //多条线 选第一个
        PvmTransition pvmTransition = transitionsToTake.get(0);
        take(pvmTransition);
    }

    @Override
    public void take(PvmTransition outgoingTransition) {

    }


    public void performOperation(AtomicOperation operation) {
        operation.execute(this);
    }

    /**
     * 执行 节点行为
     */
    @Override
    public void execute() {

        ActivityBehavior activityBehavior = findActivityInst().findActivityBehavior();
        activityBehavior.execute(this);
    }

    public void completedTask() {
        this.findActivityInst().findActivityBehavior().completedTask(this);
    }

    @Override
    public void end() {
        //更新流程状态 并保存
    }


    @Override
    public void stay() {

    }

    @Override
    public void suspend(String message) {

    }

    @Override
    public void activate() {

    }

    @Override
    public void inactivate() {

    }

    public abstract String getId();



}
