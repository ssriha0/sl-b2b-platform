package com.newco.marketplace.api.mobile.beans.viewDashboard;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("providerRegistrationDetails")
public class ProviderRegistrationDetails {

	@XStreamImplicit(itemFieldName ="providerRegistrationDetail")
	private List<ProviderRegistrationDetail> providerRegistrationDetail;

	public List<ProviderRegistrationDetail> getProviderRegistrationDetail() {
		return providerRegistrationDetail;
	}

	public void setProviderRegistrationDetail(
			List<ProviderRegistrationDetail> providerRegistrationDetail) {
		this.providerRegistrationDetail = providerRegistrationDetail;
	}
}
