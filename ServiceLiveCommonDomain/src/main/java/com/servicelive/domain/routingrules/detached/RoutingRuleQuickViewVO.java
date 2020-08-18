package com.servicelive.domain.routingrules.detached;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RoutingRuleQuickViewVO implements Serializable {
	private static final long serialVersionUID = 3502458183994354999L;
	
	private String ruleName;
	private String ruleStatus;
	private String providerFirm;
	private String zipCode;
	private String market;
	private String jobCodes;
	private String pickUplocationCodes;
	private String contact;
	private Map<String,String> customReference;
    private String lastName;
    private String firstName;
    private String email;

	
	public String getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	public String getProviderFirm() {
		return providerFirm;
	}
	public void setProviderFirm(String providerFirm) {
		this.providerFirm = providerFirm;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getJobCodes() {
		return jobCodes;
	}
	public void setJobCodes(String jobCodes) {
		this.jobCodes = jobCodes;
	}
	public String getPickUplocationCodes() {
		return pickUplocationCodes;
	}
	public void setPickUplocationCodes(String pickUplocationCodes) {
		this.pickUplocationCodes = pickUplocationCodes;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Map<String, String> getCustomReference() {
		return customReference;
	}
	public void setCustomReference(Map<String, String> customReference) {
		this.customReference = customReference;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
