package com.newco.servicelive.campaigns.service;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class BaseServiceTest {
 private BaseService service;
 private Properties alertServiceProperties;
 
 @Test
 public void testGetDecoratedCampaignName(){
	 service = new BaseService();
	 alertServiceProperties = mock(Properties.class);
	 service.setAlertServiceProperties(alertServiceProperties);
	 String name = "name";
	 String returnName = "";
	 when(alertServiceProperties.getProperty("testMode")).thenReturn("true");
	 returnName = service.getDecoratedCampaignName(name);
	 Assert.assertEquals("name_test", returnName);
 }
	
}
