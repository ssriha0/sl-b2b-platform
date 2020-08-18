package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerRegistrationStatus")
public class ProviderRegistrationStatus {

	@XStreamAlias("providerRegistrationDetails")
	private ProviderRegistrationDetails providerRegistrationDetails;

	public ProviderRegistrationDetails getProviderRegistrationDetails() {
		return providerRegistrationDetails;
	}

	public void setProviderRegistrationDetails(
			ProviderRegistrationDetails providerRegistrationDetails) {
		this.providerRegistrationDetails = providerRegistrationDetails;
	}
}
