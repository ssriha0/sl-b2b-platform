package com.newco.marketplace.api.beans.search.providerProfile;

/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.search.types.ProviderCredential;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing all information of 
 * the Credential 
 * @author Infosys
 *
 */
@XStreamAlias("credential")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderCredentialItem {


	@XStreamAlias("type")
	private String type;
	
	@XStreamAlias("category")
	private String category;
	
	@XStreamAlias("source")
	private String source;
	
	@XStreamAlias("name")
	private String name;
	
	@XStreamAlias("status")
	@XStreamAsAttribute()  
	private String status;

	@XStreamAlias("credentialType")
	@XStreamAsAttribute()  
	private String credentialType;
	
	public ProviderCredentialItem() {
		
	}
	
	public ProviderCredentialItem(ProviderCredential providerCredential) {
		this.setCategory(providerCredential.getCategory());
		this.setName(providerCredential.getName());
		this.setSource(providerCredential.getSource());
		this.setType(providerCredential.getType());
		this.setStatus(providerCredential.getStatusStr());
		if (providerCredential.getCredentialScope() == ProviderCredential.CREDENTIAL_SCOPE_COMPANY)
			credentialType = "COMPANY";
		else
			credentialType = "PROVIDER";
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}
}
