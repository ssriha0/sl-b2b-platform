package com.newco.marketplace.utils;

import java.sql.Timestamp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;

/**
 * $Revision: 1.15 $ $Author: akashya $ $Date: 2008/05/21 23:48:28 $
 */
/**
 * $Revision: 1.16 $ $Author: Seshu $ $Date: 2009/10/06 16:38:28 $
 */

public class DateUtils {

	private static final Logger logger = Logger.getLogger(DateUtils.class.getName());
	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static SimpleDateFormat headerDate = new SimpleDateFormat("EEEE, MMMM d, yyyy hh:mm a (z)");
	private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");	
	private static long ONE_HOUR = 60 * 60 * 1000L;
	
	public static final String DISPLAY_DATE_FORMAT = "MM/dd/yyyy hh:mm a";
	public static final String MMDDYYYY_format = "MM/dd/yyyy";
	public static final String YYYYDDD_format = "yyyyDDD";
	public static final String HHMMSS_format = "HHmmss";
	public static final String DISPLAY_DATE_FORMAT_WITH_TIMEZONE = "MM/dd/yyyy hh:mm a (z)";
	public static final String DATE_TIME_FORMAT_WITH_TIMEZONE = "MM/dd/yyyy 'at' hh:mm a z";
	public static final String DATE_TIME_FROM_XML="yyyy-MM-dd'T'HH:mm:ss";
	public static long getDaysBetweenDates(Date startDate, Date endDate) {
		return ((endDate.getTime() - startDate.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}
	
	public static String getHeaderDate(String pDate) {
		return headerDate.format(pDate);
	}

	public static String getHeaderDate() {
		Calendar currentDateTime = new GregorianCalendar(TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE));
		return headerDate.format(currentDateTime.getTime());
	}
	

	public static Date defaultFormatStringToDate(String source)
	{
		try {
			return defaultDateFormat.parse(source);
		} catch (ParseException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return null;
	}

	
	public static String FormatDateStringToMMDDYYYYString(String source)
	{

			SimpleDateFormat sdf = new SimpleDateFormat(MMDDYYYY_format);
			String formattedDate = "" ;
			Date dt = defaultFormatStringToDate(source);
			formattedDate = sdf.format(dt);

			return formattedDate;
	}

	/**
	 * Returns a Date object after parsing the specified string with the required format
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date getDateFromString(String dateStr, String formatStr) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("Caught Exception and ignoring: " + dateStr + " - ", e);
		}
		return date;
	}
	public static String getDateAndTimeFromString(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String formattedDate = "" ;
	
			Date dt = defaultFormatStringToDate(dateStr);
			formattedDate = sdf.format(dt);
		
		return formattedDate;
	}
	
	public static String getTimezone(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("z");
		String timezone = null;
		if (date != null) {
			try {
				timezone = sdf.format(date);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return timezone;
	}
	
	public static String dateToDefaultFormatString(Date date) throws ParseException
	{
		return defaultDateFormat.format(date);
	}
	
	public static String getFormatedDate(Date inputDate, String reqFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(reqFormat);
		return sdf.format(inputDate);
	}	
	
	public static boolean isValidDate(String strDate) {
		return isValidDate(strDate, DEFAULT_DATE_FORMAT);
	}
	
	public static boolean isValidDate(String strDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
         
            sdf.setLenient(false);
            sdf.parse(strDate);
            return true;
        } 
        catch (Exception ex) {
            return false;
        }
    }//isValidDate
	
	public static Date getCurrentDate(){
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return date;

	}

    public static Timestamp getCurrentTimeStamp(){
   	 return new Timestamp(new Date(System.currentTimeMillis()).getTime());
   	 
    }
    
    public static Date calcDateBasedOnDateNInterval(Date calcDate, Integer i, String interval){
		/****This method will give you the new date based on the following:
		/****Ex: I want to know what the date was 15 days ago for the date I Passed (pass in i=15 and interval=DAY)
		/****i = Number of days, weeks, months, years
		/****interval needs to be one of the following: DAY, WEEK, MONTH or YEAR ****/
		
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(calcDate);
		
		if (i > 0 && !interval.equals(null)){
			if (interval.equals("WEEK")){
				//Week will be using the day interval
				i = i*7;
			}
			
			if (interval.equals("MONTH")){
				calendarDate.add(calendarDate.MONTH, -i);
			}
			
			if (interval.equals("DAY") || interval.equals("WEEK")){
				calendarDate.add(calendarDate.DATE, -i);
			}
			
			if (interval.equals("YEAR")){
				calendarDate.add(calendarDate.YEAR, -i);
			}
			
			calcDate = calendarDate.getTime();
		}
		
		return calcDate;
	}
    
    public static Date calcDateBasedOnInterval(Integer i, String interval){
		/****This method will give you the date based on the following:
		/****Ex: I want to know what the date was 15 days ago (pass in i=15 and interval=DAY)
		/****i = Number of days, weeks, months, years
		/****interval needs to be one of the following: DAY, WEEK, MONTH or YEAR ****/
		
		Calendar calendarDate = Calendar.getInstance();
		Date calcDate = new Date();
		
		
		if (i > 0 && !interval.equals(null)){
			if (interval.equals("WEEK")){
				//Week will be using the day interval
				i = i*7;
			}
			
			if (interval.equals("MONTH")){
				calendarDate.add(calendarDate.MONTH, -i);
			}
			
			if (interval.equals("DAY") || interval.equals("WEEK")){
				calendarDate.add(calendarDate.DATE, -i);
			}
			
			if (interval.equals("YEAR")){
				calendarDate.add(calendarDate.YEAR, -i);
			}
			
			calcDate = calendarDate.getTime();
		}
		
		return calcDate;
	}
	
	public static int workingDays(Date startDate, Date endDate){
		int workingDays = 0;
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		while (!startCal.after(endCal)) {
			
		   int day = startCal.get(Calendar.DAY_OF_WEEK);
		   
		   if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY))
		workingDays++;

		   startCal.add(Calendar.DATE, 1);
		}
		return workingDays - 1;
	}
	public static Date addDaysToDate(Date date, Integer daysToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, daysToAdd);
		date = cal.getTime();
		return date;
	}
	
	public static String getJulianDateFromTimeStamp(Timestamp ts)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYDDD_format);
		String formattedDate = "" ;
		
		formattedDate = sdf.format(ts);

		return formattedDate;
	}

	public static String getTimeFromTimeStamp(Timestamp ts)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(HHMMSS_format);
		String formattedDate = "" ;
		
		formattedDate = sdf.format(ts);

		return formattedDate;
	}
	
	/**
	 * @return The difference between startDate and end date in days.
	 * */
	public static long getExactDaysBetweenDates(Date startDate, Date endDate) {
		return ((endDate.getTime() - startDate.getTime()) / (ONE_HOUR * 24));
	}	
	
	public static void main(String s[]){}
	 
	/**@Description: Convert String Date  to Date Object
	 * @param DateString
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseDate(String DateString){
		Date DateTime =null;
		SimpleDateFormat input = new SimpleDateFormat(DATE_TIME_FROM_XML);
		try {
			DateTime = input.parse(DateString);
			
		} catch (ParseException e) {
			logger.error("Exception in parsing estimation Date"+e);
		}
		return DateTime;
		
	}
	/**@Description: Convert Date in yyyy-MM-dd'T'HH:mm:ss to yyyy-MM-dd HH:mm:ss format
	 * @param DateString
	 * @return
	 * @throws ParseException 
	 */
	public static String formatDate(Date date){
		String DateTime =null;
		SimpleDateFormat outPut = new SimpleDateFormat(DATE_TIME_FROM_XML);
		try {
			DateTime = outPut.format(date);	
		} catch (Exception e) {
			logger.error("Exception in formatting estimation Date"+e);
		}
		return DateTime;
		
	}
	
	/**
	 * @param month
	 * @return
	 */
	public static Date addMonth(int month) {
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	public static boolean isFromDateLesserThanCurrentDate(String fromDate,
			String dateFormat) {
		boolean result = false;
		Date frmDt = getDateFromString(fromDate, dateFormat);
		Date curDt = new Date();
		try {
			if (frmDt.before(curDt) && frmDt.compareTo(curDt) < 0) {
				result = true;
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return result;
	}
	
	public static Date addMonth(Date date,int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	public static Date subMonth(Date date, int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -month);
		return cal.getTime();
	}
}
