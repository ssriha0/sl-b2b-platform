package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("LeadDetail")
public class LeadDetail {
	
	@XStreamAlias("LeadId")
	private String slLeadId;
	
	@XStreamAlias("LmsLeadId")
	private String lmsLeadId;
	
	@XStreamAlias("LeadStatus")
	private String leadStatus;
	
	@XStreamAlias("Skill")
	private String skill;
	
	@XStreamAlias("ProjectType")
	private String projectType;

	@XStreamAlias("Urgency")
	private String urgency;
	
	@XStreamAlias("CustomerFirstName")
	private String custFirstName;
	
	@XStreamAlias("CustomerLastName")
	private String custLastName;
	
	@XStreamAlias("CustomerPhoneNo")
	private String custPhoneNo;
	
	@XStreamAlias("CustomerCity")
	private String custCity;
	
	@XStreamAlias("CustomerZip")
	private String custZip;
	
	@XStreamAlias("ServicePreferedDate")
	private String servicePreferedDate;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("createdDateFormatted")
	private String createdDateFormatted;
	
	@XStreamAlias("Description")
	private String description;
	
	
	public String getCustCity() {
		return custCity;
	}

	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}

	public String getCustZip() {
		return custZip;
	}

	public void setCustZip(String custZip) {
		this.custZip = custZip;
	}

	public String getServicePreferedDate() {
		return servicePreferedDate;
	}

	public void setServicePreferedDate(String servicePreferedDate) {
		this.servicePreferedDate = servicePreferedDate;
	}



	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
	public String getSlLeadId() {
		return slLeadId;
	}

	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCustPhoneNo() {
		return custPhoneNo;
	}

	public void setCustPhoneNo(String custPhoneNo) {
		this.custPhoneNo = custPhoneNo;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDateFormatted() {
		return createdDateFormatted;
	}

	public void setCreatedDateFormatted(String createdDateFormatted) {
		this.createdDateFormatted = createdDateFormatted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLmsLeadId() {
		return lmsLeadId;
	}

	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}
	


}
