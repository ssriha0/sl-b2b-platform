package com.servicelive.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * $Revision: 1.15 $ $Author: akashya $ $Date: 2008/05/21 23:48:28 $
 */
public class DateUtils {

	/** DATE_TIME_FORMAT_WITH_TIMEZONE. */
	public static final String DATE_TIME_FORMAT_WITH_TIMEZONE = "MM/dd/yyyy 'at' hh:mm a z";

	/** DEFAULT_DATE_FORMAT. */
	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/** defaultDateFormat. */
	private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/** DISPLAY_DATE_FORMAT. */
	public static final String DISPLAY_DATE_FORMAT = "MM/dd/yyyy hh:mm a";

	/** DISPLAY_DATE_FORMAT_WITH_TIMEZONE. */
	public static final String DISPLAY_DATE_FORMAT_WITH_TIMEZONE = "MM/dd/yyyy hh:mm a (z)";

	/** headerDate. */
	private static SimpleDateFormat headerDate = new SimpleDateFormat("EEEE, MMMM d, yyyy hh:mm a (z)");

	/** HHMMSS_format. */
	public static final String HHMMSS_format = "HHmmss";

	/** logger. */
	private static final Logger logger = Logger.getLogger(DateUtils.class.getName());

	/** MMDDYYYY_format. */
	public static final String MMDDYYYY_format = "MM/dd/yyyy";

	/** ONE_HOUR. */
	private static long ONE_HOUR = 60 * 60 * 1000L;

	/** YYYYDDD_format. */
	public static final String YYYYDDD_format = "yyyyDDD";

	/**
	 * addDaysToDate.
	 * 
	 * @param date 
	 * @param daysToAdd 
	 * 
	 * @return Date
	 */
	public static Date addDaysToDate(Date date, Integer daysToAdd) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, daysToAdd);
		return cal.getTime();
	}

	/**
	 * dateToDefaultFormatString.
	 * 
	 * @param date 
	 * 
	 * @return String
	 * 
	 * @throws ParseException 
	 */
	public static String dateToDefaultFormatString(Date date) throws ParseException {

		return defaultDateFormat.format(date);
	}

	/**
	 * defaultFormatStringToDate.
	 * 
	 * @param source 
	 * 
	 * @return Date
	 */
	public static Date defaultFormatStringToDate(String source) {

		try {
			return defaultDateFormat.parse(source);
		} catch (ParseException e) {
			logger.info("Caught Exception and ignoring", e);
		}
		return null;
	}

	/**
	 * formatDateStringToMMDDYYYYString.
	 * 
	 * @param source 
	 * 
	 * @return String
	 */
	public static String formatDateStringToMMDDYYYYString(String source) {

		SimpleDateFormat sdf = new SimpleDateFormat(MMDDYYYY_format);
		String formattedDate = "";
		Date dt = defaultFormatStringToDate(source);
		formattedDate = sdf.format(dt);

		return formattedDate;
	}

	/**
	 * getCurrentTimeStamp.
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getCurrentTimeStamp() {

		return new Timestamp(new Date(System.currentTimeMillis()).getTime());

	}

	/**
	 * getDateAndTimeFromString.
	 * 
	 * @param dateStr 
	 * @param formatStr 
	 * 
	 * @return String
	 */
	public static String getDateAndTimeFromString(String dateStr, String formatStr) {

		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String formattedDate = "";

		Date dt = defaultFormatStringToDate(dateStr);
		formattedDate = sdf.format(dt);

		return formattedDate;
	}

	/**
	 * Returns a Date object after parsing the specified string with the required format.
	 * 
	 * @param dateStr 
	 * @param formatStr 
	 * 
	 * @return 
	 */
	public static Date getDateFromString(String dateStr, String formatStr) {

		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	/**
	 * getDaysBetweenDates.
	 * 
	 * @param startDate 
	 * @param endDate 
	 * 
	 * @return long
	 */
	public static long getDaysBetweenDates(Date startDate, Date endDate) {

		return ((endDate.getTime() - startDate.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}

	/**
	 * getFormatedDate.
	 * 
	 * @param inputDate 
	 * @param reqFormat 
	 * 
	 * @return String
	 */
	public static String getFormatedDate(Date inputDate, String reqFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(reqFormat);
		return sdf.format(inputDate);
	}

	/**
	 * getHeaderDate.
	 * 
	 * @param pDate 
	 * 
	 * @return String
	 */
	public static String getHeaderDate(String pDate) {

		return headerDate.format(pDate);
	}

	/**
	 * getJulianDateFromTimeStamp.
	 * 
	 * @param ts 
	 * 
	 * @return String
	 */
	public static String getJulianDateFromTimeStamp(Timestamp ts) {

		SimpleDateFormat sdf = new SimpleDateFormat(YYYYDDD_format);
		String formattedDate = "";

		formattedDate = sdf.format(ts);

		return formattedDate;
	}

	/**
	 * getTimeFromTimeStamp.
	 * 
	 * @param ts 
	 * 
	 * @return String
	 */
	public static String getTimeFromTimeStamp(Timestamp ts) {

		SimpleDateFormat sdf = new SimpleDateFormat(HHMMSS_format);
		String formattedDate = "";

		formattedDate = sdf.format(ts);

		return formattedDate;
	}

	/**
	 * getTimezone.
	 * 
	 * @param date 
	 * 
	 * @return String
	 */
	public static String getTimezone(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat("z");
		String timezone = null;
		if (date != null) {
			try {
				timezone = sdf.format(date);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return timezone;
	}

	/**
	 * isValidDate.
	 * 
	 * @param strDate 
	 * 
	 * @return boolean
	 */
	public static boolean isValidDate(String strDate) {

		return isValidDate(strDate, DEFAULT_DATE_FORMAT);
	}

	/**
	 * isValidDate.
	 * 
	 * @param strDate 
	 * @param format 
	 * 
	 * @return boolean
	 */
	public static boolean isValidDate(String strDate, String format) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			sdf.setLenient(false);
			sdf.parse(strDate);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}// isValidDate

	/**
	 * main.
	 * 
	 * @param s 
	 * 
	 * @return void
	 */
	public static void main(String s[]) {

	}

	/**
	 * workingDays.
	 * 
	 * @param startDate 
	 * @param endDate 
	 * 
	 * @return int
	 */
	public static int workingDays(Date startDate, Date endDate) {

		int workingDays = 0;
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		while (!startCal.after(endCal)) {

			int day = startCal.get(Calendar.DAY_OF_WEEK);

			if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
				workingDays++;
			}

			startCal.add(Calendar.DATE, 1);
		}
		return workingDays - 1;
	}

}
