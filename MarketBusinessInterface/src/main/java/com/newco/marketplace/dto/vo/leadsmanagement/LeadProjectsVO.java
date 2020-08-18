package com.newco.marketplace.dto.vo.leadsmanagement;

import java.sql.Timestamp;

public class LeadProjectsVO {
	
	private String leadId;
	private String project;
	private Integer primaryInd;
	private Timestamp createdDate;
	private Timestamp modifiedDate;

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Integer getPrimaryInd() {
		return primaryInd;
	}

	public void setPrimaryInd(Integer primaryInd) {
		this.primaryInd = primaryInd;
	}
}
