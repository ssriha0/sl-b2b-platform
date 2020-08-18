/*
 * @(#)TimeUtilsTest.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;

/**
 * @author Mahmud Khair
 *
 */
public class TimeUtilsTest extends TestCase {

	/**
	 * Test method for {@link com.newco.marketplace.utils.TimeUtils#isPastXTime(java.sql.Timestamp, java.sql.Timestamp, long)}.
	 * @throws ParseException 
	 */
	public void testIsPastXTimeTimestampTimestampLong() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Timestamp time1 = new Timestamp(sdf.parse("06/10/2008 09:30:34").getTime());
		Timestamp time2 = new Timestamp(sdf.parse("06/10/2008 10:00:35").getTime());
		long pastTime = 30*60*1000;
		
		assertEquals(true, TimeUtils.isPastXTime(time1 , time2, pastTime));
		
		time1 = new Timestamp(sdf.parse("06/10/2008 09:30:34").getTime());
		time2 = new Timestamp(sdf.parse("06/10/2008 10:00:34").getTime());
		pastTime = 30*60*1000;
		
		assertEquals(false, TimeUtils.isPastXTime(time1 , time2, pastTime));
		
		time1 = new Timestamp(sdf.parse("06/10/2008 09:30:00").getTime());
		time2 = new Timestamp(sdf.parse("06/10/2008 12:30:04").getTime());
		pastTime = 120*60*1000;
		
		assertEquals(true, TimeUtils.isPastXTime(time1 , time2, pastTime));
	}

}
