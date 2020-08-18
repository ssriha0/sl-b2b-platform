package com.newco.marketplace.mobile.authenticate.vo;

import java.util.Date;

public class MobileTokenVO {

	private String deviceId;
	private int resourceId;
	private String outhToken;
	private Date createdDate;
	private Date expiryDate;
	private String tokenStatus;
	private Integer expiryDateLimit;
	private String deviceOS;
	private String currentAppVersion;

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getOuthToken() {
		return outhToken;
	}
	public void setOuthToken(String outhToken) {
		this.outhToken = outhToken;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getTokenStatus() {
		return tokenStatus;
	}
	public void setTokenStatus(String tokenStatus) {
		this.tokenStatus = tokenStatus;
	}
	
	public Integer getExpiryDateLimit() {
		return expiryDateLimit;
	}
	public void setExpiryDateLimit(Integer expiryDateLimit) {
		this.expiryDateLimit = expiryDateLimit;
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
