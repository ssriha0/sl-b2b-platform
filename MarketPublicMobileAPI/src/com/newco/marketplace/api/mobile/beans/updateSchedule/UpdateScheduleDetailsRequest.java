package com.newco.marketplace.api.mobile.beans.updateSchedule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "updateScheduleDetailsRequest.xsd", path = "/resources/schemas/mobile/v3_1/")
@XStreamAlias("updateScheduleDetailsRequest") 
@XmlRootElement(name = "updateScheduleDetailsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateScheduleDetailsRequest{

	
	@XStreamAlias("source")   
	private String source;
	
	@XStreamAlias("customerAvailableFlag")   
	private Boolean customerAvailableFlag;
	
	@XStreamAlias("custNotAvailableReasonCode")   
	private Integer custNotAvailableReasonCode;
	
	@XStreamAlias("eta")   
	private String eta;
	
	@XStreamAlias("soLocNotes")   
	private String soLocNotes;
	
	@XStreamAlias("specialInstructions")   
	private String specialInstructions;	
	
	@XStreamAlias("custResponseReasonCode")   
	private Integer custResponseReasonCode;
	
	@XStreamAlias("startTime")   
	private String startTime;
	 
	@XStreamAlias("endTime")   
	private String endTime;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Boolean getCustomerAvailableFlag() {
		return customerAvailableFlag;
	}

	public void setCustomerAvailableFlag(Boolean customerAvailableFlag) {
		this.customerAvailableFlag = customerAvailableFlag;
	}

	public Integer getCustNotAvailableReasonCode() {
		return custNotAvailableReasonCode;
	}

	public void setCustNotAvailableReasonCode(Integer custNotAvailableReasonCode) {
		this.custNotAvailableReasonCode = custNotAvailableReasonCode;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getSoLocNotes() {
		return soLocNotes;
	}

	public void setSoLocNotes(String soLocNotes) {
		this.soLocNotes = soLocNotes;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public Integer getCustResponseReasonCode() {
		return custResponseReasonCode;
	}

	public void setCustResponseReasonCode(Integer custResponseReasonCode) {
		this.custResponseReasonCode = custResponseReasonCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}	
	
}
