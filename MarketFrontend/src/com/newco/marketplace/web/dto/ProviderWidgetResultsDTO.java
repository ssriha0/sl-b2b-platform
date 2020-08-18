package com.newco.marketplace.web.dto;

import java.sql.Timestamp;

public class ProviderWidgetResultsDTO extends SerializedBaseDTO {

	private String providerCount="";
	private String firmProviderCount="";
	private Integer acceptedResourceId;
	private Timestamp appointStartDate;
	private Timestamp appointEndDate;
	private String serviceTimeStart="";
	private String serviceTimeEnd="";
	private String soId="";
	private String serviceLocationTimezone="";
	private String color="";
	private String percentage="";
	private String showProgressBar="";
	private String providerID = "";
	private String providerName = "";
	private String providerFirmId = "";
	private String providerFirmName = "";
	
	public String getProviderFirmId() {
		return providerFirmId;
	}

	public void setProviderFirmId(String providerFirmId) {
		this.providerFirmId = providerFirmId;
	}

	public String getProviderFirmName() {
		return providerFirmName;
	}

	public void setProviderFirmName(String providerFirmName) {
		this.providerFirmName = providerFirmName;
	}

	public String getProviderCount() {
		return providerCount;
	}

	public void setProviderCount(String providerCount) {
		this.providerCount = providerCount;
	}

	public String getFirmProviderCount() {
		return firmProviderCount;
	}

	public void setFirmProviderCount(String firmProviderCount) {
		this.firmProviderCount = firmProviderCount;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getShowProgressBar() {
		return showProgressBar;
	}

	public void setShowProgressBar(String showProgressBar) {
		this.showProgressBar = showProgressBar;
	}

	public String getProviderID() {
		return providerID;
	}

	public void setProviderID(String providerID) {
		this.providerID = providerID;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}

	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}

	public Timestamp getAppointStartDate() {
		return appointStartDate;
	}

	public void setAppointStartDate(Timestamp appointStartDate) {
		this.appointStartDate = appointStartDate;
	}

	public Timestamp getAppointEndDate() {
		return appointEndDate;
	}

	public void setAppointEndDate(Timestamp appointEndDate) {
		this.appointEndDate = appointEndDate;
	}

	public String getServiceTimeStart() {
		return serviceTimeStart;
	}

	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}

	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}

	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}

	public String getServiceLocationTimezone() {
		return serviceLocationTimezone;
	}

	public void setServiceLocationTimezone(String serviceLocationTimezone) {
		this.serviceLocationTimezone = serviceLocationTimezone;
	}

}
