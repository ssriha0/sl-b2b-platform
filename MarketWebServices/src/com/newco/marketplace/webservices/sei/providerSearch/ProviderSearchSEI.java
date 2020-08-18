package com.newco.marketplace.webservices.sei.providerSearch;



import javax.jws.WebService;

import com.newco.marketplace.dto.request.providerSearch.ProviderSearchRequest;
import com.newco.marketplace.dto.response.providerSearch.ProviderSearchResponse;

/**
 * @author zizrale
 *
 */
@WebService(name="ProviderSearch", targetNamespace="http://provider.providersearch.soup.com")
public interface ProviderSearchSEI {

	/**
	 * @param request
	 * @return ProviderSearchResponse
	 */
	public ProviderSearchResponse getProviderList(ProviderSearchRequest request);
}
