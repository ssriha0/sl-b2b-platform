package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("EligibleProviders")
public class EligibleProvidersForLead {

	@XStreamImplicit(itemFieldName = "EligibleProvider")
	private List<EligibleProviderForLead> provider;

	public List<EligibleProviderForLead> getProvider() {
		return provider;
	}

	public void setProvider(List<EligibleProviderForLead> provider) {
		this.provider = provider;
	}

}
