package com.newco.marketplace.web.dto;


public class SOWCredentialDTO extends SOWBaseTabDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108502886728220680L;
	private Integer credentialId;
	private String credentialName;
	
	
	
	
	public Integer getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}

	public String getCredentialName() {
		return credentialName;
	}

	public void setCredentialName(String credentialName) {
		this.credentialName = credentialName;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		//return null;
	}

}
