package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;

public class BuyerCancellationPostingFeeVO {

	private Integer buyerId;
	private int fundingTypeId;
	private String description;
	private Double buyerCancellationRefund;
	private Integer busTransId;
	private java.sql.Timestamp effectiveDate;

	public BuyerCancellationPostingFeeVO(Integer buyerId, int fundingTypeId, String description,
			Double buyerCancellationRefund, Integer busTransId, Timestamp effectiveDate) {
		
		this.buyerId = buyerId;
		this.fundingTypeId = fundingTypeId;
		this.description = description;
		this.buyerCancellationRefund = buyerCancellationRefund;
		this.busTransId = busTransId;
		this.effectiveDate = effectiveDate;
	}

	public java.sql.Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(java.sql.Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public int getFundingTypeId() {
		return fundingTypeId;
	}

	public void setFundingTypeId(int fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBuyerCancellationRefund() {
		return buyerCancellationRefund;
	}

	public void setBuyerCancellationRefund(Double buyerCancellationRefund) {
		this.buyerCancellationRefund = buyerCancellationRefund;
	}

	public Integer getBusTransId() {
		return busTransId;
	}

	public void setBusTransId(Integer busTransId) {
		this.busTransId = busTransId;
	}

}
