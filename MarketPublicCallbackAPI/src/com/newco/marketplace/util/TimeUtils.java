package com.newco.marketplace.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.RescheduleOrderConstants;

public class TimeUtils {

	private static final Logger logger = Logger.getLogger(TimeUtils.class.getName());

	public static Timestamp stringToTimestamp(String timeformatString) throws ParseException {
		return Timestamp.valueOf(timeformatString);
	}

	public static String timestampToString(Timestamp timestampValue) {
		return timestampValue.toString();
	}

	public static SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static SimpleDateFormat sdf2ToDate = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat sdf3ToDate = new SimpleDateFormat("MMddyyyy");
	public static SimpleDateFormat sd4fToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdfToDateWithZone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	static {
		sdfToDateWithZone.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * Method converts the input date from a time zone to another time zone
	 * 
	 * @param tm
	 * @param fromTimeZoneString
	 * @param toTimeZoneString
	 * @return
	 */
	public static String convertGMTToGivenTimeZone(Timestamp inputTimestamp, String toTimeZoneStr) {

		TimeZone GMTZone = TimeZone.getTimeZone("GMT");
		SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String TOSformat = sdfToDate.format(inputTimestamp);
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		TimeZone toTimeZone;

		try {
			year = Integer.parseInt(TOSformat.substring(0, 4));
			month = Integer.parseInt(TOSformat.substring(5, 7));
			dayOfMonth = Integer.parseInt(TOSformat.substring(8, 10));

			hours = Integer.parseInt(TOSformat.substring(11, 13));
			min = Integer.parseInt(TOSformat.substring(14, 16));
			sec = Integer.parseInt(TOSformat.substring(17, 19));

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		toTimeZone = TimeZone.getTimeZone(toTimeZoneStr);
		Calendar cal = new GregorianCalendar(year, month - 1, dayOfMonth, hours, min, sec);

		cal.setTimeZone(GMTZone);

		if (hours <= 11) {
			cal.set(Calendar.AM_PM, Calendar.AM);
		} else {
			cal.set(Calendar.AM_PM, Calendar.PM);
		}

		sdfToDate.setTimeZone(toTimeZone);
		Date date = cal.getTime();
		String dateInReqdZone = sdfToDate.format(date);
		return dateInReqdZone;
	}

	public static String formatDateString(String inputDateStr, String requiredFormat) {

		String outputDate = "";
		if (requiredFormat.equals(RescheduleOrderConstants.DATE_FORMAT)) {
			outputDate = inputDateStr.substring(0, 10);
		} else if (requiredFormat.equals(RescheduleOrderConstants.TIME_FORMAT)) {
			outputDate = inputDateStr.substring(11, 16);
		}
		return outputDate;
	}
}