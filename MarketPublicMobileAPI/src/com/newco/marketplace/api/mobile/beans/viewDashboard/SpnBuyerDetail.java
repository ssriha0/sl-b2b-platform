package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("spnBuyerDetail")
public class SpnBuyerDetail {

	@XStreamAlias("buyerId")
	private Integer buyerId;
	
	@XStreamAlias("buyerName")
	private String buyerName;
	
	@XStreamAlias("spnDetails")
	private	SpnDetails spnDetails;

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

	public SpnDetails getSpnDetails() {
		return spnDetails;
	}

	public void setSpnDetails(SpnDetails spnDetails) {
		this.spnDetails = spnDetails;
	}

	
}
