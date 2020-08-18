package com.newco.marketplace.dto.vo.spn;


import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class SPNetHeaderVO extends SerializableBaseVO {

	private static final long serialVersionUID = -7375867014890438529L;

	private Integer spnId;
	
	private String contactEmail;
	
	private String contactName;
	
	private String contactPhone;
	
	private String spnDescription;
	
	private String spnName;
	
	private Date effectiveDate;
	
	private Boolean isAlias;
	
	private Integer aliasOrigSpnId;
	/**
	 * is overflow exits
	 */
	private Boolean isMPOverflow;
	
	private String perfCriteriaLevel;
	
	private String priorityStatus;
	
	public String getPriorityStatus() {
		return priorityStatus;
	}
	public void setPriorityStatus(String priorityStatus) {
		this.priorityStatus = priorityStatus;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getSpnDescription() {
		return spnDescription;
	}
	public void setSpnDescription(String spnDescription) {
		this.spnDescription = spnDescription;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getAliasOrigSpnId() {
		return aliasOrigSpnId;
	}
	public void setAliasOrigSpnId(Integer aliasOrigSpnId) {
		this.aliasOrigSpnId = aliasOrigSpnId;
	}
	public Boolean getIsAlias() {
		return isAlias;
	}
	public void setIsAlias(Boolean isAlias) {
		this.isAlias = isAlias;
	}
	public Boolean getIsMPOverflow() {
		return isMPOverflow;
	}
	public void setIsMPOverflow(Boolean isMPOverflow) {
		this.isMPOverflow = isMPOverflow;
	}
	public String getPerfCriteriaLevel() {
		return perfCriteriaLevel;
	}
	public void setPerfCriteriaLevel(String perfCriteriaLevel) {
		this.perfCriteriaLevel = perfCriteriaLevel;
	}

}
