package com.newco.marketplace.dto.vo.account;

import com.sears.os.vo.SerializableBaseVO;

public class UserProfile extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6146848797449785346L;
	private String userName;
	private String password;
	private int roleId;
	private String roleName;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
