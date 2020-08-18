package com.newco.marketplace.web.dto.adminlogging;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class AdminLoggingDto extends SerializedBaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4773440652399455509L;
	private String userId;
	private int companyId;
	private int roleId;
	private int activityId;
	private String activityName;
	private int loggId;
	private String startTime;
	private String endTime;
	
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
}
