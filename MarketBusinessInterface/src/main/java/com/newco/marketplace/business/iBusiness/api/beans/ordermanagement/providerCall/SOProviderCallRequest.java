package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.providerCall;

import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("soProviderCallRequest")   
public class SOProviderCallRequest extends UserIdentificationRequest {


	@XStreamAlias("scheduleStatus")   
	private String scheduleStatus;
	@XStreamAlias("reason")   
	private String reason;
	@XStreamAlias("soNotes")   
	private String soNotes;
	@XStreamAlias("specialInstructions")   
	private String specialInstructions;	
	@XStreamAlias("eta")   
	private String eta;
	@XStreamAlias("customerConfirmInd")   
	private Boolean customerConfirmInd;
	@XStreamAlias("preCallInd")   
	private Boolean preCallInd;
	public String getEta() {
		return eta;
	}
	public void setEta(String eta) {
		this.eta = eta;
	}
	public Boolean getCustomerConfirmInd() {
		return customerConfirmInd;
	}
	public void setCustomerConfirmInd(Boolean customerConfirmInd) {
		this.customerConfirmInd = customerConfirmInd;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSoNotes() {
		return soNotes;
	}
	public void setSoNotes(String soNotes) {
		this.soNotes = soNotes;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public Boolean getPreCallInd() {
		return preCallInd;
	}
	public void setPreCallInd(Boolean preCallInd) {
		this.preCallInd = preCallInd;
	}



}
