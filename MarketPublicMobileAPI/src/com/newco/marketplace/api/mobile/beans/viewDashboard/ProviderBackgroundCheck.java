package com.newco.marketplace.api.mobile.beans.viewDashboard;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerBackgroundCheck")
public class ProviderBackgroundCheck {

	@XStreamAlias("providerBackgroundDetails")
	private ProviderBackgroundDetails providerBackgroundDetails;

	public ProviderBackgroundDetails getProviderBackgroundDetails() {
		return providerBackgroundDetails;
	}

	public void setProviderBackgroundDetails(
			ProviderBackgroundDetails providerBackgroundDetails) {
		this.providerBackgroundDetails = providerBackgroundDetails;
	}
}
