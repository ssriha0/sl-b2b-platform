package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * This class is used to hold ruleid,zipcodes,jobcodes and pickup locations of a
 * car rule.
 * 
 */
public class RoutingRulesConflictVO implements Serializable {
	private static final long serialVersionUID = 4209518599795591722L;
	private Integer ruleId;
	private List<String> zipCodes;
	private List<String> jobCodes;
	private List<String> pickupLocation;
	private Map<Integer,List<String>> marketZipsMap;
	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public List<String> getZipCodes() {
		return zipCodes;
	}

	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}

	public List<String> getJobCodes() {
		return jobCodes;
	}

	public void setJobCodes(List<String> jobCodes) {
		this.jobCodes = jobCodes;
	}

	public List<String> getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(List<String> pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public Map<Integer, List<String>> getMarketZipsMap() {
		return marketZipsMap;
	}

	public void setMarketZipsMap(Map<Integer, List<String>> marketZipsMap) {
		this.marketZipsMap = marketZipsMap;
	}
}
