package com.newco.ofac.vo;

import java.util.Date;

public class ProviderOfacVO {
	
	private Integer providerID;
	private Integer providerOfacIndicator;
	private Date lastOfacCheckDate;
	private String businessName;
	

	public Integer getProviderOfacIndicator() {
		return providerOfacIndicator;
	}
	public void setProviderOfacIndicator(Integer providerOfacIndicator) {
		this.providerOfacIndicator = providerOfacIndicator;
	}
	public Date getLastOfacCheckDate() {
		return lastOfacCheckDate;
	}
	public void setLastOfacCheckDate(Date lastOfacCheckDate) {
		this.lastOfacCheckDate = lastOfacCheckDate;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getProviderID() {
		return providerID;
	}
	public void setProviderID(Integer providerID) {
		this.providerID = providerID;
	}
	

}
