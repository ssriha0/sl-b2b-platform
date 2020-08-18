package com.newco.marketplace.api.beans.search.provider;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import com.newco.marketplace.vo.apiSearch.SearchProviderRequestCriteria;

@XmlRootElement(name = "searchProviderRequest")
public class SearchProviderRequest{
	
	
	private List<SearchProviderRequestCriteria> providerRequests = new ArrayList<SearchProviderRequestCriteria>();
	
	@XmlElement (name = "providerRequest")	
	public List<SearchProviderRequestCriteria> getProviderRequests() {
		return providerRequests;
	}

	public void setProviderRequests(
			List<SearchProviderRequestCriteria> providerRequests) {
		this.providerRequests = providerRequests;
	}
}