/*package com.newco.marketplace.api.mobile.beans.submitReschedule;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XSD(name = "mobileSOSubmitRescheduleRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "submitRescheduleRequest")
@XStreamAlias("submitRescheduleRequest")

public class MobileSOSubmitRescheduleRequest{
	
	@XStreamAlias("rescheduleDateType")
	private String rescheduleDateType;
	
	@XStreamAlias("rescheduleStartDate")
	private String rescheduleStartDate;
	
	@XStreamAlias("rescheduleStartTime")
	private String rescheduleStartTime;
	
	@XStreamAlias("rescheduleEndDate")	
	private String rescheduleEndDate;			
	
	@XStreamAlias("rescheduleEndTime")
	private String rescheduleEndTime;
	
	@XStreamAlias("rescheduleReasonCode")
	private Integer rescheduleReasonCode;
	
	@XStreamAlias("rescheduleComments")
	private String rescheduleComments;

	
	//for validation 
	private String soId;
	private String firmId;
	private String firmResourceId;
	public String getSoId() {
		return soId;
	}
	
	public String getRescheduleDateType() {
		return rescheduleDateType;
	}
	public void setRescheduleDateType(String rescheduleDateType) {
		this.rescheduleDateType = rescheduleDateType;
	}
	public String getRescheduleStartDate() {
		return rescheduleStartDate;
	}
	public void setRescheduleStartDate(String rescheduleStartDate) {
		this.rescheduleStartDate = rescheduleStartDate;
	}
	public String getRescheduleEndDate() {
		return rescheduleEndDate;
	}
	public void setRescheduleEndDate(String rescheduleEndDate) {
		this.rescheduleEndDate = rescheduleEndDate;
	}
	
	public String getRescheduleStartTime() {
		return rescheduleStartTime;
	}
	public void setRescheduleStartTime(String rescheduleStartTime) {
		this.rescheduleStartTime = rescheduleStartTime;
	}
	public String getRescheduleEndTime() {
		return rescheduleEndTime;
	}
	public void setRescheduleEndTime(String rescheduleEndTime) {
		this.rescheduleEndTime = rescheduleEndTime;
	}
	public Integer getRescheduleReasonCode() {
		return rescheduleReasonCode;
	}
	public void setRescheduleReasonCode(Integer rescheduleReasonCode) {
		this.rescheduleReasonCode = rescheduleReasonCode;
	}
	public String getRescheduleComments() {
		return rescheduleComments;
	}
	public void setRescheduleComments(String rescheduleComments) {
		this.rescheduleComments = rescheduleComments;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public String getFirmResourceId() {
		return firmResourceId;
	}
	public void setFirmResourceId(String firmResourceId) {
		this.firmResourceId = firmResourceId;
	}


}
*/