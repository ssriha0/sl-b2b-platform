package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class IncreaseSpendLimitRequestVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7345380315508855728L;
	Integer buyerId; 
	String serviceOrderID;
	Double increaseSpendLimit;
	Double increaseSpendLimitParts;
	Double currentSpendLimit;
	Double addedSpendLimit;
	String comment;
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}
	public Double getIncreaseSpendLimit() {
		return increaseSpendLimit;
	}
	public Double getIncreaseSpendLimitParts() {
		return increaseSpendLimitParts;
	}
	public void setIncreaseSpendLimitParts(Double increaseSpendLimitParts) {
		this.increaseSpendLimitParts = increaseSpendLimitParts;
	}
	public void setIncreaseSpendLimit(Double increaseSpendLimit) {
		this.increaseSpendLimit = increaseSpendLimit;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Double getCurrentSpendLimit() {
		return currentSpendLimit;
	}
	public void setCurrentSpendLimit(Double currentSpendLimit) {
		this.currentSpendLimit = currentSpendLimit;
	}
	public Double getAddedSpendLimit() {
		return addedSpendLimit;
	}
	public void setAddedSpendLimit(Double addedSpendLimit) {
		this.addedSpendLimit = addedSpendLimit;
	}
}
