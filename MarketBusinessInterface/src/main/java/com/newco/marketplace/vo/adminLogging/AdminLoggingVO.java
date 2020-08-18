package com.newco.marketplace.vo.adminLogging;

import com.sears.os.vo.SerializableBaseVO;

public class AdminLoggingVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1008865552020256788L;
	private String userId;
	private int companyId;
	private int roleId;
	private int activityId;
	private int loggId;
	private java.sql.Timestamp startTime;
	private java.sql.Timestamp endTime;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getLoggId() {
		return loggId;
	}
	public void setLoggId(int loggId) {
		this.loggId = loggId;
	}
	public java.sql.Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(java.sql.Timestamp startTime) {
		this.startTime = startTime;
	}
	public java.sql.Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(java.sql.Timestamp endTime) {
		this.endTime = endTime;
	}
	
}
