package com.newco.marketplace.vo.common;

import com.sears.os.vo.SerializableBaseVO;

public class UserProfilePermissionSetVO extends SerializableBaseVO{
	private Integer roleId;
	private Integer entityId;
	boolean getInactive;
	boolean callerSuperAdmin;
	private String userName;
	private Integer vendBuyerResId;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public boolean isGetInactive() {
		return getInactive;
	}
	public void setGetInactive(boolean getInactive) {
		this.getInactive = getInactive;
	}
	public boolean isCallerSuperAdmin() {
		return callerSuperAdmin;
	}
	public void setCallerSuperAdmin(boolean callerSuperAdmin) {
		this.callerSuperAdmin = callerSuperAdmin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getVendBuyerResId() {
		return vendBuyerResId;
	}
	public void setVendBuyerResId(Integer vendBuyerResId) {
		this.vendBuyerResId = vendBuyerResId;
	}
}
