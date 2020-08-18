package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class RoleActivityPermissionVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7637351383985337433L;
	private int roleId;
	private int activityId;
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
}
