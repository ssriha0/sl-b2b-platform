package com.newco.marketplace.dto.vo.autoAcceptance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ManageAutoOrderAcceptanceDTO implements Serializable
{
	private static final long serialVersionUID = -1527770441184997895L;
	
	private String buyer;
	private Integer buyerId;
	private Integer vendorId;
	private String ruleName;
	private Date createdDate;
	private Integer routingRuleId;
	private boolean updatedInd;
	private String userName;
	private String autoAcceptStatus;
	private String turnOffReason;
	private String ruleStatus;
	private String adoptedBy;
	private String emailId;
	private Integer resourceId;
	//SL-20436
	private boolean opportunityEmailInd=true;
	
	private List<Integer> ruleId;
	private List<String> ruleNames;
	private List<String> status;
	private List<String> reason;
	private List<Boolean> updated;
	private List<Integer> buyerIds;
	
	//SL-20436
	private List<Boolean> opportunityEmailIndList;
	
	private String providerName;
	private String primaryPhNo;
	private String altPhNo;
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getPrimaryPhNo() {
		return primaryPhNo;
	}
	public void setPrimaryPhNo(String primaryPhNo) {
		this.primaryPhNo = primaryPhNo;
	}
	public String getAltPhNo() {
		return altPhNo;
	}
	public void setAltPhNo(String altPhNo) {
		this.altPhNo = altPhNo;
	}
	public List<Integer> getRuleId() {
		return ruleId;
	}
	public void setRuleId(List<Integer> ruleId) {
		this.ruleId = ruleId;
	}
	public String getAutoAcceptStatus() {
		return autoAcceptStatus;
	}
	public void setAutoAcceptStatus(String autoAcceptStatus) {
		this.autoAcceptStatus = autoAcceptStatus;
	}
	public String getTurnOffReason() {
		return turnOffReason;
	}
	public void setTurnOffReason(String turnOffReason) {
		this.turnOffReason = turnOffReason;
	}
	public List<String> getStatus() {
		return status;
	}
	public void setStatus(List<String> status) {
		this.status = status;
	}
	public List<String> getReason() {
		return reason;
	}
	public void setReason(List<String> reason) {
		this.reason = reason;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public boolean isUpdatedInd() {
		return updatedInd;
	}
	public void setUpdatedInd(boolean updatedInd) {
		this.updatedInd = updatedInd;
	}
	public Integer getRoutingRuleId() {
		return routingRuleId;
	}
	public void setRoutingRuleId(Integer routingRuleId) {
		this.routingRuleId = routingRuleId;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public List<Boolean> getUpdated() {
		return updated;
	}
	public void setUpdated(List<Boolean> updated) {
		this.updated = updated;
	}
	public String getAdoptedBy() {
		return adoptedBy;
	}
	public void setAdoptedBy(String adoptedBy) {
		this.adoptedBy = adoptedBy;
	}
	public List<String> getRuleNames() {
		return ruleNames;
	}
	public void setRuleNames(List<String> ruleNames) {
		this.ruleNames = ruleNames;
	}
	public List<Integer> getBuyerIds() {
		return buyerIds;
	}
	public void setBuyerIds(List<Integer> buyerIds) {
		this.buyerIds = buyerIds;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	//SL-20436
	public boolean isOpportunityEmailInd() {
		return opportunityEmailInd;
	}
	public void setOpportunityEmailInd(boolean opportunityEmailInd) {
		this.opportunityEmailInd = opportunityEmailInd;
	}
	public List<Boolean> getOpportunityEmailIndList() {
		return opportunityEmailIndList;
	}
	public void setOpportunityEmailIndList(List<Boolean> opportunityEmailIndList) {
		this.opportunityEmailIndList = opportunityEmailIndList;
	}
	
	
	
	
	
}
