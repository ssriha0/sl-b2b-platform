package com.newco.marketplace.api.mobile.beans.viewDashboard;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("providerBackgroundDetails")
public class ProviderBackgroundDetails {

	@XStreamImplicit(itemFieldName ="providerBackgroundDetail")
	private List<ProviderBackgroundDetail>providerBackgroundDetail;

	public List<ProviderBackgroundDetail> getProviderBackgroundDetail() {
		return providerBackgroundDetail;
	}

	public void setProviderBackgroundDetail(
			List<ProviderBackgroundDetail> providerBackgroundDetail) {
		this.providerBackgroundDetail = providerBackgroundDetail;
	}
}
