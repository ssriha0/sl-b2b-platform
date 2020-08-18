package com.servicelive.bus.event.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.servicelive.bus.MessageSelector;
import com.servicelive.bus.event.EventHeader;

/**
 * User: Mustafa Motiwala
 * Date: Mar 31, 2010
 * Time: 11:41:16 PM
 */
public class ServiceOrderMessageSelectorTest  {
	private ServiceOrderMessageSelector selector;
	
	@Test
	public void testBuildSelector(){
		selector = new ServiceOrderMessageSelector();
		List<String> buyers = new ArrayList<String>();
		buyers.add("1000");
		buyers.add("3000");
		selector.setBuyers(buyers);
		String result = "";
		
		result = selector.buildSelector();
		
		Assert.assertEquals("EVENT_TYPE= 'com.servicelive.bus.event.order.ServiceOrderEvent' AND BUYER_COMPANY_ID IN ('1000','3000') ", result);
		
	}
	

}
