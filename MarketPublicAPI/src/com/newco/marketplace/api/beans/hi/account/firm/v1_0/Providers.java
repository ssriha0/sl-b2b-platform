package com.newco.marketplace.api.beans.hi.account.firm.v1_0;

import java.util.List;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("providers")
public class Providers {

	@XStreamImplicit(itemFieldName = "provider")
	List<Provider> provider;

	public List<Provider> getProvider() {
		return provider;
	}

	public void setProvider(List<Provider> provider) {
		this.provider = provider;
	}
}
