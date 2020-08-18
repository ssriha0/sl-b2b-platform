package com.newco.marketplace.webservices.seiImpl.providerSearch;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.newco.marketplace.dto.request.providerSearch.ProviderSearchRequest;
import com.newco.marketplace.dto.response.providerSearch.ProviderSearchResponse;
import com.newco.marketplace.webservices.dispatcher.providerSearch.ProviderSearchDispatcher;
import com.newco.marketplace.webservices.sei.providerSearch.ProviderSearchSEI;

/**
 * @author zizrale
 *
 */
@WebService(endpointInterface="com.newco.services.provider.sei.ProviderSearchSEI", serviceName="ProviderSearch")
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public class ProviderSearchImpl  implements ProviderSearchSEI{
	
	public ProviderSearchResponse getProviderList(ProviderSearchRequest request){
		ProviderSearchDispatcher providerSearchDispatcher = new ProviderSearchDispatcher();
		return providerSearchDispatcher.getProviderList(request);
	}
}
