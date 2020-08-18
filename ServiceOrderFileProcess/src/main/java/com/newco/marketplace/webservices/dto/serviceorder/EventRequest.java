package com.newco.marketplace.webservices.dto.serviceorder;

import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class EventRequest extends ABaseWebserviceRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3204365808763498475L;

	private String serviceOrderID;
	
	private String resourceID;
	
	private long eventTypeID;
	
	private long eventReasonCode;
	
	@XmlElement(minOccurs="1", nillable=false)
	public String getServiceOrderID() {
		return this.serviceOrderID;
	}
	
	@XmlElement(minOccurs="1", nillable=false)
	public String getResourceID() {
		return this.resourceID;
	}

	@XmlElement(minOccurs="1", nillable=false)
	public long getEventTypeID() {
		return eventTypeID;
	}

	public void setEventTypeID(long eventTypeID) {
		this.eventTypeID = eventTypeID;
	}

	@XmlElement(minOccurs="1", nillable=false)
	public long getEventReasonCode() {
		return eventReasonCode;
	}

	public void setEventReasonCode(long eventReasonCode) {
		this.eventReasonCode = eventReasonCode;
	}

	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	
}
