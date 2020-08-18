package com.newco.marketplace.dto.vo.leadsmanagement;


import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder; 

public class InsideSalesLeadCustVO {
	
	private String leadId;
	private String customField;
	private String customFieldValue;
	private Date createdDate;
	private Date modifiedDate;	
	private Integer isleadId;

	
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getCustomField() {
		return customField;
	}
	public void setCustomField(String customField) {
		this.customField = customField;
	}
	public String getCustomFieldValue() {
		return customFieldValue;
	}
	public void setCustomFieldValue(String customFieldValue) {
		this.customFieldValue = customFieldValue;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getIsleadId() {
		return isleadId;
	}
	public void setIsleadId(Integer isleadId) {
		this.isleadId = isleadId;
	}
    
	
	
}
