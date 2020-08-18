package com.newco.marketplace.api.mobile.beans.authenticateMobileVersionCheck.v2_0;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="mobileVersionCheckRequest.xsd", path="/resources/schemas/mobile/v2_0/")
@XmlRootElement(name="mobileVersionCheckRequest")
@XStreamAlias("mobileVersionCheckRequest")
public class AuthenticateMobileVersionRequest {

	@XStreamAlias("username")
	private String username;
	
	@XStreamAlias("deviceName")   
	private String deviceName;
	
	@XStreamAlias("deviceOS")   
	private String deviceOS;

	@XStreamAlias("currentAppVersion")   
	private String currentAppVersion;
	
	
	@XStreamAlias("deviceId")   
	private String deviceId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceOS() {
		return deviceOS;
	}

	public void setDeviceOS(String deviceOS) {
		this.deviceOS = deviceOS;
	}

	public String getCurrentAppVersion() {
		return currentAppVersion;
	}

	public void setCurrentAppVersion(String currentAppVersion) {
		this.currentAppVersion = currentAppVersion;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
}
