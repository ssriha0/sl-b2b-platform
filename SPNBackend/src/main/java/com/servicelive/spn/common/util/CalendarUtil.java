package com.servicelive.spn.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author svanloon
 *
 */
public class CalendarUtil {

	/**
	 * This method returns the server's date and midnight.  So if 
	 * the server's time is 1/16/2008 1:30 pm CST, then the time returned 
	 * is 1/16/2008 12:00:00 am/  The time is reverted to the beginning of 
	 * the timezones date.
	 * @return Date
	 */
	public static Date getTodayAtMidnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 
	 * @return the current time 
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * @param d 
	 * @return the formatted date for display purposes
	 * 
	 */
	public static String formatDateForDisplay(Date d) {
		if(d == null) {
			return "";
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
		return dateFormatter.format(d);
	}

	/**
	 * @param d 
	 * @return the formatted date for display purposes
	 * 
	 */
	public static String formatDateAndTimeForDisplay(Date d) {
		if(d == null) {
			return "";
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a zz");
		return dateFormatter.format(d);
	}
	
	
	/**
	 * 
	 * @param d
	 * @param format 
	 * @return String
	 */
	public static String formatDate(Date d, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(d);
	}

	/**
	 * 
	 * @param d
	 * @param minutes
	 */
	public static void addMinutes(Date d, int minutes) {
		long ms = d.getTime();
		ms += minutes*60L*1000L;
		d.setTime(ms);
	}
	
	/**
	 * 
	 * @param date
	 * @param days
	 * @return Date
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * 
	 * @param dateString
	 * @param dateFormat
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString, String dateFormat) throws ParseException {
		if(dateString == null || dateString.trim().equals("")) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = sdf.parse(dateString);
		return date;
		
	}
}
