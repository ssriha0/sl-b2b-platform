package com.newco.marketplace.vo.mobile;

public class AppVersionData {
	
	private String deviceOS;
	private String baseAppVersion;
	private String latestAppVersion;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
