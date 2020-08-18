/*
 * @(#)TimeStampUtilsTest.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

/**
 * @author Mahmud Khair
 *
 */
public class TimeStampUtilsTest extends TestCase {

	/**
	 * Test method for {@link com.newco.marketplace.utils.TimestampUtils#getDayDifference(java.sql.Timestamp, java.sql.Timestamp)}.
	 * @throws ParseException 
	 */
	public void testGetDayDifference() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date startDate = sdf.parse("06/10/2008 09:30:34");
		Date endDate = sdf.parse("06/10/2008 11:23:32");
		
		int dayDiff = TimestampUtils.getDayDifference(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
		assertEquals(0, dayDiff);
		
		startDate = sdf.parse("06/10/2008 09:30:34");
		endDate = sdf.parse("06/15/2008 11:23:32");
		
		dayDiff = TimestampUtils.getDayDifference(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
		assertEquals(5, dayDiff);
		
		startDate = sdf.parse("06/10/2008 09:30:34");
		endDate = sdf.parse("06/09/2008 11:23:32");
		
		dayDiff = TimestampUtils.getDayDifference(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
		assertEquals(-1, dayDiff);
		
		startDate = sdf.parse("06/10/2008 09:30:34");
		endDate = sdf.parse("06/10/2008 08:23:32");
		
		dayDiff = TimestampUtils.getDayDifference(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
		assertEquals(-1, dayDiff);
		
		startDate = sdf.parse("06/10/2008 09:30:34");
		endDate = sdf.parse("06/11/2008 08:23:32");
		
		dayDiff = TimestampUtils.getDayDifference(new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
		assertEquals(1, dayDiff);
	}

}
