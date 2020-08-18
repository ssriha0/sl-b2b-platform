package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.List;

public class CancelLeadVO {
	private String leadId;
	private int reasonCode;
	private String comment;
	private List<Integer> providerList;
	private boolean revokePointsInd;
	private int status;
	private boolean chkAllProviderInd;
	private String buyer_name;
	private int roleId;
	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private String additionalProjects;
	private String projectType;
	private String firmFirstName;
	private String firmLastName;
	private String firmEmail;
	private String preferredAppointment;
	private String preferredStartTime;
	private String preferredEndTime;
	private String serviceCategoryDescription;
	private String swyrId;
	private String customerFullName;
	private String streetInfo;
	private String cityInfo;
	private String zip;
	private String service;
	private String cancelInitiatedByRoleType;	
	private String lmsLeadId;

	public String getCancelInitiatedByRoleType() {
		return cancelInitiatedByRoleType;
	}

	public void setCancelInitiatedByRoleType(String cancelInitiatedByRoleType) {
		this.cancelInitiatedByRoleType = cancelInitiatedByRoleType;
	}

	public String getServiceCategoryDescription() {
		return serviceCategoryDescription;
	}

	public void setServiceCategoryDescription(String serviceCategoryDescription) {
		this.serviceCategoryDescription = serviceCategoryDescription;
	}

	public String getSwyrId() {
		return swyrId;
	}

	public void setSwyrId(String swyrId) {
		this.swyrId = swyrId;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getStreetInfo() {
		return streetInfo;
	}

	public void setStreetInfo(String streetInfo) {
		this.streetInfo = streetInfo;
	}

	public String getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(String cityInfo) {
		this.cityInfo = cityInfo;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	
	public String getAdditionalProjects() {
		return additionalProjects;
	}

	public void setAdditionalProjects(String additionalProjects) {
		this.additionalProjects = additionalProjects;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getFirmFirstName() {
		return firmFirstName;
	}

	public void setFirmFirstName(String firmFirstName) {
		this.firmFirstName = firmFirstName;
	}

	public String getFirmLastName() {
		return firmLastName;
	}

	public void setFirmLastName(String firmLastName) {
		this.firmLastName = firmLastName;
	}



	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getFirmEmail() {
		return firmEmail;
	}

	public void setFirmEmail(String firmEmail) {
		this.firmEmail = firmEmail;
	}

	public String getPreferredAppointment() {
		return preferredAppointment;
	}

	public void setPreferredAppointment(String preferredAppointment) {
		this.preferredAppointment = preferredAppointment;
	}

	public String getPreferredStartTime() {
		return preferredStartTime;
	}

	public void setPreferredStartTime(String preferredStartTime) {
		this.preferredStartTime = preferredStartTime;
	}

	public String getPreferredEndTime() {
		return preferredEndTime;
	}

	public void setPreferredEndTime(String preferredEndTime) {
		this.preferredEndTime = preferredEndTime;
	}

	public String getBuyer_name() {
		return buyer_name;
	}

	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}

	public int getReasonCode() {
		return reasonCode;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public void setReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLmsLeadId() {
		return lmsLeadId;
	}

	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}

	public boolean isRevokePointsInd() {
		return revokePointsInd;
	}

	public void setRevokePointsInd(boolean revokePointsInd) {
		this.revokePointsInd = revokePointsInd;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Integer> getProviderList() {
		return providerList;
	}

	public void setProviderList(List<Integer> providerList) {
		this.providerList = providerList;
	}

	public boolean isChkAllProviderInd() {
		return chkAllProviderInd;
	}

	public void setChkAllProviderInd(boolean chkAllProviderInd) {
		this.chkAllProviderInd = chkAllProviderInd;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	

	

}
