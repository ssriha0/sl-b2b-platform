/**
 * 
 */
package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author MTedder
 *
 */
public class ServiceProviderRegistrationVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3174208060694866784L;
	private String password="";
	private String email="";
	private String userName="";
	private Integer resourceId = 0;
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
