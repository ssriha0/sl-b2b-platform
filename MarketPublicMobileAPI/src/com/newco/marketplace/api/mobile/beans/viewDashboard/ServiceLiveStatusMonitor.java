package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("serviceLiveStatusMonitor")
public class ServiceLiveStatusMonitor {

	@XStreamAlias("firmRegistrationStatus")
	private String firmRegistrationStatus;
	
	@XStreamAlias("providerRegistrationStatus")
	private ProviderRegistrationStatus providerRegistrationStatus;
	
	@XStreamAlias("providerBackgroundCheck")
	private ProviderBackgroundCheck providerBackgroundCheck;
	
	public String getFirmRegistrationStatus() {
		return firmRegistrationStatus;
	}
	public void setFirmRegistrationStatus(String firmRegistrationStatus) {
		this.firmRegistrationStatus = firmRegistrationStatus;
	}
	public ProviderRegistrationStatus getProviderRegistrationStatus() {
		return providerRegistrationStatus;
	}
	public void setProviderRegistrationStatus(
			ProviderRegistrationStatus providerRegistrationStatus) {
		this.providerRegistrationStatus = providerRegistrationStatus;
	}
	public ProviderBackgroundCheck getProviderBackgroundCheck() {
		return providerBackgroundCheck;
	}
	public void setProviderBackgroundCheck(
			ProviderBackgroundCheck providerBackgroundCheck) {
		this.providerBackgroundCheck = providerBackgroundCheck;
	}

}
