package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;

/**
 * 
 * This class is used to display rulename,zipcodes,jobcodes and pickup locations of conflict of
 * car rule.
 * 
 */
public class RuleConflictDisplayVO implements Serializable {
	private static final long serialVersionUID = 4209518599795591722L;
	
	private String ruleName;
	private String zipCodes;
	private String jobCodes;
	private String pickupLocation;
	private String markets;
	// Added to display the rule id in the conflict pop up
	private Integer ruleId;
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getZipCodes() {
		return zipCodes;
	}
	public void setZipCodes(String zipCodes) {
		this.zipCodes = zipCodes;
	}
	public String getJobCodes() {
		return jobCodes;
	}
	public void setJobCodes(String jobCodes) {
		this.jobCodes = jobCodes;
	}
	public String getPickupLocation() {
		return pickupLocation;
	}
	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}
	public String getMarkets() {
		return markets;
	}
	public void setMarkets(String markets) {
		this.markets = markets;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	
}
