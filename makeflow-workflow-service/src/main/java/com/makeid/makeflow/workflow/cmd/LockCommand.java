package com.makeid.makeflow.workflow.cmd;

public interface LockCommand {

	Long getProcessInstanceId();
	String getOpLockKey();
}
