package com.newco.marketplace.api.beans.hi.account.create.provider;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;



@XStreamAlias("credentials")
public class Credentials {
    
	@XStreamImplicit(itemFieldName="credential")
	private List<Credential> credential;

	public List<Credential> getCredential() {
		return credential;
	}

	public void setCredential(List<Credential> credential) {
		this.credential = credential;
	}
   
    
}
