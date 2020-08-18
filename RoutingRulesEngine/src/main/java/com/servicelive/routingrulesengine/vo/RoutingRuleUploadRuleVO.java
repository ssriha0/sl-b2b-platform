package com.servicelive.routingrulesengine.vo;

import java.util.List;

public class RoutingRuleUploadRuleVO {
	
	String ruleName;
	String ruleId;
	String emailId;
	String contactFirstName;
	String contactLastName;
	List<String> zipCodes;
	List<String> providerFirmIds;
	List<CustomReferenceVO> custRefs;
	List<JobPriceVO> jobPrice;
	String action;
	Integer errorType;
	String comments;
	List<String> ruleIds;
	String fileaction;
	
	public String getFileaction() {
		return fileaction;
	}
	public void setFileaction(String fileaction) {
		this.fileaction = fileaction;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Integer getErrorType() {
		return errorType;
	}
	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}
	public List<String> getZipCodes() {
		return zipCodes;
	}
	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}
	public List<String> getProviderFirmIds() {
		return providerFirmIds;
	}
	public void setProviderFirmIds(List<String> providerFirmIds) {
		this.providerFirmIds = providerFirmIds;
	}
	public List<CustomReferenceVO> getCustRefs() {
		return custRefs;
	}
	public void setCustRefs(List<CustomReferenceVO> custRefs) {
		this.custRefs = custRefs;
	}
	public List<JobPriceVO> getJobPrice() {
		return jobPrice;
	}
	public void setJobPrice(List<JobPriceVO> jobPrice) {
		this.jobPrice = jobPrice;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<String> getRuleIds() {
		return ruleIds;
	}
	public void setRuleIds(List<String> ruleIds) {
		this.ruleIds = ruleIds;
	}
}
