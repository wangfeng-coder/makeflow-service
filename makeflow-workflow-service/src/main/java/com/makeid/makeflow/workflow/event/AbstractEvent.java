package com.makeid.makeflow.workflow.event;

import java.util.Map;

public abstract class AbstractEvent <T> implements Event<T> {

	private EventType eventType;

	private T data;

	private Map<String, Object> extendedData;

	public AbstractEvent(EventType eventType, T data) {
		this(eventType, data, null);
	}

	public AbstractEvent(EventType eventType, T data,
                         Map<String, Object> extendedData) {
		this.eventType = eventType;
		this.data = data;
		this.extendedData = extendedData;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Map<String, Object> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(Map<String, Object> extendedData) {
		this.extendedData = extendedData;
	}

}
