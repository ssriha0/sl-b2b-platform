package com.newco.marketplace.dto.vo.performancematric;

public class VendorPerformanceMatricVo {

	private Integer buyerId;
	private Integer vendorId;

	// appointment_commitment
	private Double totalRescheduleCount;

	// appointment_commitment
	private Double totalClosedOrdersCount;

	// customer_rating_csat
	private Double avgCsatRating;

	// Time_on-site_arrival
	private Double timeOnSiteCountSum;

	// PERFORMANCE_METRIC
	private Double vendorPerformanceMatricTableScore;

	// NEW_SIGNUP
	private Double newSignUpBonusScore;

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Double getTotalRescheduleCount() {
		return totalRescheduleCount;
	}

	public void setTotalRescheduleCount(Double totalRescheduleCount) {
		this.totalRescheduleCount = totalRescheduleCount;
	}

	public Double getTotalClosedOrdersCount() {
		return totalClosedOrdersCount;
	}

	public void setTotalClosedOrdersCount(Double totalClosedOrdersCount) {
		this.totalClosedOrdersCount = totalClosedOrdersCount;
	}

	public Double getAvgCsatRating() {
		return avgCsatRating;
	}

	public void setAvgCsatRating(Double avgCsatRating) {
		this.avgCsatRating = avgCsatRating;
	}

	public Double getTimeOnSiteCountSum() {
		return timeOnSiteCountSum;
	}

	public void setTimeOnSiteCountSum(Double timeOnSiteCountSum) {
		this.timeOnSiteCountSum = timeOnSiteCountSum;
	}

	public Double getVendorPerformanceMatricTableScore() {
		return vendorPerformanceMatricTableScore;
	}

	public void setVendorPerformanceMatricTableScore(
			Double vendorPerformanceMatricTableScore) {
		this.vendorPerformanceMatricTableScore = vendorPerformanceMatricTableScore;
	}

	public Double getNewSignUpBonusScore() {
		return newSignUpBonusScore;
	}

	public void setNewSignUpBonusScore(Double newSignUpBonusScore) {
		this.newSignUpBonusScore = newSignUpBonusScore;
	}

}
