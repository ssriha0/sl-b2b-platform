package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("eligibleProviders")   
public class EligibleProviders{
	
	@XStreamImplicit(itemFieldName="eligibleProvider")
	private List<EligibleProvider> eligibleProviderList;

	public List<EligibleProvider> getEligibleProviderList() {
		return eligibleProviderList;
	}

	public void setEligibleProviderList(List<EligibleProvider> eligibleProviderList) {
		this.eligibleProviderList = eligibleProviderList;
	}
	
}
