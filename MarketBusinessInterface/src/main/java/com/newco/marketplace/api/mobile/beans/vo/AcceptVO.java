package com.newco.marketplace.api.mobile.beans.vo;

public class AcceptVO {
	private Integer firmId;
	private String soId;
	private String groupId;
	private Integer resourceIdRequest;
	private Integer resourceIdUrl;
	private Integer acceptedResourceId;
	private Integer roleId;
	private boolean acceptByFirmInd = false;
	private Integer preferenceInd;


	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

    public boolean isAcceptByFirmInd() {
		return acceptByFirmInd;
	}

	public void setAcceptByFirmInd(boolean acceptByFirmInd) {
		this.acceptByFirmInd = acceptByFirmInd;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getResourceIdRequest() {
		return resourceIdRequest;
	}

	public void setResourceIdRequest(Integer resourceIdRequest) {
		this.resourceIdRequest = resourceIdRequest;
	}

	public Integer getResourceIdUrl() {
		return resourceIdUrl;
	}

	public void setResourceIdUrl(Integer resourceIdUrl) {
		this.resourceIdUrl = resourceIdUrl;
	}

	public Integer getAcceptedResourceId() {
		return acceptedResourceId;
	}

	public void setAcceptedResourceId(Integer acceptedResourceId) {
		this.acceptedResourceId = acceptedResourceId;
	}

	public Integer getPreferenceInd() {
		return preferenceInd;
	}

	public void setPreferenceInd(Integer preferenceInd) {
		this.preferenceInd = preferenceInd;
	}
	
}
