package com.newco.ofac.vo;

import java.util.Date;

public class BuyerOfacVO {
	
	private Integer buyerID;
	private Integer buyerOfacIndicator;
	private Date lastOfacCheckDate;
	private String businessName;

	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getBuyerOfacIndicator() {
		return buyerOfacIndicator;
	}
	public void setBuyerOfacIndicator(Integer buyerOfacIndicator) {
		this.buyerOfacIndicator = buyerOfacIndicator;
	}
	public Date getLastOfacCheckDate() {
		return lastOfacCheckDate;
	}
	public void setLastOfacCheckDate(Date lastOfacCheckDate) {
		this.lastOfacCheckDate = lastOfacCheckDate;
	}
	public Integer getBuyerID() {
		return buyerID;
	}
	public void setBuyerID(Integer buyerID) {
		this.buyerID = buyerID;
	}
	

}
