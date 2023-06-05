package com.makeid.makeflow.workflow.cmd;

public interface LockCommand {

	String getProcessInstanceId();
	String getOpLockKey();
}
