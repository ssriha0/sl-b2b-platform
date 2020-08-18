package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerFirm")
public class Provider {
	
	@XStreamAlias("providerId")
	private Integer providerId;
	
	@XStreamAlias("backgroundCheck")
	private BackgroundCheck backgroundCheck;
	
	@XStreamAlias("providerStatus")
	private ProviderStatus providerStatus;
	

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public ProviderStatus getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(ProviderStatus providerStatus) {
		this.providerStatus = providerStatus;
	}

	public BackgroundCheck getBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(BackgroundCheck backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

	
}
