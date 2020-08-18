package com.servicelive.wallet.batch.ach;

import org.junit.Assert;
import org.junit.Test;

public class NachaUtilTest {
	
	private NachaUtil util;
	
	@Test
	public void testGetConsolidatedLedgerId(){
		util = new NachaUtil();
		long ledgerId = 0L;
		
		ledgerId = util.getConsolidatedLedgerId();
		Assert.assertNotNull(ledgerId);
	}

}
