package com.servicelive.bus.event;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.bus.event.order.ServiceOrderEvent;

/**
 * 
 * Header Properties:
 *  ServiceOrderId
 *  GroupServiceOrderId
 *  BuyerResourceId
 *  BuyerCompanyId
 *  VendorId
 *  ProviderResourceId
 *  TypeHierarchy
 *  EventTime
 * 
 * User: Mustafa Motiwala
 * Date: Mar 26, 2010 Time: 1:00:17 PM
 */
@XmlRootElement(name = "ServiceLiveEvent")
@XmlSeeAlso( { ServiceOrderEvent.class })
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceLiveEvent implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -1235869150808168475L;

	@XmlElement(name = "source")
	private Object eventSource;

	@XmlTransient
	protected Map<String, String> eventHeader = new HashMap<String, String>();

	@XmlElement(name = "eventId")
	private String eventId;

	/**
	 * This is the default no args constructor to allow JAXB serialization.
	 */
	protected ServiceLiveEvent() {
		this.doInit();
	}

	public ServiceLiveEvent(Object source, Calendar time) {
		this.eventSource = source;
		this.doInit();
	}
	
	private void doInit() {
		this.eventId = UUID.randomUUID().toString();
	}

	public void addHeader(EventHeader header, String value) {
		this.eventHeader.put(header.name(), value);
	}

	public void addHeader(String name, String value) {
		this.eventHeader.put(name, value);
	}
	
	public String getHeaderValue(EventHeader header) {
		return this.eventHeader.get(header.name());
	}

	public String getHeaderValue(String headerName) {
		return this.eventHeader.get(headerName);
	}

	public Map<String, String> getEventHeader() {
		return Collections.unmodifiableMap(this.eventHeader);
	}

	public String getEventId() {
		return this.eventId;
	}

	public Object getEventSource() {
		return this.eventSource;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public void setEventSource(Object eventSource) {
		this.eventSource = eventSource;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
