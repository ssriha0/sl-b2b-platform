package com.newco.marketplace.dto.vo.rank;

public class VendorRankingWeightedScoreVo {

	private Integer vendorRankingWeightedScoreVoId;
	private Integer vendorId;
	private Double weightedScore;
	private String soId;
	private String corelationId;
	private Integer vendorRankingBuyerId;

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Double getWeightedScore() {
		return weightedScore;
	}

	public void setWeightedScore(Double weightedScore) {
		this.weightedScore = weightedScore;
	}

	public String getCorelationId() {
		return corelationId;
	}

	public void setCorelationId(String corelationId) {
		this.corelationId = corelationId;
	}

	public Integer getVendorRankingWeightedScoreVoId() {
		return vendorRankingWeightedScoreVoId;
	}

	public void setVendorRankingWeightedScoreVoId(
			Integer vendorRankingWeightedScoreVoId) {
		this.vendorRankingWeightedScoreVoId = vendorRankingWeightedScoreVoId;
	}

	public Integer getVendorRankingBuyerId() {
		return vendorRankingBuyerId;
	}

	public void setVendorRankingBuyerId(Integer vendorRankingBuyerId) {
		this.vendorRankingBuyerId = vendorRankingBuyerId;
	}

}
