package com.servicelive.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * $Revision: 1.15 $ $Author: akashya $ $Date: 2008/05/21 23:48:28 $
 */
public class DateUtilsTest {
	
	private DateUtils utils;

	@Test
	public void testWorkingDays(){
		utils = new DateUtils();
		Date startDate = new Date();
		Date endDate = new Date();
		 Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.MONTH, 8);
	        cal.set(Calendar.DATE, 6);
	        cal.set(Calendar.YEAR, 2014);
	        endDate = cal.getTime();
		
		int workingDays = utils.workingDays(startDate, endDate);
		Assert.assertNotSame(4, workingDays);
	}
	

}
