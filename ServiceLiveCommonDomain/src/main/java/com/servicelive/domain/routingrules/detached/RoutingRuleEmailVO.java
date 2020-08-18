package com.servicelive.domain.routingrules.detached;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RoutingRuleEmailVO implements Serializable {
	private static final long serialVersionUID = 3502458183994354859L;
	private String ruleContactFN;
	private String ruleContactLN;
	private String carRuleName;
	private String buyerCompanyName;
	private String ruleContactEmail;
	private Integer buyerCompanyID;
	private Map<String, Integer> vendors;
	private List<String> jobCodes;
	private Boolean isStatusChange = Boolean.FALSE;
	private Boolean isMissingPrice = Boolean.FALSE;
	public String getRuleContactFN() {
		return ruleContactFN;
	}
	public void setRuleContactFN(String ruleContactFN) {
		this.ruleContactFN = ruleContactFN;
	}
	public String getCarRuleName() {
		return carRuleName;
	}
	public void setCarRuleName(String carRuleName) {
		this.carRuleName = carRuleName;
	}
	public String getBuyerCompanyName() {
		return buyerCompanyName;
	}
	public void setBuyerCompanyName(String buyerCompanyName) {
		this.buyerCompanyName = buyerCompanyName;
	}
	public Integer getBuyerCompanyID() {
		return buyerCompanyID;
	}
	public void setBuyerCompanyID(Integer buyerCompanyID) {
		this.buyerCompanyID = buyerCompanyID;
	}
	public Map<String, Integer> getVendors() {
		return vendors;
	}
	public void setVendors(Map<String, Integer> vendors) {
		this.vendors = vendors;
	}
	public List<String> getJobCodes() {
		return jobCodes;
	}
	public void setJobCodes(List<String> jobCodes) {
		this.jobCodes = jobCodes;
	}
	public String getRuleContactLN() {
		return ruleContactLN;
	}
	public void setRuleContactLN(String ruleContactLN) {
		this.ruleContactLN = ruleContactLN;
	}
	public String getRuleContactEmail() {
		return ruleContactEmail;
	}
	public void setRuleContactEmail(String ruleContactEmail) {
		this.ruleContactEmail = ruleContactEmail;
	}
	public Boolean getIsStatusChange() {
		return isStatusChange;
	}
	public void setIsStatusChange(Boolean isStatusChange) {
		this.isStatusChange = isStatusChange;
	}
	public Boolean getIsMissingPrice() {
		return isMissingPrice;
	}
	public void setIsMissingPrice(Boolean isMissingPrice) {
		this.isMissingPrice = isMissingPrice;
	}
}
