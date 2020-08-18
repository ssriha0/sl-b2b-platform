package com.newco.marketplace.api.mobile.beans.vo;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class RecievedOrdersVO {

	private String soId;
	private String orderType;
	private String soTitle;
	private String soStatus;
	private Timestamp serviceStartDate;
	private Timestamp serviceEndDate;
	private String serviceWindowStartTime;
	private String serviceWindowEndTime;
	private String timeZone;
	private String city;
	private String state;
	private String zip;
	private Integer buyerId;
	private String buyerName;
	private String buyerLogo;
	private Timestamp recievedDate;
	private String routedProvider;
	private String productAvailability;
	private String pickupAddress1;
	private String pickupAddress2;
	private String pickupCity;
	private String pickupState;
	private String pickupZip;
	private String pickupDate;
	private BigDecimal spendLimit;
	private BigDecimal spendLimitParts;
	
	//SL-20838
	private Boolean followUpFlag;
	private boolean sealedBidInd;
	private Double bidRangeMax;
	private Double bidRangeMin;
	private Double currentBid;
	private Integer bidCount;
	private Integer resourceId;

	
	public Double getBidRangeMax() {
		return bidRangeMax;
	}
	public void setBidRangeMax(Double bidRangeMax) {
		this.bidRangeMax = bidRangeMax;
	}
	public Double getBidRangeMin() {
		return bidRangeMin;
	}
	public void setBidRangeMin(Double bidRangeMin) {
		this.bidRangeMin = bidRangeMin;
	}
	public Double getCurrentBid() {
		return currentBid;
	}
	public void setCurrentBid(Double currentBid) {
		this.currentBid = currentBid;
	}
	public Integer getBidCount() {
		return bidCount;
	}
	public void setBidCount(Integer bidCount) {
		this.bidCount = bidCount;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSoTitle() {
		return soTitle;
	}
	public void setSoTitle(String soTitle) {
		this.soTitle = soTitle;
	}
	public String getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public Timestamp getServiceStartDate() {
		return serviceStartDate;
	}
	public void setServiceStartDate(Timestamp serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}
	public Timestamp getServiceEndDate() {
		return serviceEndDate;
	}
	public void setServiceEndDate(Timestamp serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}
	public String getServiceWindowStartTime() {
		return serviceWindowStartTime;
	}
	public void setServiceWindowStartTime(String serviceWindowStartTime) {
		this.serviceWindowStartTime = serviceWindowStartTime;
	}
	public String getServiceWindowEndTime() {
		return serviceWindowEndTime;
	}
	public void setServiceWindowEndTime(String serviceWindowEndTime) {
		this.serviceWindowEndTime = serviceWindowEndTime;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerLogo() {
		return buyerLogo;
	}
	public void setBuyerLogo(String buyerLogo) {
		this.buyerLogo = buyerLogo;
	}
	public Timestamp getRecievedDate() {
		return recievedDate;
	}
	public void setRecievedDate(Timestamp recievedDate) {
		this.recievedDate = recievedDate;
	}
	public String getRoutedProvider() {
		return routedProvider;
	}
	public void setRoutedProvider(String routedProvider) {
		this.routedProvider = routedProvider;
	}
	public String getProductAvailability() {
		return productAvailability;
	}
	public void setProductAvailability(String productAvailability) {
		this.productAvailability = productAvailability;
	}
	public String getPickupAddress1() {
		return pickupAddress1;
	}
	public void setPickupAddress1(String pickupAddress1) {
		this.pickupAddress1 = pickupAddress1;
	}
	public String getPickupAddress2() {
		return pickupAddress2;
	}
	public void setPickupAddress2(String pickupAddress2) {
		this.pickupAddress2 = pickupAddress2;
	}
	public String getPickupCity() {
		return pickupCity;
	}
	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}
	public String getPickupState() {
		return pickupState;
	}
	public void setPickupState(String pickupState) {
		this.pickupState = pickupState;
	}
	public String getPickupZip() {
		return pickupZip;
	}
	public void setPickupZip(String pickupZip) {
		this.pickupZip = pickupZip;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public BigDecimal getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(BigDecimal spendLimit) {
		this.spendLimit = spendLimit;
	}
	public BigDecimal getSpendLimitParts() {
		return spendLimitParts;
	}
	public void setSpendLimitParts(BigDecimal spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}
	public Boolean getFollowUpFlag() {
		return followUpFlag;
	}
	public void setFollowUpFlag(Boolean followUpFlag) {
		this.followUpFlag = followUpFlag;
	}
	public boolean isSealedBidInd() {
		return sealedBidInd;
	}
	public void setSealedBidInd(boolean sealedBidInd) {
		this.sealedBidInd = sealedBidInd;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	
}
