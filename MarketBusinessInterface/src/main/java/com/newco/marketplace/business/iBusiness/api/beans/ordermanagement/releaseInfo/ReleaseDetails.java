package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseInfo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("releaseDetails")
public class ReleaseDetails {
	
	@XStreamAlias("soId")
	private String soId;
	
	@XStreamAlias("title")
	private String title;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("price")
	private Double price;
	
	@XStreamAlias("assignedProvider")
	private Provider assignedProvider;
	
	@XStreamAlias("appointmentStartDate")
	private String appointmentStartDate;
	
	@XStreamAlias("appointmentEndDate")
	private String appointmentEndDate;
	
	@XStreamAlias("appointmentStartTime")
	private String appointmentStartTime;
	
	@XStreamAlias("appointmentEndTime")
	private String appointmentEndTime;
	
	@XStreamAlias("assignedProviderInd")
	private boolean assignedProviderInd;
	
	@XStreamAlias("manageSOInd")
	private boolean manageSOInd;
	
	@XStreamAlias("reasonCodes")
	@XStreamImplicit(itemFieldName="reason")
	private List<ReasonCode>reasonCodes;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Provider getAssignedProvider() {
		return assignedProvider;
	}

	public void setAssignedProvider(Provider assignedProvider) {
		this.assignedProvider = assignedProvider;
	}

	public String getAppointmentStartDate() {
		return appointmentStartDate;
	}

	public void setAppointmentStartDate(String appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}

	public String getAppointmentEndDate() {
		return appointmentEndDate;
	}

	public void setAppointmentEndDate(String appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}

	public String getAppointmentStartTime() {
		return appointmentStartTime;
	}

	public void setAppointmentStartTime(String appointmentStartTime) {
		this.appointmentStartTime = appointmentStartTime;
	}

	public String getAppointmentEndTime() {
		return appointmentEndTime;
	}

	public void setAppointmentEndTime(String appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}

	

	public boolean isAssignedProviderInd() {
		return assignedProviderInd;
	}

	public void setAssignedProviderInd(boolean assignedProviderInd) {
		this.assignedProviderInd = assignedProviderInd;
	}

	public boolean isManageSOInd() {
		return manageSOInd;
	}

	public void setManageSOInd(boolean manageSOInd) {
		this.manageSOInd = manageSOInd;
	}

	public List<ReasonCode> getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(List<ReasonCode> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	
}
