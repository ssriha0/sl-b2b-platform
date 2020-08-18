package com.servicelive.orderfulfillment.client.utils;

import org.junit.Assert;
import org.junit.Test;


public class ConfigurationTest {
	
	private Configuration config;
	
	@Test
	public void testIsOrderFulfillmentEnabled(){
		config =  new Configuration();
		boolean isOFEnabled;
		isOFEnabled = config.isOrderFulfillmentEnabled();
		
		Assert.assertFalse(isOFEnabled);
}
}
