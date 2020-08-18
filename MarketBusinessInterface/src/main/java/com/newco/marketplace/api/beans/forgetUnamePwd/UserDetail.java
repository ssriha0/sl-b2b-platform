package com.newco.marketplace.api.beans.forgetUnamePwd;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a generic bean class for storing part information.
 * @author Infosys
 *
 */
@XStreamAlias("userDetail")
public class UserDetail {
	
	@XStreamAlias("userId")
	private Integer userId;
	
	@XStreamAlias("contactName")
	private String contactName;

	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("userName")
	private String userName;
	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the userFirstName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
