package com.newco.marketplace.dto.vo.systemgeneratedemail;

public class EventTemplateVO {
	private Integer buyerEventId;
	private Integer buyerId;
	private Integer eventId;
	private String eventName;
	private String eventType;
	private Integer templateId;
	private Integer actionId;
	private Integer wfStateId;
	private Integer soSubStatusId;
		
	public Integer getBuyerEventId() {
		return buyerEventId;
	}
	public void setBuyerEventId(Integer buyerEventId) {
		this.buyerEventId = buyerEventId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String event_type) {
		this.eventType = event_type;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public Integer getSoSubStatusId() {
		return soSubStatusId;
	}
	public void setSoSubStatusId(Integer soSubStatusId) {
		this.soSubStatusId = soSubStatusId;
	}
}
