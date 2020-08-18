package com.newco.marketplace.translator.util;


import java.util.Date;

import org.junit.Test;

import com.newco.marketplace.translator.util.DateUtil;

import junit.framework.Assert;

public class DateUtilTest {
	
	private DateUtil util;

	@SuppressWarnings("static-access")
	@Test
	public void testIsSameDay() {
		util = new DateUtil();
		Date date1 = new Date();
		Date date2 = new Date(date1.getTime()+(24*60*60*1000));
		boolean sameDay = false;
		sameDay = util.isSameDay(date1, date2);
		Assert.assertFalse(sameDay);
	}


}
