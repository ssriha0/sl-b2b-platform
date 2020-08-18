/**
 * 
 */
package com.newco.marketplace.vo.mobile;

/**
 * @author karthik_hariharan01
 *
 */
public class UserDetailsVO {
	
	private Integer activityId;
	private String activityName;
	private boolean primaryInd;
	private boolean adminInd;
	private boolean dispatcherInd;

	/**
	 * @return the activityId
	 */
	public Integer getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}
	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	/**
	 * @return the primaryInd
	 */
	public boolean isPrimaryInd() {
		return primaryInd;
	}
	/**
	 * @param primaryInd the primaryInd to set
	 */
	public void setPrimaryInd(boolean primaryInd) {
		this.primaryInd = primaryInd;
	}
	/**
	 * @return the adminInd
	 */
	public boolean isAdminInd() {
		return adminInd;
	}
	/**
	 * @param adminInd the adminInd to set
	 */
	public void setAdminInd(boolean adminInd) {
		this.adminInd = adminInd;
	}
	/**
	 * @return the dispatcherInd
	 */
	public boolean isDispatcherInd() {
		return dispatcherInd;
	}
	/**
	 * @param dispatcherInd the dispatcherInd to set
	 */
	public void setDispatcherInd(boolean dispatcherInd) {
		this.dispatcherInd = dispatcherInd;
	}
	
	
	
}
