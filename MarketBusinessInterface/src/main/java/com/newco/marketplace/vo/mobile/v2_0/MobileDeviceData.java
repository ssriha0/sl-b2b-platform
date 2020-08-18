package com.newco.marketplace.vo.mobile.v2_0;



public class MobileDeviceData {
	private int id;
	private String deviceId;
	private String deviceOS;
	private String currentAppVersion;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	
}
