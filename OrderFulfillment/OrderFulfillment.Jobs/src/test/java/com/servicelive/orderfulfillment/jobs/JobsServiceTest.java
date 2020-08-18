package com.servicelive.orderfulfillment.jobs;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.orderfulfillment.vo.CacheRefreshResponse;


public class JobsServiceTest {
	
	private JobsService service;
	
	@Test
	public void testRefreshCache(){
		service = new JobsService();
		String cacheName = "cache";
		
		CacheRefreshResponse response = new CacheRefreshResponse();
		
		response = service.refreshCache(cacheName);
		
		Assert.assertNotNull(response);
	}
}
