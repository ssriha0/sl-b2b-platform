/**
 * 
 */
package com.newco.marketplace.dto.vo.systemgeneratedemail;


/**
 * @author rkathir
 *
 */
public class EmailConfigVO {
	
	private Integer eventId;
	private String eventName;
	private String eventType;
	private String eventDescr;
	private String subEventName;
	private Integer buyerId;
	private String paramKey;
	private String paramValue;
	private String eventParamKey;
	private String eventParamValue;
	private boolean active;
	private Integer buyerEventId;
	private Integer buyerEventMappingId;
	private Integer templateId;
	private String modifiedBy;
	private Integer surveyOptionId;
	private String eventStatus;
	
	
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
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
	 * @return the eventDescr
	 */
	public String getEventDescr() {
		return eventDescr;
	}

	/**
	 * @param eventDescr the eventDescr to set
	 */
	public void setEventDescr(String eventDescr) {
		this.eventDescr = eventDescr;
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
	 * @return the paramKey
	 */
	public String getParamKey() {
		return paramKey;
	}

	/**
	 * @param paramKey the paramKey to set
	 */
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getEventParamKey() {
		return eventParamKey;
	}

	public void setEventParamKey(String eventParamKey) {
		this.eventParamKey = eventParamKey;
	}

	public String getEventParamValue() {
		return eventParamValue;
	}

	public void setEventParamValue(String eventParamValue) {
		this.eventParamValue = eventParamValue;
	}

	public Integer getBuyerEventId() {
		return buyerEventId;
	}

	public void setBuyerEventId(Integer buyerEventId) {
		this.buyerEventId = buyerEventId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getBuyerEventMappingId() {
		return buyerEventMappingId;
	}

	public void setBuyerEventMappingId(Integer buyerEventMappingId) {
		this.buyerEventMappingId = buyerEventMappingId;
	}

	public Integer getSurveyOptionId() {
		return surveyOptionId;
	}

	public void setSurveyOptionId(Integer surveyOptionId) {
		this.surveyOptionId = surveyOptionId;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	
}
