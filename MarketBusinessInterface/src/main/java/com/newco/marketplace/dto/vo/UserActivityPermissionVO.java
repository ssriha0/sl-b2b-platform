package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class UserActivityPermissionVO extends SerializableBaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8023095151376538454L;
	private String userName;
	private int activityId;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
}
