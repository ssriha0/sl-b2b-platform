package com.newco.marketplace.web.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SOPendingCancelDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8179705660168610109L;
	
	
	private String cancelComment;
	private double cancelAmt;
	private double cancelAmount;
	private String soId;
	private Integer roleId;
	private String action;
	String adminUserName;
	String adminResourecId;
	String userName;
	String userId;
	
	
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
	public double getCancelAmount() {
		return cancelAmount;
	}
	public void setCancelAmount(double cancelAmount) {
		this.cancelAmount = cancelAmount;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
	
	
	
	
}
