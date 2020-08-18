package com.newco.marketplace.dto.vo.systemgeneratedemail;

public class EventActionDetailsVO {
	private Integer id;
	private Integer eventId;
	private Integer actionId;
	private Integer wfStateId;
	private Integer soSubStatusId;
	private String eventType;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
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
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

}
