package com.newco.marketplace.dto.vo.newsignupbonus;

public class VendorNewSignUpBonusVo {

	private Integer vendorNewSignUpBonusId;
	private Integer vendorId;
	private Integer buyerId;
	private Double bonusScore = (double) 0;

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getVendorNewSignUpBonusId() {
		return vendorNewSignUpBonusId;
	}

	public void setVendorNewSignUpBonusId(Integer vendorNewSignUpBonusId) {
		this.vendorNewSignUpBonusId = vendorNewSignUpBonusId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Double getBonusScore() {
		return bonusScore;
	}

	public void setBonusScore(Double bonusScore) {
		this.bonusScore = bonusScore;
	}

}