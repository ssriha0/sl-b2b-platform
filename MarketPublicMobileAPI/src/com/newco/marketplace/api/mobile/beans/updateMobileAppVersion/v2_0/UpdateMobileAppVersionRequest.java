package com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.v2_0;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name="updateMobileAppVersionRequest.xsd", path="/resources/schemas/mobile/v2_0/")
@XmlRootElement(name="updateMobileAppVersionRequest")
@XStreamAlias("updateMobileAppVersionRequest")
public class UpdateMobileAppVersionRequest {

	@XStreamAlias("username")
	private String username;
	
	@XStreamAlias("deviceOS")   
	private String deviceOS;
	
	@XStreamAlias("baseAppVersion")   
	private String baseAppVersion;
	
	@XStreamAlias("latestAppVersion")   
	private String latestAppVersion;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeviceOS() {
		return deviceOS;
	}

	public void setDeviceOS(String deviceOS) {
		this.deviceOS = deviceOS;
	}

	public String getBaseAppVersion() {
		return baseAppVersion;
	}

	public void setBaseAppVersion(String baseAppVersion) {
		this.baseAppVersion = baseAppVersion;
	}

	public String getLatestAppVersion() {
		return latestAppVersion;
	}

	public void setLatestAppVersion(String latestAppVersion) {
		this.latestAppVersion = latestAppVersion;
	}

	
	
}