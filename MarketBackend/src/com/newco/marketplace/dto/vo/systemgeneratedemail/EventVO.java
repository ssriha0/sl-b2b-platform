package com.newco.marketplace.dto.vo.systemgeneratedemail;

import java.util.List;

public class EventVO {
	private Integer eventId;
	private String eventName;
	private String subEventName;
	private String eventType;
	List<ActionVO> actions;
	
	/**
	 * @return the eventId
	 */
	public Integer getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	/**
	 * @return the subEventName
	 */
	public String getSubEventName() {
		return subEventName;
	}
	/**
	 * @param subEventName the subEventName to set
	 */
	public void setSubEventName(String subEventName) {
		this.subEventName = subEventName;
	}
	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * @return the actions
	 */
	public List<ActionVO> getActions() {
		return actions;
	}
	/**
	 * @param actions the actions to set
	 */
	public void setActions(List<ActionVO> actions) {
		this.actions = actions;
	}
}
