package com.newco.marketplace.api.provider.beans.authenticateUser.v2_0;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="proLoginRequest.xsd", path="/resources/schemas/mobile/")
@XmlRootElement(name="loginProviderRequest")
@XStreamAlias("loginProviderRequest")
public class LoginProviderRequest {

	@XStreamAlias("username")
	private String username;
	
	@XStreamAlias("password")
	private String password;
	
	@XStreamAlias("deviceId")   
	private String deviceId;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
