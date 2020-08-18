package com.newco.ofac.webservice;

import org.junit.Assert;
import org.junit.Test;

public class OFACSDNClientTest {
	
	private OFACSDNClient client;
	
	@Test
	public void  testIsSDNAndBlockedPersons(){
		client = new OFACSDNClient();
		String resourceName = "melrose1";
		boolean result =false;
		
		try {
			result = client.isSDNAndBlockedPersons(resourceName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertFalse(result);
	}
}
