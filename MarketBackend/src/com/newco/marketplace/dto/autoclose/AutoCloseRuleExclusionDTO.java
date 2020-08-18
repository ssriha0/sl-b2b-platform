package com.newco.marketplace.dto.autoclose;

import java.util.Date;
import java.util.List;

import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssocHistory;

public class AutoCloseRuleExclusionDTO {
	
	Integer autoCloseRuleAssocId;
	ProviderFirm providerFirm;
	ServiceProvider serviceProvider;
	String existingReason;
	String newReason;
	Date excludedDate;
	String excludedDateFormatted;
	String modifiedBy;
	List<AutoCloseRuleFirmAssocHistory> firmAssocHistory;
	List<AutoCloseRuleProviderAssocHistory> providerAssocHistory;	
	
	public ProviderFirm getProviderFirm() {
		return providerFirm;
	}
	public void setProviderFirm(ProviderFirm providerFirm) {
		this.providerFirm = providerFirm;
	}
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
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
	public Date getExcludedDate() {
		return excludedDate;
	}
	public void setExcludedDate(Date excludedDate) {
		this.excludedDate = excludedDate;
	}
	public Integer getAutoCloseRuleAssocId() {
		return autoCloseRuleAssocId;
	}
	public void setAutoCloseRuleAssocId(Integer autoCloseRuleAssocId) {
		this.autoCloseRuleAssocId = autoCloseRuleAssocId;
	}
	public String getExcludedDateFormatted() {
		return excludedDateFormatted;
	}
	public void setExcludedDateFormatted(String excludedDateFormatted) {
		this.excludedDateFormatted = excludedDateFormatted;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public List<AutoCloseRuleFirmAssocHistory> getFirmAssocHistory() {
		return firmAssocHistory;
	}
	public void setFirmAssocHistory(
			List<AutoCloseRuleFirmAssocHistory> firmAssocHistory) {
		this.firmAssocHistory = firmAssocHistory;
	}
	public List<AutoCloseRuleProviderAssocHistory> getProviderAssocHistory() {
		return providerAssocHistory;
	}
	public void setProviderAssocHistory(
			List<AutoCloseRuleProviderAssocHistory> providerAssocHistory) {
		this.providerAssocHistory = providerAssocHistory;
	}

}
