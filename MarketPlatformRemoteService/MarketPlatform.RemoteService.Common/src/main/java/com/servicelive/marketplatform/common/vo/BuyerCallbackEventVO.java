package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class BuyerCallbackEventVO {
	private static final long serialVersionUID = 1L;
	private Integer eventId;
	private String eventName;
	private Integer serviceId;
	private Integer actionId;
	private Integer buyerId;
	private Integer filterId;
	public Integer getEventId() {
		return eventId;
	}
	
	@XmlElement
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	@XmlElement
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	@XmlElement
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	@XmlElement
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getFilterId() {
		return filterId;
	}
	
	@XmlElement
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	
}