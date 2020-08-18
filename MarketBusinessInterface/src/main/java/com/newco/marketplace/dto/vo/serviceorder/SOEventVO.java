package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class SOEventVO extends CommonVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8377496305174253614L;

	private Long eventID;
	
	private String serviceOrderID;
	
	private String resourceID;
	
	private Date createDate;
	
	private Long eventTypeID;
	
	private Long eventReasonCode;
	
	private String processInd;

	/**
	 * @return the processInd
	 */
	public String getProcessInd() {
		return processInd;
	}

	/**
	 * @param processInd the processInd to set
	 */
	public void setProcessInd(String processInd) {
		this.processInd = processInd;
	}

	/**
	 * @param eventReasonCode the eventReasonCode to set
	 */
	public void setEventReasonCode(Long eventReasonCode) {
		this.eventReasonCode = eventReasonCode;
	}

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public String getServiceOrderID() {
		return serviceOrderID;
	}

	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}

	public String getResourceID() {
		return resourceID;
	}

	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getEventTypeID() {
		return eventTypeID;
	}

	public void setEventTypeID(Long eventTypeID) {
		this.eventTypeID = eventTypeID;
	}

	public Long getEventReasonCode() {
		return eventReasonCode;
	}

	public void setReasonCode(Long eventReasonCode) {
		this.eventReasonCode = eventReasonCode;
	}
	

}
