package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.fetchOrders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("providerList")
public class ProviderFilterList {
	
	@XStreamImplicit(itemFieldName="providers")
	private List<ProviderFilterVO> providers;

	public List<ProviderFilterVO> getProviders() {
		return providers;
	}

	public void setProviders(List<ProviderFilterVO> providers) {
		this.providers = providers;
	}


}
