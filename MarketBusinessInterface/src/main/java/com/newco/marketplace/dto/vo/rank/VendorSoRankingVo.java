package com.newco.marketplace.dto.vo.rank;

public class VendorSoRankingVo {

	private Integer vendorSoRankingId;
	private Integer vendorId;
	private Double rankingScore = (double) 0;
	private Integer rank;
	private String soId;
	private String corelationId;

	public Integer getVendorSoRankingId() {
		return vendorSoRankingId;
	}

	public void setVendorSoRankingId(Integer vendorSoRankingId) {
		this.vendorSoRankingId = vendorSoRankingId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public double getRankingScore() {
		return rankingScore;
	}

	public void setRankingScore(double rankingScore) {
		this.rankingScore = rankingScore;
	}

	public String getCorelationId() {
		return corelationId;
	}

	public void setCorelationId(String corelationId) {
		this.corelationId = corelationId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}