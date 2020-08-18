package com.newco.marketplace.dto.vo;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

public class AccessControlList extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9095938517192652937L;
	private String userName;
	private ArrayList<ActivityVO> activities;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ArrayList<ActivityVO> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<ActivityVO> activities) {
		this.activities = activities;
	}
}
