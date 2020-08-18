package com.servicelive.bus.event.log;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ServiceLiveBusEventLog implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -3216099216660481137L;

	private long id;
	private String eventId;
	private String clientId;
	private String eventType;
	private String serviceOrderId;
	private String orderEventType;
	

	public ServiceLiveBusEventLog() {
	}

	public ServiceLiveBusEventLog(long id, String eventId, String clientId,
			String eventType, String serviceOrderId, String orderEventType) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.clientId = clientId;
		this.eventType = eventType;
		this.serviceOrderId = serviceOrderId;
		this.orderEventType = orderEventType;
	}

	public String getClientId() {
		return clientId;
	}

	public String getEventId() {
		return eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public long getId() {
		return id;
	}

	public String getOrderEventType() {
		return orderEventType;
	}

	public String getServiceOrderId() {
		return serviceOrderId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setOrderEventType(String orderEventType) {
		this.orderEventType = orderEventType;
	}

	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
