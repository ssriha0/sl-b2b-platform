/**
 * 
 */
package com.servicelive.common.util;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.servicelive.common.util.SmsAlert;

/**
 * @author hoza
 *
 */
public class SmsAlertTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("sms.provider.host", "api.imws.us");
		System.setProperty("sms.provider.username", "s3rv1c3l1v3");
		//2vOd8mbhL9ipbwEteXx7Sg==
		System.setProperty("sms.provider.credential", "s3rv1c3l1v33333");
		System.setProperty("sms.provider.method", "/messages/send");
		System.setProperty("sms.provider.campaignid", "DONTMATTER"); // This property DOESNT matter as the provider is going to pick up the campaign id based on the USERNAME
		System.setProperty("sms.provider.ssl.port", "443");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.servicelive.common.util.SmsAlert#sendMessage(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSendMessage() {
		try {System.out.println("testSendMessage method executed");
			//Integer returnCode = SmsAlert.sendMessage("2242108224", "", "", "Test Single Number SMS Sent at "+(new Date()).toString());
			//Assert.assertNotNull("I should Not Get NULL return code", returnCode);
			//Assert.assertTrue("Methods should return 1 " , returnCode.intValue() == 1);
		}catch(Exception e) {
			Assert.fail("Got Exception.. se log ");
		}
	}
	
	@Test
	public final void testSendMessageMutliple() {
		String multiplePhone = "2242108224;8479773884";
		try {System.out.println("testSendMessageMutliple method executed");
			//Integer returnCode = SmsAlert.sendMessage(multiplePhone, "", "", "Test Mutliple  Number SMS Sent at"+(new Date()).toString());
			//Assert.assertNotNull("I should Not Get NULL return code", returnCode);
			//Assert.assertTrue("Methods should return 1 " , returnCode.intValue() == 2);
		}catch(Exception e) {
			Assert.fail("Got Exception.. se log ");
		}
	}

}
