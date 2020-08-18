package com.newco.marketplace.api.mobile.beans.createNewPassword;


import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the CreateNewPassword
 * @author Infosys
 *
 */
@XSD(name="createNewPasswordRequest.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name = "createNewPasswordRequest")
@XStreamAlias("createNewPasswordRequest")
public class CreateNewPasswordRequest{
	
	@XStreamAlias("userId")
	private String userId;
	
	@XStreamAlias("currentPassword")
	private String currentPassword;
	
	@XStreamAlias("password")
	private String password;
	
	@XStreamAlias("confirmPassword")
	private String confirmPassword;
	
	@XStreamAlias("securityQuestionId")
	private Integer securityQuestionId;
	
	@XStreamAlias("securityAnswer")
	private String securityAnswer;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Integer getSecurityQuestionId() {
		return securityQuestionId;
	}

	public void setSecurityQuestionId(Integer securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	
}

