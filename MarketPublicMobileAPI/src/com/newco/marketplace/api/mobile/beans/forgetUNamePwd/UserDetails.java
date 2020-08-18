package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Infosys
 *
 */
@XStreamAlias("userDetails")
public class UserDetails {

	@XStreamAlias("userName")
	private String userName;
	
	@XStreamAlias("userId")
	private Integer userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
}
