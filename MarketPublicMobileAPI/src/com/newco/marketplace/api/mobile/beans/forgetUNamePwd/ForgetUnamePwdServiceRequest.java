package com.newco.marketplace.api.mobile.beans.forgetUNamePwd;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the ForgetUnamePwdService1Request
 * @author Infosys
 *
 */
@XSD(name = "forgetUnamePwdServiceRequest.xsd", path = "/resources/schemas/mobile/v3_0/")
@XmlRootElement(name = "forgetUnamePwdServiceRequest")
@XStreamAlias("forgetUnamePwdServiceRequest")
public class ForgetUnamePwdServiceRequest {
	
	@XStreamAlias("requestFor")
	private String requestFor;
	
	@XStreamAlias("email")
	private String email;
	
	@XStreamAlias("userName")
	private String userName;

	@XStreamAlias("userId")
	private Integer userId;

	/**
	 * @return the requestFor
	 */
	public String getRequestFor() {
		return requestFor;
	}

	/**
	 * @param requestFor the requestFor to set
	 */
	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
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
	
	

}
