package com.servicelive.spn.buyer.network;

import java.util.List;

public class SPNMemberManagerProviderDTO
{
	private String vendorResourceName;
	private Integer vendorResourceID;
	private Integer vendorID;
	private String companyStatus;
	private String providerStatus;
	
	
	private List<SPNMMNetworkDTO> networkList;


	public String getVendorResourceName()
	{
		return vendorResourceName;
	}


	public void setVendorResourceName(String vendorResourceName)
	{
		this.vendorResourceName = vendorResourceName;
	}




	public String getProviderStatus()
	{
		return providerStatus;
	}


	public void setProviderStatus(String providerStatus)
	{
		this.providerStatus = providerStatus;
	}


	public List<SPNMMNetworkDTO> getNetworkList()
	{
		return networkList;
	}


	public void setNetworkList(List<SPNMMNetworkDTO> networkList)
	{
		this.networkList = networkList;
	}


	public String getCompanyStatus()
	{
		return companyStatus;
	}


	public void setCompanyStatus(String companyStatus)
	{
		this.companyStatus = companyStatus;
	}


	public Integer getVendorResourceID()
	{
		return vendorResourceID;
	}


	public void setVendorResourceID(Integer vendorResourceID)
	{
		this.vendorResourceID = vendorResourceID;
	}


	public Integer getVendorID()
	{
		return vendorID;
	}


	public void setVendorID(Integer vendorID)
	{
		this.vendorID = vendorID;
	}
	
	
}
