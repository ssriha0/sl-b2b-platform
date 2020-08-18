package com.newco.marketplace.dto.inhome.autoclose;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.provider.ProviderFirm;


public class InHomeAutoCloseDTO{
	
	Integer inhomeAutoCloseRuleFirmAssocId;
	ProviderFirm providerFirm;
	String existingOverrideReason;
	String newOverrideReason;
	BigDecimal reimbursementRate;
	Date overrideDate;
	String overrideDateFormatted;
	String modifiedBy;
	List<InHomeAutoCloseRuleFirmAssocHistory> firmAssocHistory;
	
	public Integer getInhomeAutoCloseRuleFirmAssocId() {
		return inhomeAutoCloseRuleFirmAssocId;
	}
	public void setInhomeAutoCloseRuleFirmAssocId(
			Integer inhomeAutoCloseRuleFirmAssocId) {
		this.inhomeAutoCloseRuleFirmAssocId = inhomeAutoCloseRuleFirmAssocId;
	}
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}
	public String getExistingOverrideReason() {
		return existingOverrideReason;
	}
	public void setExistingOverrideReason(String existingOverrideReason) {
		this.existingOverrideReason = existingOverrideReason;
	}
	public String getNewOverrideReason() {
		return newOverrideReason;
	}
	public void setNewOverrideReason(String newOverrideReason) {
		this.newOverrideReason = newOverrideReason;
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
	public List<InHomeAutoCloseRuleFirmAssocHistory> getFirmAssocHistory() {
		return firmAssocHistory;
	}
	public void setFirmAssocHistory(
			List<InHomeAutoCloseRuleFirmAssocHistory> firmAssocHistory) {
		this.firmAssocHistory = firmAssocHistory;
	}
	public BigDecimal getReimbursementRate() {
		return reimbursementRate;
	}
	public void setReimbursementRate(BigDecimal reimbursementRate) {
		this.reimbursementRate = reimbursementRate;
	}
}
