package com.servicelive.marketplatform.notification.service;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.common.rest.RemoteServiceStatus;

public class MarketPlatformRestServiceTest {

	private MarketPlatformRestService service;
	
	@Test
	public void testIsServiceActive(){
		service = new MarketPlatformRestService();
		
		RemoteServiceStatus status = new RemoteServiceStatus();
		
		status = service.isServiceActive();
		
		Assert.assertEquals(true, status.isActive());
	}
	
}
