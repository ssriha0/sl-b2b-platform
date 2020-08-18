package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo;

import java.sql.Timestamp;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("callDetails")
public class CallDetails {

	
	@XStreamAlias("customerInfo")
	private Contact customerInfo;
	
	
	@XStreamAlias("productDetails")
	private Part productDetails;
	
	
	@XStreamAlias("scope")
	private List<Task> scope;
	
	
	@XStreamAlias("historyList")
	private List<PreCallHistory> historyList;
	
	
	@XStreamAlias("specialInstructions")
	private String specialInstructions;
	
	
	@XStreamAlias("serviceLocationNotes")
	private String serviceLocationNotes;
	
	
	@XStreamAlias("assignedProvider")
	private Provider assignedProvider;
	
	@XStreamAlias("appointmentStartDate")
	private String appointmentStartDate;
	
	private Timestamp startDate;

	
	@XStreamAlias("appointmentEndDate")
	private String appointmentEndDate;
	
	private Timestamp endDate;
	

	@XStreamAlias("appointmentStartTime")
	private String appointmentStartTime;
	
	
	@XStreamAlias("appointmentEndTime")
	private String appointmentEndTime;
	
	
	@XStreamAlias("timezone")
	private String timezone;
	
	@XStreamAlias("reasonCodeList")
	private List<ReasonCode> reasonCodeList;
	
	
	public Contact getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(Contact customerInfo) {
		this.customerInfo = customerInfo;
	}
	
	
	public Part getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(Part productDetails) {
		this.productDetails = productDetails;
	}
	public List<Task> getScope() {
		return scope;
	}
	public void setScope(List<Task> scope) {
		this.scope = scope;
	}
	public List<PreCallHistory> getHistoryList() {
		return historyList;
	}
	public void setHistoryList(List<PreCallHistory> historyList) {
		this.historyList = historyList;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public String getServiceLocationNotes() {
		return serviceLocationNotes;
	}
	public void setServiceLocationNotes(String serviceLocationNotes) {
		this.serviceLocationNotes = serviceLocationNotes;
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
	public List<ReasonCode> getReasonCodeList() {
		return reasonCodeList;
	}
	public void setReasonCodeList(List<ReasonCode> reasonCodeList) {
		this.reasonCodeList = reasonCodeList;
	}
	public Provider getAssignedProvider() {
		return assignedProvider;
	}
	public void setAssignedProvider(Provider assignedProvider) {
		this.assignedProvider = assignedProvider;
	}
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
}
