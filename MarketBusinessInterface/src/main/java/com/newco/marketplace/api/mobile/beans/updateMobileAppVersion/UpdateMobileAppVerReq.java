package com.newco.marketplace.api.mobile.beans.updateMobileAppVersion;

public class UpdateMobileAppVerReq{
	private int id;
	private String baseAppVersion;
	private String latestAppVersion;
	private String modifiedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}