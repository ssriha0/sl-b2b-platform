package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Credential")
public class SubCredentials {

	@XStreamAlias("Status")
	@XStreamAsAttribute()
	private String status;

	@XStreamAlias("CredentialType")
	@XStreamAsAttribute()
	private String credentialType;

	@XStreamAlias("Type")
	private String type;

	@XStreamAlias("Category")
	private String category;

	@XStreamAlias("Source")
	private String source;

	@XStreamAlias("Name")
	private String name;

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
