package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerRegistrationDetail")
public class ProviderRegistrationDetail {

	@XStreamAlias("registrationStatus")
	private String registrationStatus;
	
	@XStreamAlias("registrationStatusCount")
	private Integer registrationStatusCount;

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public Integer getRegistrationStatusCount() {
		return registrationStatusCount;
	}

	public void setRegistrationStatusCount(Integer registrationStatusCount) {
		this.registrationStatusCount = registrationStatusCount;
	}
}
