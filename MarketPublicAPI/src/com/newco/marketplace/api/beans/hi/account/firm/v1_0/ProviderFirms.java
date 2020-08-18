package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("providerFirms")
public class ProviderFirms {

	@XStreamImplicit(itemFieldName = "providerFirm")
	List<ProviderFirm> providerFirm;

	public List<ProviderFirm> getProviderFirm() {
		return providerFirm;
	}

	public void setProviderFirm(List<ProviderFirm> providerFirm) {
		this.providerFirm = providerFirm;
	}
}
