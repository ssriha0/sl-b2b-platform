package com.newco.marketplace.beans.d2c.d2cproviderportal;

import java.util.Comparator;

public class D2CProviderAPIVO implements Comparator<D2CProviderAPIVO> {
	private Integer firmId;
	private Double price;
	private Integer dailyLimit;
	private Integer acceptedCount;
	private Double rating;
	private Integer soCompleted;
	private Double buyerRetailPrice;
	private Boolean optIn = true;
	private Double totalWeightage;
	private Integer providerRank;

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(Integer dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public Integer getAcceptedCount() {
		return acceptedCount;
	}

	public void setAcceptedCount(Integer acceptedCount) {
		this.acceptedCount = acceptedCount;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getSoCompleted() {
		return soCompleted;
	}

	public void setSoCompleted(Integer soCompleted) {
		this.soCompleted = soCompleted;
	}

	public Double getBuyerRetailPrice() {
		return buyerRetailPrice;
	}

	public void setBuyerRetailPrice(Double buyerRetailPrice) {
		this.buyerRetailPrice = buyerRetailPrice;
	}

	public Boolean getOptIn() {
		return optIn;
	}

	public void setOptIn(Boolean optIn) {
		this.optIn = optIn;
	}

	public Double getTotalWeigtage() {
		return totalWeightage;
	}

	public void setTotalWeigtage(Double totalWeightage) {
		this.totalWeightage = totalWeightage;
	}
	
	public Integer getProviderRank() {
		return providerRank;
	}

	public void setProviderRank(Integer providerRank) {
		this.providerRank = providerRank;
	}

	public int compare(D2CProviderAPIVO d2cProviderAPIVO1,
			D2CProviderAPIVO d2cProviderAPIVO2) {
		if (d2cProviderAPIVO1.totalWeightage == d2cProviderAPIVO2.totalWeightage)
			return 0;
		else if (d2cProviderAPIVO1.totalWeightage < d2cProviderAPIVO2.totalWeightage)
			return 1;
		else
			return -1;
	}

}
