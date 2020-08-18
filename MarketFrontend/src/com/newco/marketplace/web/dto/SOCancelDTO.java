package com.newco.marketplace.web.dto;

import java.util.HashMap;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.dto.vo.ManageTaskVO;

public class SOCancelDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8179705660168610109L;
	private String soId;
	private int buyerId;
	private String cancelComment;
	private double cancelAmt;
	private String buyerName;
	private int statusCd;
	private String reason;
	private String comments;
	private Integer reasonCode;
	private boolean tripChargeOverride;
	private boolean providerPaymentCheck;
	private boolean providerApproveIndicator;
	private String soPricing;
	private boolean within24Hours;
	private int wfStateId;
	private String scopeChangeCancelComments;
	private String scopeChangeCancelReason;
	private Integer scopeChangeCancelReasonCode;
	private String adminUserName;
	private String adminResourecId;
	private String userName;
	private String userId;
	HashMap<Integer, ManageTaskVO> scopeChangeTasks;
	private boolean nonFunded;

	
	public HashMap<Integer, ManageTaskVO> getScopeChangeTasks() {
		return scopeChangeTasks;
	}
	public void setScopeChangeTasks(HashMap<Integer, ManageTaskVO> scopeChangeTasks) {
		this.scopeChangeTasks = scopeChangeTasks;
	}
	public int getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(int wfStateId) {
		this.wfStateId = wfStateId;
	}
	public boolean isTripChargeOverride() {
		return tripChargeOverride;
	}
	public void setTripChargeOverride(boolean tripChargeOverride) {
		this.tripChargeOverride = tripChargeOverride;
	}
	public boolean isProviderApproveIndicator() {
		return providerApproveIndicator;
	}
	public void setProviderApproveIndicator(boolean providerApproveIndicator) {
		this.providerApproveIndicator = providerApproveIndicator;
	}
	public boolean isProviderPaymentCheck() {
		return providerPaymentCheck;
	}
	public void setProviderPaymentCheck(boolean providerPaymentCheck) {
		this.providerPaymentCheck = providerPaymentCheck;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public int getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}
	public String getCancelComment() {
		return cancelComment;
	}
	public void setCancelComment(String cancelComment) {
		this.cancelComment = cancelComment;
	}
	public double getCancelAmt() {
		return cancelAmt;
	}
	public void setCancelAmt(double cancelAmt) {
		this.cancelAmt = cancelAmt;
	}
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("soId", getSoId())
			.append("buyerId", getBuyerId())
			.append("cancelComment", getCancelComment())
			.append("cancelAmt", getCancelAmt())	
			.append("buyerName", getBuyerName())
			.append("statusCd", getStatusCd())
			.toString();
	}
	public int getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(int statusCd) {
		this.statusCd = statusCd;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSoPricing() {
		return soPricing;
	}
	public void setSoPricing(String soPricing) {
		this.soPricing = soPricing;
	}
	public boolean isWithin24Hours() {
		return within24Hours;
	}
	public void setWithin24Hours(boolean within24Hours) {
		this.within24Hours = within24Hours;
	}
	public String getScopeChangeCancelComments() {
		return scopeChangeCancelComments;
	}
	public void setScopeChangeCancelComments(String scopeChangeCancelComments) {
		this.scopeChangeCancelComments = scopeChangeCancelComments;
	}
	public String getScopeChangeCancelReason() {
		return scopeChangeCancelReason;
	}
	public void setScopeChangeCancelReason(String scopeChangeCancelReason) {
		this.scopeChangeCancelReason = scopeChangeCancelReason;
	}
	public Integer getScopeChangeCancelReasonCode() {
		return scopeChangeCancelReasonCode;
	}
	public void setScopeChangeCancelReasonCode(Integer scopeChangeCancelReasonCode) {
		this.scopeChangeCancelReasonCode = scopeChangeCancelReasonCode;
	}
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getAdminResourecId() {
		return adminResourecId;
	}
	public void setAdminResourecId(String adminResourecId) {
		this.adminResourecId = adminResourecId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isNonFunded() {
		return nonFunded;
	}
	public void setNonFunded(boolean nonFunded) {
		this.nonFunded = nonFunded;
	}
	
	


	
}
