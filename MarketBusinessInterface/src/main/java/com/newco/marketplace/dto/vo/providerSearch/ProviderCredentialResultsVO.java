package com.newco.marketplace.dto.vo.providerSearch;

import com.newco.marketplace.vo.MPBaseVO;


public class ProviderCredentialResultsVO extends MPBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2188667294470906864L;
	private int providerResourceId;
	private int providerCredentialId;
	private int providerCredentialTypeId;
	
	public int getProviderResourceId() {
		return providerResourceId;
	}
	public void setProviderResourceId(int providerResourceId) {
		this.providerResourceId = providerResourceId;
	}
	public int getProviderCredentialId() {
		return providerCredentialId;
	}
	public void setProviderCredentialId(int providerCredentialId) {
		this.providerCredentialId = providerCredentialId;
	}
	
	public int getProviderCredentialTypeId() {
		return providerCredentialTypeId;
	}
	public void setProviderCredentialTypeId(int providerCredentialTypeId) {
		this.providerCredentialTypeId = providerCredentialTypeId;
	}
	@Override
	public String toString () {
	    return (
	            "<ProviderCredentialResultVO>" 
	            + providerResourceId 
	            + providerCredentialId 
	            + "</ProviderCredentialId>");
	}
}
