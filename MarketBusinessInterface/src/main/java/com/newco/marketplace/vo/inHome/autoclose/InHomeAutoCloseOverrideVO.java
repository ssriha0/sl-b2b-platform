/**
 *
 */
package com.newco.marketplace.vo.inHome.autoclose;

import java.util.Date;





public class InHomeAutoCloseOverrideVO {

	private Integer autoCloseRuleAssocId;
	private Integer providerId;
	private Integer reimbursementRate;
	private String existingReason;
	private String newReason;
	private Date overrideDate;
	private String overrideDateFormatted;
	private String modifiedBy;
	private String businessName;
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getReimbursementRate() {
		return reimbursementRate;
	}
	public void setReimbursementRate(Integer reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
	public Integer getAutoCloseRuleAssocId() {
		return autoCloseRuleAssocId;
	}
	public void setAutoCloseRuleAssocId(Integer autoCloseRuleAssocId) {
		this.autoCloseRuleAssocId = autoCloseRuleAssocId;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getExistingReason() {
		return existingReason;
	}
	public void setExistingReason(String existingReason) {
		this.existingReason = existingReason;
	}
	public String getNewReason() {
		return newReason;
	}
	public void setNewReason(String newReason) {
		this.newReason = newReason;
	}
	public Date getOverrideDate() {
		return overrideDate;
	}
	public void setOverrideDate(Date overrideDate) {
		this.overrideDate = overrideDate;
	}
	public String getOverrideDateFormatted() {
		return overrideDateFormatted;
	}
	public void setOverrideDateFormatted(String overrideDateFormatted) {
		this.overrideDateFormatted = overrideDateFormatted;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}


	

	

