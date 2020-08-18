package com.newco.marketplace.api.beans.login;

import javax.xml.bind.annotation.XmlElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;



@XStreamAlias("result")
public class LoginResult {

	@XStreamAlias("successInd")
	@XStreamAsAttribute()
	private String successInd;
	
	@XStreamAlias("success")
	@XmlElement(name = "success")
	private String success;
	
	@XStreamAlias("failure")
	@XmlElement(name = "failure")
	private LoginFailureResult failure;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getSuccessInd() {
		return successInd;
	}

	public void setSuccessInd(String successInd) {
		this.successInd = successInd;
	}

	public LoginFailureResult getFailure() {
		return failure;
	}

	public void setFailure(LoginFailureResult failure) {
		this.failure = failure;
	}

	
}
