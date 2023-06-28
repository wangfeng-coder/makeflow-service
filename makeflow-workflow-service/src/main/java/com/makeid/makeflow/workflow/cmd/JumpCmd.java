package com.makeid.makeflow.workflow.cmd;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-06-27
 */
public class JumpCmd extends AbstractCommand<Void>{

    /**
     * 当前源 节点的id
     */
    private String sourceActivityId;

    /**
     * 目标活动的codeId
     */
    private String destinationActivityCode;



    @Override
    public Void execute() {
        return null;
    }
}
