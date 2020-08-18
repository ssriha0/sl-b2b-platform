package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Credentials")
public class Credentials {
	
	@XStreamImplicit(itemFieldName = "Credential")
	private List<SubCredentials> subCredentials;

	public List<SubCredentials> getSubCredentials() {
		return subCredentials;
	}

	public void setSubCredentials(List<SubCredentials> subCredentials) {
		this.subCredentials = subCredentials;
	}

	
	
	

}