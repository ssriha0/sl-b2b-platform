package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of results.
 * @author Infosys
 *
 */
@XStreamAlias("providers")
public class LMSProviders {
	
	@XStreamImplicit(itemFieldName="provider")
	private List<LMSProvider> lmsProvider;

	public List<LMSProvider> getLmsProvider() {
		return lmsProvider;
	}

	public void setLmsProvider(List<LMSProvider> lmsProvider) {
		this.lmsProvider = lmsProvider;
	}

}
