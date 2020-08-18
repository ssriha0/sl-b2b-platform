/**
 * 
 */
package com.newco.marketplace.api.mobile.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.eligibleProviders.GetEligibleProviderResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
/**
 * @author Infosys
 *
 */
public class GetEligibleProvidersTest {
	private MobileGenericMapper mapper;
	
	@Before
	public void setUp() {
		mapper = new MobileGenericMapper();
		
	}
	@Test
	public void validateResponse(){
		GetEligibleProviderResponse soGetEligibleProvResponse = mapper.mapEligibleProviderResponse(null, null);
		Assert.assertNotNull(soGetEligibleProvResponse);
		Assert.assertNotNull(soGetEligibleProvResponse.getResults());
		Assert.assertNotNull(soGetEligibleProvResponse.getResults().getError());
		Assert.assertNotNull(soGetEligibleProvResponse.getResults().getError().get(0));
		Assert.assertNotNull(soGetEligibleProvResponse.getResults().getError().get(0).getCode());
		Assert.assertEquals("3001",soGetEligibleProvResponse.getResults().getError().get(0).getCode());	
	}
	
}
