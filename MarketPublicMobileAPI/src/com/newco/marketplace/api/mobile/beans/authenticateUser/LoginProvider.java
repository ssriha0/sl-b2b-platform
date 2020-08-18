package com.newco.marketplace.api.mobile.beans.authenticateUser;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("loginProvider")
public class LoginProvider {

	@XStreamAlias("deviceId")   
	private String deviceId;
	
	@XStreamAlias("userName")
	private String userName;
	
	@XStreamAlias("password")
	private String password;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
