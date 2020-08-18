package com.newco.marketplace.dto.vo;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

public class ActivityTemplate extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5116345473833060109L;
	private int roleId;
	private ArrayList<ActivityVO> activities;
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public ArrayList<ActivityVO> getActivities() {
		return activities;
	}
	public void setActivities(ArrayList<ActivityVO> activities) {
		this.activities = activities;
	}
}
