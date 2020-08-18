package com.newco.marketplace.webservices.util;

import java.math.BigDecimal;

import org.junit.Test;

import junit.framework.Assert;

public final class MoneyUtilTest {

	private MoneyUtil util;
	
	@Test
	public void  testGetProportion(){
		util = new MoneyUtil();
		
		Double num1 = 35.00;
		Double num2 = 25.00;
		
		Double proportion = 0.00;
		
		proportion = util.getProportion(num1, num2);
		
		Assert.assertEquals(1.4, proportion);
	}

}
