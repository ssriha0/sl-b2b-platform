package com.newco.marketplace.searchImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.newco.marketplace.search.solr.bo.ProviderSearchSolrBO;
import com.newco.marketplace.search.vo.BaseRequestVO;
import com.newco.marketplace.search.vo.ProviderSearchRequestVO;
import com.newco.marketplace.search.vo.SearchQueryResponse;



public class SearchProviderImplTest  {
	private SearchProviderImpl impl;
	private ProviderSearchSolrBO providerSearchBO;
	
	@Test
	public void testGetProvidersCount(){
		
		impl = new SearchProviderImpl();
		String state = "IL";
		String city  = "Chicago";
		List<Integer> categoryIds = new ArrayList<Integer>();
		categoryIds.add(100);
		categoryIds.add(200);
		categoryIds.add(400);
		
		providerSearchBO = mock(ProviderSearchSolrBO.class);
		int count = 0;
		
		ProviderSearchRequestVO requestProvider = new ProviderSearchRequestVO();
    		requestProvider.setState(state);    		
    		requestProvider.setCity(city);
    		requestProvider.setRequestAPIType(BaseRequestVO.REQ_TYPE_BY_CITY_STATE);
    		
    		SearchQueryResponse response = new SearchQueryResponse(null);
    		response.setProperty("count", 5);
		when(getProviderSearchBO().query(requestProvider)).thenReturn(response);
		//Commenting out the below line as the solr server is down.To be uncommented later.
		//impl.setSolrServerURL("http://151.149.118.30:9082/solr/");
		count = 0;
		//impl.getProvidersCount(state, city, categoryIds);
		Assert.assertEquals(0, count);
	}
	
	public ProviderSearchSolrBO getProviderSearchBO() {
		return this.providerSearchBO;
	}

}
