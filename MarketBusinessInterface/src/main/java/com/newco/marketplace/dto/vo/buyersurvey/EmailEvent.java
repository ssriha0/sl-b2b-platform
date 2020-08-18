package com.newco.marketplace.dto.vo.buyersurvey;

import java.util.List;

import com.newco.marketplace.dto.vo.survey.SurveyOptionVO;

public class EmailEvent {

	private Integer eventId;
	private String eventName;
	private String eventType;
	private String eventDescr;
	private boolean active;
	private String signature;
	private List<EmailEvent> statusEvent;
	private Integer statusEventOption;
	private String statusOption;
	private List<SurveyOptionVO> surveyOptionList; 
	private Integer surveyOptionId;
	private String surveyType;
	private String surveyFeature;
	private String emailTemplate;
	
	public Integer getSurveyOptionId() {
		return surveyOptionId;
	}

	public void setSurveyOptionId(Integer surveyOptionId) {
		this.surveyOptionId = surveyOptionId;
	}

	public String getSurveyType() {
		return surveyType;
	}

	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
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

	public String getStatusOption() {
		return statusOption;
	}

	public void setStatusOption(String statusOption) {
		this.statusOption = statusOption;
	}

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
	 * @return the signature
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public List<EmailEvent> getStatusEvent() {
		return statusEvent;
	}

	public void setStatusEvent(List<EmailEvent> statusEvent) {
		this.statusEvent = statusEvent;
	}

	public List<SurveyOptionVO> getSurveyOptionList() {
		return surveyOptionList;
	}

	public void setSurveyOptionList(List<SurveyOptionVO> surveyOptionList) {
		this.surveyOptionList = surveyOptionList;
	}

	public Integer getStatusEventOption() {
		return statusEventOption;
	}

	public void setStatusEventOption(Integer statusEventOption) {
		this.statusEventOption = statusEventOption;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	/**
	 * @return the surveyFeature
	 */
	public String getSurveyFeature() {
		return surveyFeature;
	}

	/**
	 * @param surveyFeature the surveyFeature to set
	 */
	public void setSurveyFeature(String surveyFeature) {
		this.surveyFeature = surveyFeature;
	}

}
