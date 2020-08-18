package com.servicelive.marketplatform.client;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.domain.common.Contact;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.MarketPlatformServiceResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import static org.mockito.Mockito.*;

public class MarketPlatformRemoteServiceClientTest{
	
	private static MarketPlatformRemoteServiceClient serviceClient;
	
	@Test
	public void testFetchSpnDetails(){
		serviceClient = new MarketPlatformRemoteServiceClient();
		
		/*
		 * NOT WORKING
		 * 
		 * String baseUrl = "http://10.60.3.47:8480/MarketPlatform.RemoteService.WebServices/rest-services";
		serviceClient.setBaseUrl(baseUrl);
		
		String relativeUrl = "/notification/contacts/providers/10254";
		
		Client client = Client.create();
		//WebResource resource = mock(WebResource.class);
		WebResource resource = client.resource(baseUrl);
		MarketPlatformServiceResponse resp = new MarketPlatformServiceResponse();
		Builder res = resource.path(relativeUrl).type(MediaType.APPLICATION_XML_TYPE).accept(MediaType.APPLICATION_XML_TYPE);
		
		when(resource.path(relativeUrl).accept(MediaType.APPLICATION_XML_TYPE).get(MarketPlatformServiceResponse.class)).thenReturn(resp);
		
		Contact cont = new Contact();
		Long providerRsrcId  = 10254L;
		cont = serviceClient.retrieveProviderResourceContactInfo(providerRsrcId);
		Assert.assertNotNull(cont);*/
		
		Integer spnId = 75;
		SPNHeader hdr = new SPNHeader();
		hdr = serviceClient.fetchSpnDetails(spnId);
		
		Assert.assertTrue(hdr.getIsAlias());
		
	}
	
	
}
