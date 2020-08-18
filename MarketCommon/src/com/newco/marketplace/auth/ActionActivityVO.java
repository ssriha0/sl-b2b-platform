package com.newco.marketplace.auth;

import java.io.Serializable;

public class ActionActivityVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8846475199352072070L;
	private int activityId;
	private int roleId;
	private String actionName;
	
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
}
