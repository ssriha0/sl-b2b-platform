package com.servicelive.spn.buyer.network;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;

public class SPNMemberManagerModel extends SPNBaseModel
{

	private static final long serialVersionUID = 146677084326614162L;
	
	private List<SPNMemberManagerProviderDTO> providerResults;

	public List<SPNMemberManagerProviderDTO> getProviderResults()
	{
		return providerResults;
	}

	public void setProviderResults(List<SPNMemberManagerProviderDTO> providerResults)
	{
		this.providerResults = providerResults;
	}


}
