package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public class SPNetParameterBean extends RatingParameterBean{

	private static final long serialVersionUID = -7548398383978827130L;

	private Integer spnetId;
	private String spnetName;
	private List<Integer> performanceLevels;
	private Double performanceScore;
	private Boolean routingPriorityApplied;
	/**
	 * @return the spnetId
	 */
	public Integer getSpnetId() {
		return spnetId;
	}
	/**
	 * @param spnetId the spnetId to set
	 */
	public void setSpnetId(Integer spnetId) {
		this.spnetId = spnetId;
	}
	/**
	 * @return the spnetName
	 */
	public String getSpnetName() {
		return spnetName;
	}
	/**
	 * @param spnetName the spnetName to set
	 */
	public void setSpnetName(String spnetName) {
		this.spnetName = spnetName;
	}
	public List<Integer> getPerformanceLevels() {
		return performanceLevels;
	}
	public void setPerformanceLevels(List<Integer> performanceLevels) {
		this.performanceLevels = performanceLevels;
	}
	public Double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(Double performanceScore) {
		this.performanceScore = performanceScore;
	}
	public Boolean getRoutingPriorityApplied() {
		return routingPriorityApplied;
	}
	public void setRoutingPriorityApplied(Boolean routingPriorityApplied) {
		this.routingPriorityApplied = routingPriorityApplied;
	}
	
}
