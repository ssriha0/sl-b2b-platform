package com.servicelive.spn.buyer.membermanagement;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;

public class SPNProviderDetailsNetworksModel extends SPNBaseModel {

	private static final long serialVersionUID = 20100202L;

	private List<SPNProviderDetailsNetworkDTO> networks;
	private Integer providerCount;
	private Integer spnCount;
	

	private String errorMessage = null;
	
	
	
	public List<SPNProviderDetailsNetworkDTO> getNetworks() {
		return networks;
	}

	public void setNetworks(List<SPNProviderDetailsNetworkDTO> networks) {
		this.networks = networks;
	}

	/**
	 * @return the providerCount
	 */
	public Integer getProviderCount()
	{
		return providerCount;
	}

	/**
	 * @param providerCount the providerCount to set
	 */
	public void setProviderCount(Integer providerCount)
	{
		this.providerCount = providerCount;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public Integer getSpnCount() {
		return spnCount;
	}

	public void setSpnCount(Integer spnCount) {
		this.spnCount = spnCount;
	}
	
	


}
