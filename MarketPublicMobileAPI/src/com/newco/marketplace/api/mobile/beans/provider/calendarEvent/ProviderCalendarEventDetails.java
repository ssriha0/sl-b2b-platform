package com.newco.marketplace.api.mobile.beans.provider.calendarEvent;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


import com.thoughtworks.xstream.annotations.XStreamAlias;


@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderCalendarEventDetails {


	
	@XStreamAlias("personId")
	private Integer personId;
	
	@XStreamAlias("type")
	private String type;
	
	@XStreamAlias("source")
	private String source;
	
	@XStreamAlias("eventId")
	private String eventId;
	
	@XStreamAlias("eventName")
	private String eventName;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("startDate")
	private String startDate;

	
	@XStreamAlias("endDate")
	private String endDate;
	
	@XStreamAlias("startTime")
	private String startTime;
	
	
	@XStreamAlias("endTime")
	private String endTime;
	
	@XStreamAlias("memberFirstName")
	private String memberFirstName;
	
	@XStreamAlias("memberLastName")
	private String memberLastName;
	
	@XStreamAlias("memberCity")
	private String memberCity;
	
	@XStreamAlias("memberState")
	private String memberState;
	
	@XStreamAlias("memberZip")
	private String memberZip;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("modifiedDate")
	private String modifiedDate;
	
	@XStreamAlias("modifiedBy")
	private String modifiedBy;

	/**
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the memberFirstName
	 */
	public String getMemberFirstName() {
		return memberFirstName;
	}

	/**
	 * @param memberFirstName the memberFirstName to set
	 */
	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}

	/**
	 * @return the memberLastName
	 */
	public String getMemberLastName() {
		return memberLastName;
	}

	/**
	 * @param memberLastName the memberLastName to set
	 */
	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}

	/**
	 * @return the memberCity
	 */
	public String getMemberCity() {
		return memberCity;
	}

	/**
	 * @param memberCity the memberCity to set
	 */
	public void setMemberCity(String memberCity) {
		this.memberCity = memberCity;
	}

	/**
	 * @return the memberState
	 */
	public String getMemberState() {
		return memberState;
	}

	/**
	 * @param memberState the memberState to set
	 */
	public void setMemberState(String memberState) {
		this.memberState = memberState;
	}

	/**
	 * @return the memberZip
	 */
	public String getMemberZip() {
		return memberZip;
	}

	/**
	 * @param memberZip the memberZip to set
	 */
	public void setMemberZip(String memberZip) {
		this.memberZip = memberZip;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
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
	
	
	
}
