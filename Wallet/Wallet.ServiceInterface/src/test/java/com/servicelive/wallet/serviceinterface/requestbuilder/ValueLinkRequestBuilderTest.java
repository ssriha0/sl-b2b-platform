package com.servicelive.wallet.serviceinterface.requestbuilder;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.wallet.serviceinterface.vo.ValueLinkVO;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkRequestBuilder.
 */
public class ValueLinkRequestBuilderTest {
	private ValueLinkRequestBuilder bldr;
	
	@Test
	public void testActivateBuyer(){
		bldr = new ValueLinkRequestBuilder();
		
		Long buyerId = 1000L;
		long fundingTypeId = 2; 
		String buyerState = "IL";
		
		ValueLinkVO vo = new ValueLinkVO();
		ValueLinkVO voExpected = new ValueLinkVO();
		voExpected.setBuyerState("IL");
		vo = bldr.activateBuyer(buyerId, fundingTypeId, buyerState);
		Assert.assertEquals(voExpected.getBuyerState(), vo.getBuyerState());
	}


}
