package com.newco.marketplace.dto.autoclose;

import java.util.List;

import com.servicelive.domain.autoclose.AutoCloseRuleCriteriaHistory;

public class AutoCloseRuleDTO {
	
	private Integer autoCloseRuleId;
	private Integer autoCloseRuleHdrId;
	private String autoCloseRuleName;
	private String autoCloseRuleDescription;
	private String autoCloseRuleCriteriaValue;
	private Integer autoCloseRuleCriteriaId;
	private List<AutoCloseRuleCriteriaHistory> autoCloseRuleCriteriaHistoryList;
	
	public Integer getAutoCloseRuleId() {
		return autoCloseRuleId;
	}
	public void setAutoCloseRuleId(Integer autoCloseRuleId) {
		this.autoCloseRuleId = autoCloseRuleId;
	}
	public Integer getAutoCloseRuleHdrId() {
		return autoCloseRuleHdrId;
	}
	public void setAutoCloseRuleHdrId(Integer autoCloseRuleHdrId) {
		this.autoCloseRuleHdrId = autoCloseRuleHdrId;
	}
	public String getAutoCloseRuleName() {
		return autoCloseRuleName;
	}
	public void setAutoCloseRuleName(String autoCloseRuleName) {
		this.autoCloseRuleName = autoCloseRuleName;
	}
	public String getAutoCloseRuleDescription() {
		return autoCloseRuleDescription;
	}
	public void setAutoCloseRuleDescription(String autoCloseRuleDescription) {
		this.autoCloseRuleDescription = autoCloseRuleDescription;
	}
	public String getAutoCloseRuleCriteriaValue() {
		return autoCloseRuleCriteriaValue;
	}
	public void setAutoCloseRuleCriteriaValue(String autoCloseRuleCriteriaValue) {
		this.autoCloseRuleCriteriaValue = autoCloseRuleCriteriaValue;
	}
	public Integer getAutoCloseRuleCriteriaId() {
		return autoCloseRuleCriteriaId;
	}
	public void setAutoCloseRuleCriteriaId(Integer autoCloseRuleCriteriaId) {
		this.autoCloseRuleCriteriaId = autoCloseRuleCriteriaId;
	}
	public List<AutoCloseRuleCriteriaHistory> getAutoCloseRuleCriteriaHistoryList() {
		return autoCloseRuleCriteriaHistoryList;
	}
	public void setAutoCloseRuleCriteriaHistoryList(
			List<AutoCloseRuleCriteriaHistory> autoCloseRuleCriteriaHistoryList) {
		this.autoCloseRuleCriteriaHistoryList = autoCloseRuleCriteriaHistoryList;
	}

}
