package com.makeid.makeflow.workflow.event;

public enum EventType {
	
	PROCESS_CREATED(ElementType.PROCESS),
	PROCESS_STARTED(ElementType.PROCESS),
	PROCESS_ENDED(ElementType.PROCESS),
	PROCESS_DISAGREED(ElementType.PROCESS),


	ACTIVITY_CREATED(ElementType.ACTIVITY),
	ACTIVITY_STARTED(ElementType.ACTIVITY),
	ACTIVITY_EXECUTED(ElementType.ACTIVITY),
	ACTIVITY_ENDED(ElementType.ACTIVITY),
	ACTIVITY_DISAGREED(ElementType.ACTIVITY),
	ACTIVITY_STAYED(ElementType.ACTIVITY),
	ACTIVITY_RETURNED(ElementType.ACTIVITY),

	ACTIVITY_JUMPED(ElementType.ACTIVITY),


	EXECUTION_CREATED(ElementType.EXECUTION),

	EXECUTION_STARTED(ElementType.EXECUTION),
	EXECUTION_ENDED(ElementType.EXECUTION),
	EXECUTION_ACTIVATED(ElementType.EXECUTION),
	EXECUTION_INACTIVATED(ElementType.EXECUTION),
	
	TASK_CREATED(ElementType.TASK),
	TASK_RUNNING(ElementType.TASK),

	TASK_DISAGREE(ElementType.TASK),
	TASK_DONE(ElementType.TASK),
	
	TASK_CANCEL(ElementType.TASK),

	TASK_RETURN(ElementType.TASK),

	TASK_JUMP(ElementType.TASK);
	
	private ElementType elementType;
	
	private EventType(ElementType elementType) {
		this.elementType = elementType;
	}
	
	public ElementType getElementType(){
		return elementType;
	}
	
	
	public boolean isProcess(){
		return getElementType() == ElementType.PROCESS;
	}
	
	public boolean isActivity(){
		return getElementType() == ElementType.ACTIVITY;

	}
	
	public boolean isExecution(){
		return getElementType() == ElementType.EXECUTION;

	}
	
	public boolean isTask(){
		return getElementType() == ElementType.TASK;

	}

	enum ElementType{
		PROCESS,
		ACTIVITY,
		EXECUTION,
		TASK
	}

}
