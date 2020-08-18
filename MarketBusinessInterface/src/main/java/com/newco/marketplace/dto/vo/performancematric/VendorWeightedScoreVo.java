package com.newco.marketplace.dto.vo.performancematric;

public class VendorWeightedScoreVo {

	private Integer vendorPerfWeightedScoreId;
	private Integer vendorRankingBuyerId;
	private Integer vendorPerformanceMatricId;
	
	private Integer buyerId;
	private Integer vendorId;
	private Double weightedScore;

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public Double getWeightedScore() {
		return weightedScore;
	}

	public void setWeightedScore(Double weightedScore) {
		this.weightedScore = weightedScore;
	}

	public Integer getVendorPerformanceMatricId() {
		return vendorPerformanceMatricId;
	}

	public void setVendorPerformanceMatricId(Integer vendorPerformanceMatricId) {
		this.vendorPerformanceMatricId = vendorPerformanceMatricId;
	}

	public Integer getVendorPerfWeightedScoreId() {
		return vendorPerfWeightedScoreId;
	}

	public void setVendorPerfWeightedScoreId(Integer vendorPerfWeightedScoreId) {
		this.vendorPerfWeightedScoreId = vendorPerfWeightedScoreId;
	}

	public Integer getVendorRankingBuyerId() {
		return vendorRankingBuyerId;
	}

	public void setVendorRankingBuyerId(Integer vendorRankingBuyerId) {
		this.vendorRankingBuyerId = vendorRankingBuyerId;
	}

}
