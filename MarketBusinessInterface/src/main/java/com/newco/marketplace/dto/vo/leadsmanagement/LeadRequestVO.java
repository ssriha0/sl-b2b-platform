package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;
import java.util.List;


public class LeadRequestVO {

	private String leadId;
	private Date serviceDate;
    private String serviceTimeZone;
    private String serviceStartTime;
    private String serviceEndTime;
    private String projectDescription;
	private Integer numberOfMatches;
	private Integer boberDooLeadId;
	private Date createdDate;
	private Integer clientId;
	private String leadWfStatus;
	private String clientIpAddress;
	private Integer acceptedFirmId;
	private String leadType;
	private String leadSource;
	private Date modifiedDate;
	private Date postMemberLeadReqDate;
	private Date postLeadReqDate;
	private List<String> firmIds;
	
	private List<LeadProjectsVO>secondaryProjects;
	
	public List<LeadProjectsVO> getSecondaryProjects() {
		return secondaryProjects;
	}

	public void setSecondaryProjects(List<LeadProjectsVO> secondaryProjects) {
		this.secondaryProjects = secondaryProjects;
	}

	private LeadContactInfoVO custContact;

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	

	public LeadContactInfoVO getCustContact() {
		return custContact;
	}

	public void setCustContact(LeadContactInfoVO custContact) {
		this.custContact = custContact;
	}

	public List<String> getFirmIds() {
		return firmIds;
	}

	public void setFirmIds(List<String> firmIds) {
		this.firmIds = firmIds;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceTimeZone() {
		return serviceTimeZone;
	}

	public void setServiceTimeZone(String serviceTimeZone) {
		this.serviceTimeZone = serviceTimeZone;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public Integer getNumberOfMatches() {
		return numberOfMatches;
	}

	public void setNumberOfMatches(Integer numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}

	public Integer getBoberDooLeadId() {
		return boberDooLeadId;
	}

	public void setBoberDooLeadId(Integer boberDooLeadId) {
		this.boberDooLeadId = boberDooLeadId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getLeadWfStatus() {
		return leadWfStatus;
	}

	public void setLeadWfStatus(String leadWfStatus) {
		this.leadWfStatus = leadWfStatus;
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	public Integer getAcceptedFirmId() {
		return acceptedFirmId;
	}

	public void setAcceptedFirmId(Integer acceptedFirmId) {
		this.acceptedFirmId = acceptedFirmId;
	}

	public String getLeadType() {
		return leadType;
	}

	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getPostMemberLeadReqDate() {
		return postMemberLeadReqDate;
	}

	public void setPostMemberLeadReqDate(Date postMemberLeadReqDate) {
		this.postMemberLeadReqDate = postMemberLeadReqDate;
	}

	public Date getPostLeadReqDate() {
		return postLeadReqDate;
	}

	public void setPostLeadReqDate(Date postLeadReqDate) {
		this.postLeadReqDate = postLeadReqDate;
	}


}
