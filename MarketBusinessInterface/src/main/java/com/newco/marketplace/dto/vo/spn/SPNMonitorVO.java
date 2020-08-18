package com.newco.marketplace.dto.vo.spn;

import java.io.Serializable;

public class SPNMonitorVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -645331214344123531L;
	private String buyerName;
	private String spnName;
	private String membershipStatus;
	private String message;
	private int qualifiedProviders;
	private int totalProviders;
	private int spnId;
	private int buyerId;
	private String performanceCriteriaLevel;
	private String routingPriorityStatus;
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public String getMembershipStatus() {
		return membershipStatus;
	}
	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getQualifiedProviders() {
		return qualifiedProviders;
	}
	public void setQualifiedProviders(int qualifiedProviders) {
		this.qualifiedProviders = qualifiedProviders;
	}
	public int getTotalProviders() {
		return totalProviders;
	}
	public void setTotalProviders(int totalProviders) {
		this.totalProviders = totalProviders;
	}
	public int getSpnId() {
		return spnId;
	}
	public void setSpnId(int spnId) {
		this.spnId = spnId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getPerformanceCriteriaLevel() {
		return performanceCriteriaLevel;
	}
	public void setPerformanceCriteriaLevel(String performanceCriteriaLevel) {
		this.performanceCriteriaLevel = performanceCriteriaLevel;
	}
	public String getRoutingPriorityStatus() {
		return routingPriorityStatus;
	}
	public void setRoutingPriorityStatus(String routingPriorityStatus) {
		this.routingPriorityStatus = routingPriorityStatus;
	}
	
}
