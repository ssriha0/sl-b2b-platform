package com.newco.marketplace.mobile.authenticate.vo;

public class AuthenticateUserVO {

	
	protected String userName = null;
	
	protected String activityName = null;

	protected String activityId = null;
	
	protected String dispatchInd = null;
	
	protected  boolean checked = false;
	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String toString(){
		return "activityName:" + activityName + "|activityId:" + activityId +"|dispatchInd:" + dispatchInd + "|checked" + checked + "|username"+ userName + "\n";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String inUserName) {
		userName = inUserName;
	}
	public String getDispatchInd() {
		return dispatchInd;
	}
	public void setDispatchInd(String dispatchInd) {
		this.dispatchInd = dispatchInd;
	}                
	
}
