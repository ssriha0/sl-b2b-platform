package com.servicelive.orderfulfillment.vo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.marketplace.interfaces.OrderConstants;





/**
 * ksharm6
 *
 * $Revision: 1.49 $ $Author: akashya $ $Date: 2008/05/21 23:48:28 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class TimeUtils {

	private static final Logger logger = Logger.getLogger(TimeUtils.class.getName());

	public Timestamp stringToTimestamp(String timeformatString)
			throws ParseException {

		return Timestamp.valueOf(timeformatString);
	}

	public String timestampToString(Timestamp timestampValue) {
		return timestampValue.toString();
	}

	/*-----------------------------------------------------------------
	 * This is method will be used to convert string time to milliseconds
	 * @param 		strTime - time string
	 * @returns 	milliseconds in long
	 *-----------------------------------------------------------------*/
	public static long convertStringTimeToMilliSeconds(String strTime) {
		int intHr = 0;
		int intMin = 0;
		long timeMiliSeconds = 0;
		String strAmPm = "";

		if (strTime != null && strTime.length() == 8
				&& (strTime.endsWith("AM") || strTime.endsWith("PM"))) {
			intHr = Integer
					.parseInt(strTime.substring(0, strTime.indexOf(":")));

			intMin = Integer.parseInt(strTime.substring(
					strTime.indexOf(":") + 1, strTime.indexOf(" ")));

			strAmPm = strTime.substring(strTime.indexOf(" ") + 1, strTime
					.length());

			if (strAmPm.equalsIgnoreCase("PM")) {
				intHr = intHr + 12;
			}// end of if (strAmPm.equalsIgnoreCase("PM")){
			timeMiliSeconds = (intHr * 60 * 60 + intMin * 60) * 1000;

		}
		return timeMiliSeconds;
	}

	public static boolean isPastCurrentTime(Timestamp appointmentDate, String timeStr) {
		
		Calendar appointmentCal = getDateTime(appointmentDate, timeStr, OrderConstants.DEFAULT_ZONE);
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE));
		
		long currentTimeInMillis = cal.getTimeInMillis();
		
		if (currentTimeInMillis > appointmentCal.getTimeInMillis())
			return true;
		else
			return false;

	}

	public static boolean isPastXTime(Timestamp appointmentDate, String timeStr, long pastTime, String appointmentTimeZone) {

		Calendar calCurrent = new GregorianCalendar(TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE));

		Calendar calAppointment = getDateTime(appointmentDate, timeStr, appointmentTimeZone, Locale.UK);
		
		logger.debug("isPastXTime-->calCurrent="+calCurrent);
		logger.debug("isPastXTime-->calAppointment="+calAppointment);
		
		Long timeToAppointment = calAppointment.getTimeInMillis() - calCurrent.getTimeInMillis();
		
		if (timeToAppointment < 0){
			logger.debug("isPastXTime-->true, SO date is in past");
			return true;
		}
		
		if (timeToAppointment < pastTime){
			logger.debug("isPastXTime-->true, SO date is less than 24 hours away");
			return true;
		}
		else{
			logger.debug("isPastXTime-->false");
			return false;
		}
		
		
	}


	public static boolean isPastXTime(Timestamp appointmentDate, String timeStr, long pastTime) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Calendar cal = Calendar.getInstance();
			 
		Calendar calCurrent= getDateTime(new Timestamp(new Date().getTime()), formatter.format(cal.getTime()).substring(11), OrderConstants.GMT_ZONE, Locale.UK);

		Calendar calAppointment = getDateTime(appointmentDate, timeStr, OrderConstants.GMT_ZONE, Locale.UK);
		
		logger.debug("isPastXTime-->calCurrent="+calCurrent);
		logger.debug("isPastXTime-->calAppointment="+calAppointment);
		
		Long timeToAppointment = calAppointment.getTimeInMillis() - calCurrent.getTimeInMillis();
		
		if (timeToAppointment < 0){
			logger.debug("isPastXTime-->true, SO date is in past");
			return true;
		}
		
		if (timeToAppointment < pastTime){
			logger.debug("isPastXTime-->true, SO date is less than 24 hours away");
			return true;
		}
		else{
			logger.debug("isPastXTime-->false");
			return false;
		}
		
		
	}
	
	/**
	 * This method checks if second time is passed first time for specified time
	 * @param time1
	 * @param time2
	 * @param pastTime in milliseconds
	 * @return true, it second time is passed first time for specified time. Otherwise false
	 */
	public static boolean isPastXTime(Timestamp time1,Timestamp time2, long pastTime) {

		if ((time2.getTime() - time1.getTime()) > pastTime){
			logger.debug("isPastXTime-->true");
			return true;
		}
		else{
			logger.debug("isPastXTime-->false");
			return false;
		}
	}
	
	public static boolean isPastCurrentTime(Timestamp appointmentDate, String timeStr, Locale locale, String timeZone) {

		
		Calendar appointmentCal = getDateTime(appointmentDate, timeStr, timeZone, locale);
		
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(OrderConstants.DEFAULT_ZONE));
		long currentTimeInMillis = cal.getTimeInMillis();
		
		if (currentTimeInMillis > appointmentCal.getTimeInMillis())
			return true;
		else
			return false;

	}	
	
	public static boolean isPastCurrentTime(Timestamp appointmentDate, 
			String timeStr,String timeZone) {		
		Calendar appointmentCal = getDateTime(appointmentDate, timeStr, timeZone);
		
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
		long currentTimeInMillis = cal.getTimeInMillis();
		
		if (currentTimeInMillis > appointmentCal.getTimeInMillis())
			return true;
		else
			return false;
	}
public static boolean isPastCurrentTimeInServiceLocTimezone(Timestamp appointmentDate, String timeStr, Locale locale, String timeZone) {

		
		Calendar appointmentCal = getDateTime(appointmentDate, timeStr, timeZone, locale);					
		Calendar calendar = Calendar.getInstance();		
		Timestamp now = new Timestamp(calendar.getTimeInMillis());		
		Timestamp currentTimeInGMT=convertToGMT(now, calendar.getTimeZone().toString());		
		Date dt1=(Date)currentTimeInGMT;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		String formatedDate = sdf.format(dt1);
		String st=getTimePart(formatedDate);
		HashMap<String, Object> dateTimeMap = TimeUtils
		.convertGMTToGivenTimeZone(currentTimeInGMT,st,timeZone);
		Timestamp dateInTZ=
				(Timestamp) dateTimeMap.get(OrderConstants.GMT_DATE);
		String timeInTZ=(String) dateTimeMap.get(OrderConstants.GMT_TIME);
		Date date1=combineDateAndTime(dateInTZ, timeInTZ, timeZone);
		Calendar cal=new GregorianCalendar(TimeZone.getTimeZone(timeZone));
	       cal.setTime(date1);

		/*Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(timeZone));
		Date date1 = cal.getTime(); 
		logger.info("====================Current DAte"+date1);*/
		long currentTimeInMillis = cal.getTimeInMillis();		
		if (currentTimeInMillis > appointmentCal.getTimeInMillis())
			return true;
		else
			return false;

	}	
	public static Calendar getDateTime(Timestamp dateStr, String timeStr, String timeZone) {
		if(dateStr == null || timeStr == null){
			return null;
		}
		Calendar cal = null;
		
		try{
			DateFormat sdf_yyyyddMM = new SimpleDateFormat("yyyy-dd-MM");
			String dt = sdf_yyyyddMM.format(dateStr);
			int year = 0;
			int month = 0;
			int dayOfMonth = 0;
			int hours = 0;
			int min = 0;
			int sec = 0;
			String timeType = "";
			year = Integer.parseInt(dt.substring(0, 4));
			dayOfMonth = Integer.parseInt(dt.substring(5, 7));
			month = Integer.parseInt(dt.substring(8, 10));
			if(timeStr != null && !("").equals(timeStr)){
				hours = Integer.parseInt(timeStr.substring(0, 2));
				min = Integer.parseInt(timeStr.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = timeStr.substring(6, 8);
			}
					
			cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);
			
			if (timeZone != null) {
				cal.setTimeZone(TimeZone.getTimeZone(timeZone));
			}
			
			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}
			else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return cal;
	}
	
	public static Calendar getDateTime(Timestamp dateStr, String timeStr, String timeZone, Locale locale) {
		if(dateStr == null || timeStr == null){
			return null;
		}
		Calendar cal = null;
		
		try{
			DateFormat sdf_yyyyddMM = new SimpleDateFormat("yyyy-dd-MM");
			String dt = sdf_yyyyddMM.format(dateStr);
			int year = 0;
			int month = 0;
			int dayOfMonth = 0;
			int hours = 0;
			int min = 0;
			int sec = 0;
			String timeType = "";
			year = Integer.parseInt(dt.substring(0, 4));
			dayOfMonth = Integer.parseInt(dt.substring(5, 7));
			month = Integer.parseInt(dt.substring(8, 10));
			if(timeStr != null && !("").equals(timeStr)){
				hours = Integer.parseInt(timeStr.substring(0, 2));
				min = Integer.parseInt(timeStr.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = timeStr.substring(6, 8);
			}
			cal = new GregorianCalendar(TimeZone.getTimeZone(timeZone), locale);	
			cal.set(year, month-1, dayOfMonth,hours, min, sec);

			boolean toAdd = false;			

			if(timeType.equalsIgnoreCase("PM") && hours != 12){
				toAdd = true;
			}
			if(toAdd){
				cal.add(Calendar.HOUR_OF_DAY,12);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return cal;
	}	
	
	public static HashMap<String, Object> convertGMTToGivenTimeZone(Timestamp ts, String tm, String zone){
		if(zone == null){
			zone = OrderConstants.EST_ZONE;
		}
		HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
		TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat sdf_yyyyddMM = new SimpleDateFormat("yyyy-dd-MM");
		DateFormat sdfReturn = new SimpleDateFormat("MM/dd/yyyy");
		String dt = sdf_yyyyddMM.format(ts);
		String formatedDate = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		TimeZone tz;
		String timeType = null;
		if(tm != null && tm.equals("[HH:MM]")){
			tm = null;
		}
		if (dt != null && tm != null && tm.length() == 8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				dayOfMonth = Integer.parseInt(dt.substring(5, 7));
				month = Integer.parseInt(dt.substring(8, 10));

				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = tm.substring(6, 8);
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			tz = TimeZone.getTimeZone(zone);

			Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);

			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
			cal.setTimeZone(GMTZone);
			sdf.setTimeZone(tz);

			Date date = cal.getTime();

			formatedDate = sdf.format(date);
			
			try {
				dateTimeMap.put(OrderConstants.GMT_DATE, new Timestamp(sdfReturn.parse(getDatePart(formatedDate)).getTime()));
			} catch (ParseException e) {
				logger.info("Unable to parse time",e);
			}
			dateTimeMap.put(OrderConstants.GMT_TIME, getTimePart(formatedDate));

		}
		return dateTimeMap;
		
	}
	
	public static HashMap<String, Object> convertGMTToGivenTimeZone(Timestamp ts, String zone){
	
		HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
		TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
		SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String TOSformat = sdfToDate.format(ts);
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		TimeZone tz;
		
		try{
			year = Integer.parseInt(TOSformat.substring(0, 4));
			month  = Integer.parseInt(TOSformat.substring(5, 7));
			dayOfMonth = Integer.parseInt(TOSformat.substring(8, 10));
			
			hours = Integer.parseInt(TOSformat.substring(11, 13));
			min = Integer.parseInt(TOSformat.substring(14, 16));
			sec = Integer.parseInt(TOSformat.substring(17, 19));
			
		}catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		tz = TimeZone.getTimeZone(zone);
		Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
				hours, min, sec);

		cal.setTimeZone(GMTZone);

		if(hours<=11){
			cal.set(Calendar.AM_PM,Calendar.AM);
		}else {
			cal.set(Calendar.AM_PM, Calendar.PM);
		}

		sdfToDate.setTimeZone(tz);
		Date date = cal.getTime();
		dateTimeMap.put(OrderConstants.GMT_TIME, sdfToDate.format(date));
		return dateTimeMap;

	}
	
	
	public static Timestamp convertToGMT(Timestamp tm, String zone){
		Timestamp tmStmp = null;
		TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		
		TimeZone timeZone= TimeZone.getTimeZone(zone);
		
		sdf.setTimeZone(timeZone);
		

		dateTime = sdf.format(new Date(tm.getTime()));
		
		if(tm != null){
			dateTime = sdf.format(new Date(tm.getTime()));
		}
		if(dateTime != null && dateTime.length() == 19){
			try{
				year = Integer.parseInt(dateTime.substring(0, 4));
				month = Integer.parseInt(dateTime.substring(5, 7));
				dayOfMonth = Integer.parseInt(dateTime.substring(8, 10));
		
				hours = Integer.parseInt(dateTime.substring(11, 13));
				min = Integer.parseInt(dateTime.substring(14, 16));
				sec = Integer.parseInt("00");
				
			}catch(NumberFormatException nfe){
				logger.info("Number format exception",nfe);
			}
		}
		
		Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
				hours, min, sec);

		
		cal.setTimeZone(timeZone);
		
		
		sdf.setTimeZone(GMTZone);
		
		Date date = cal.getTime();

		String formatedDate = sdf.format(date);
		
		
		
		if(formatedDate != null){
			try {
				tmStmp =  new Timestamp(sdf1.parse(formatedDate).getTime());
			} catch (ParseException e) {
				logger.info("Unable to parse date",e);
			}
		}
		
		return tmStmp;
	}

	public static HashMap<String, Object> convertToGMT(Timestamp ts, String tm, String zone) {
		if(zone == null){
			zone = OrderConstants.EST_ZONE;
		}
		HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
		TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		DateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf_yyyymmdd.format(ts);
		String formatedDate = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		String timeType = null;
		TimeZone tz;
		if(tm != null && tm.equals("[HH:MM]")){
			tm = null;
		}
		if (dt != null && tm != null && tm.length()==8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				month = Integer.parseInt(dt.substring(5, 7));
				dayOfMonth = Integer.parseInt(dt.substring(8, 10));
	
				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = tm.substring(6, 8);
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			tz = TimeZone.getTimeZone(zone);
	
			Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);
	
			cal.setTimeZone(tz);
			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
			sdf.setTimeZone(GMTZone);
	
			Date date = cal.getTime();
	
			formatedDate = sdf.format(date);
			
			try {
				dateTimeMap.put(OrderConstants.GMT_DATE, new Timestamp(sdf_yyyymmdd.parse(getDatePart(formatedDate)).getTime()));
			} catch (ParseException e) {
				logger.info("Unable to parse date",e);
			}
			dateTimeMap.put(OrderConstants.GMT_TIME, getTimePart(formatedDate));

		}
		return dateTimeMap;
	}
	
	
	public static String getTimePart(String dateTime) {
		String time = null;
		if (dateTime != null) {
			StringTokenizer tok = new StringTokenizer(dateTime, " ");

			int i = 0;
			if (null != tok && tok.countTokens() == 3) {
				while (tok.hasMoreTokens()) {
					i = i + 1;
					
					if(i == 1){
						tok.nextToken();
					}
					if (i == 2) {
						time = tok.nextToken();
					}
					if (i == 3){
						time = time + " " + tok.nextToken();
					}
					
				}
			}
		}
		return time;
	}

	public static String getDatePart(String dateTime) {
		String date = null;
		if(dateTime != null){
			StringTokenizer tok = new StringTokenizer(dateTime, " ");
			
			int i = 0;
			if (null != tok && tok.countTokens() == 3) {
				while (tok.hasMoreTokens()) {
					i = i + 1;
	
					if (i == 1) {
						date = tok.nextToken();
					}
					tok.nextToken();
				}
			}
		}
		return date;
	}
	
	public static Date combineDateAndTime(Timestamp ts, String tm, String zone){
		if(zone == null){
			zone = OrderConstants.EST_ZONE;
		}
		Calendar cal = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		String timeType = null;
		DateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf_yyyymmdd.format(ts);
		if (dt != null && tm != null && tm.length()==8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				month = Integer.parseInt(dt.substring(5, 7));
				dayOfMonth = Integer.parseInt(dt.substring(8, 10));
	
				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = tm.substring(6, 8);
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			TimeZone tz = TimeZone.getTimeZone(zone);
	
			cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);
	
			cal.setTimeZone(tz);
			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
		}
		return cal.getTime();
	
	}
	/**
	 * 
	 * combineDateTime combines the date time , 
	 * Date in Date format as reqd
	 * @param date the Date
	 * @param time the time
	 * @return Date in reqquied format.
	 * @throws ParseException
	 */
	
	public static Date combineDateTime(Date date, String time) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		Date combinedDate = dfFull.parse(df.format(date) + " " + time);
		return combinedDate;
	}
	
	/**
	 * 
	 * combineDateTimeFromFormat
	 * Date 
	 * @param date
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date combineDateTimeFromFormat(Date date, String time) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		Date combinedDate = dfFull.parse(df.format(date) + " " + time);
		return combinedDate;
	}
	
	/**
	 * 
	 * combineDateTimeFromFormat
	 * Date 
	 * @param date
	 * @param time
	 * @param dateFormat
	 * @param timeFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date combineDateTimeFromFormat(Date date, String time, String dateFormat, String timeFormat) throws ParseException {
		DateFormat df = new SimpleDateFormat(dateFormat);
		DateFormat dfFull = new SimpleDateFormat(dateFormat+" "+timeFormat);
		Date combinedDate = dfFull.parse(df.format(date) + " " + time);
		return combinedDate;
	}
	/**
	 * 
	 * isDateTimeValid
	 * boolean 
	 * @param date
	 * @param time
	 * @param dateFormat
	 * @param timeFormat
	 * @return
	 */
	public static boolean isDateTimeValid(String date, String time, String dateFormat, String timeFormat) {
		boolean valid = true;
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			String fullDateFormat = dateFormat+" "+timeFormat;
			DateFormat dfFull = new SimpleDateFormat(fullDateFormat);
			dfFull.parse(date + " " + time);
		} catch (Exception e) {
			valid = false;
		}
		return valid;
	}
	
	/**
	 * adjustYearValue adjusts the value of the year from two digit to normal four digit value.
	 * @param dateField The date field , with either mm/dd/yyyy format or mm/dd/yy format
	 * @return The date field converted to mm/dd/yyyy format if its not in it.
	 */
	public static String adjustYearValue(String dateField){
		
		String adjustedDateValue = ""; 
		
		try {
			if(!StringUtils.isBlank(dateField)){
				int year = 0;
				int lastIndex = dateField.lastIndexOf("/");
				if(lastIndex>0){
					String yearValue = dateField.substring(lastIndex+1);
					if(!StringUtils.isBlank(yearValue)){
						year = Integer.parseInt(yearValue);
						
						if(year<100 && year >0){
							year = year+2000;
							adjustedDateValue = dateField.substring(0, lastIndex+1)+""+year;
						}
						
					}
				}
				
			}
		}catch (Exception e) {
			return dateField;
		}
		
		
		return StringUtils.isBlank(adjustedDateValue)?dateField:adjustedDateValue;
	}
	
		
	public static boolean isDateTimeInFuture(String date, String time, String dateFormat, String timeFormat){
		
		boolean inFuture = false;
		
			try {
			
				DateFormat df = new SimpleDateFormat(dateFormat);
				String fullDateFormat = dateFormat+" "+timeFormat;
				DateFormat dfFull = new SimpleDateFormat(fullDateFormat);
				Date datePassed = dfFull.parse(date + " " + time);
				
				 Date dateSystem = new Date();
				    
				    //DateFormat firstFormat = new SimpleDateFormat();
				    DateFormat serverFormat = new SimpleDateFormat();
				   // TimeZone serverTime = TimeZone.getTimeZone(new GregorianCalendar().getTimeZone().getID());
				    TimeZone serverTimeInCST = TimeZone.getTimeZone("CST");
				    //firstFormat.setTimeZone(serverTime);
				    serverFormat.setTimeZone(serverTimeInCST);
				    serverFormat.format(dateSystem);
				    
				    Date dateCST = new SimpleDateFormat().parse(serverFormat.format(dateSystem));
				    
				    if(datePassed.after(dateCST)){
				    	inFuture = true;
				    }else{
				    	inFuture = false;
				    }
				    //System.out.println(new SimpleDateFormat().parse(secondFormat.format(dateSystem)));
				    
			} catch (Exception e) {
				inFuture = false;
			}
		
		return inFuture;
	}
	
	
	public static Calendar combineDateTime(Calendar date, String time) throws ParseException {
		Date calDate = new Date();
		calDate.setTime(date.getTimeInMillis());
		Calendar cal = new GregorianCalendar();
		cal.setTime(calDate);
		return cal;
	}
	
	public static Date getCurrentGMT() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		date = out.parse(sdf.format(date));
		return date;
	}
	public static Date getServiceDateInGMT(Date serviceDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = out.parse(sdf.format(serviceDate));
		return date;
	}
	
	/**
	 * Takes a TimeStamp and converts it to xd yh from current time where d is days and h is hours
	 * @param routedDate
	 * @return
	 */
	public static String getAgeOfOrder(Timestamp routedDate) {
		Calendar calRoutedDate = Calendar.getInstance();
		Calendar calRightNow = Calendar.getInstance();

		if (routedDate == null)
			return "No Data";
		calRoutedDate.setTime(routedDate);
		return ((calRightNow.getTimeInMillis() - calRoutedDate
				.getTimeInMillis()) / 24 / 60 / 60 / 1000)
				+ "d "
				+ ((calRightNow.getTimeInMillis() - calRoutedDate
						.getTimeInMillis()) / 60 / 60 / 1000) % 24 + "h";

	}
	
	
	public static Date convertGMTToGivenTimeZoneforTimeonsite(Timestamp ts, String tm, String zone){
		if(zone == null){
			zone = OrderConstants.EST_ZONE;
		}
		HashMap<String, Object> dateTimeMap = new HashMap<String, Object>();
		TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		DateFormat sdf_yyyyddMM = new SimpleDateFormat("yyyy-dd-MM");
		DateFormat sdfReturn = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf_yyyyddMM.format(ts);
		String formatedDate = null;
		Date returnedDate= new Date(); 
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		TimeZone tz;
		String timeType = null;
		if(tm != null && tm.equals("[HH:MM]")){
			tm = null;
		}
		if (dt != null && tm != null && tm.length() == 8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				dayOfMonth = Integer.parseInt(dt.substring(5, 7));
				month = Integer.parseInt(dt.substring(8, 10));

				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt(tm.substring(6, 8));
				
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			tz = TimeZone.getTimeZone(zone);

			Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);

			cal.setTimeZone(GMTZone);
			
			sdf.setTimeZone(tz);

			 returnedDate = cal.getTime();
			
			
			/*formatedDate = sdf.format(returnedDate);
			
			
			try {
			dateTimeMap.put(OrderConstants.GMT_DATE, new Timestamp(sdf.parse(getDatePart(formatedDate)+" "+getTimePart(formatedDate)).getTime()));
			}
			catch (ParseException e) {
				logger.info("Unable to parse date",e);
			}
			dateTimeMap.put(OrderConstants.GMT_TIME, getTimePart(formatedDate));*/

		}
		return returnedDate;
		
	}
	public static String getTimePartforTimeonsite(String dateTime) {
		String time = null;
		if (dateTime != null) {
			StringTokenizer tok = new StringTokenizer(dateTime, " ");

			int i = 0;
							if (null != tok && tok.countTokens() == 6) {
								while (tok.hasMoreTokens()) {
									i = i + 1;
									
									if(i == 1){
										tok.nextToken();
									}
									if(i == 2){
										tok.nextToken();
									}
									if(i == 3){
										 tok.nextToken();
									}	
									if(i == 4){
										time=tok.nextToken(); 
									}
									if (i == 5) {
										 tok.nextToken();
									}
									if (i == 6){
										tok.nextToken();
									}
									
								}
							}
						}
			
		return time;
	}	
	
	
	public static String convertToDojoDateFormat(String date){
		String validDate = "";
		DateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String theDate = "";
		Date actualDate = null;
		   try{
			   actualDate = sdf1.parse(date);
			   theDate    = sdf2.format(actualDate);
		   }

		   catch (ParseException e){
			   return "invalid_date";
		   }
		   
		return theDate;
	}

	/* method to convert GMT to Given Timezone and return as String in MM/dd/yyyy hh:mm a (z) format*/
		public static String convertGMTToGivenTimeZoneInString(Timestamp ts, String tm, String zone){
			if(zone == null){
				zone = OrderConstants.SERVICELIVE_ZONE;
			}

			TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a ");
			DateFormat sdf_yyyyddMM = new SimpleDateFormat("yyyy-dd-MM");
			String dt = sdf_yyyyddMM.format(ts);
			String formatedDate = null;
			int year = 0;
			int month = 0;
			int dayOfMonth = 0;
			int hours = 0;
			int min = 0;
			int sec = 0;
			TimeZone tz;
			String timeType = null;

			if(tm != null && tm.equals("[HH:MM]")){
				tm = null;
			}
			if (dt != null && tm != null && tm.length() == 8 && zone != null) {
				try {
					year = Integer.parseInt(dt.substring(0, 4));
					dayOfMonth = Integer.parseInt(dt.substring(5, 7));
					month = Integer.parseInt(dt.substring(8, 10));

					hours = Integer.parseInt(tm.substring(0, 2));
					min = Integer.parseInt(tm.substring(3, 5));
					sec = Integer.parseInt(tm.substring(6, 8));
					timeType = tm.substring(6, 8);

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
				tz = TimeZone.getTimeZone(zone);

				Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
						hours, min, sec);

				cal.setTimeZone(GMTZone);
				if(timeType.equalsIgnoreCase("AM")){
					cal.set(Calendar.AM_PM,Calendar.AM);
				}else if(timeType.equalsIgnoreCase("PM")){
					cal.set(Calendar.AM_PM, Calendar.PM);
				}
				Date date = cal.getTime();
				
				sdf.setTimeZone(tz);
				formatedDate=sdf.format(date);
				formatedDate=formatedDate+"("+zone+")";

			}
			return formatedDate;

	}
		
		public static String convertToTimezone(Timestamp tm, String zone){
			TimeZone GMTZone = TimeZone.getTimeZone(OrderConstants.GMT_ZONE);
			TimeZone timeZone= TimeZone.getTimeZone(zone);
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			DateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			String dateTime = null;
			int year = 0;
			int month = 0;
			int dayOfMonth = 0;
			int hours = 0;
			int min = 0;
			int sec = 0;
			sdf.setTimeZone(GMTZone);
			

			dateTime = sdf.format(new Date(tm.getTime()));
			
			if(tm != null){
				dateTime = sdf.format(new Date(tm.getTime()));
			}
			if(dateTime != null && dateTime.length() == 19){
				try{
					year = Integer.parseInt(dateTime.substring(0, 4));
					month = Integer.parseInt(dateTime.substring(5, 7));
					dayOfMonth = Integer.parseInt(dateTime.substring(8, 10));
			
					hours = Integer.parseInt(dateTime.substring(11, 13));
					min = Integer.parseInt(dateTime.substring(14, 16));
					sec = Integer.parseInt("00");
					
				}catch(NumberFormatException nfe){
					logger.info("Number format exception",nfe);
				}
			}
			
			Calendar cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);

			
			cal.setTimeZone(GMTZone);
			
			sdf.setTimeZone(timeZone);
			
			Date date = cal.getTime();

			String formatedDate = sdf1.format(date);
			
			return formatedDate;
		}
		
		
		
		
		public static void main(String[] args) {
			/*System.out.println(TimeUtils.isDateTimeValid("02/09/2009", "09:45 PM",
					"MM/dd/yyyy" , "hh:mm a"));
			System.out.println(TimeUtils.isDateTimeInFuture("03/06/2009", "03:00 PM",
					"MM/dd/yyyy" , "hh:mm a"));
			
			System.out.println(TimeUtils.isDateTimeInFuture(null, "01:45 PM",
					"MM/dd/yyyy" , "hh:mm a"));*/
			
			System.out.println(TimeUtils.adjustYearValue("02/3/20"));
			
			
		}
		
		public static Date convertStringToDate(String dt, String tm, DateFormat df) throws ParseException {
			String date = dt;
			
			if(tm != null)
				date += " " + tm;
			
			return df.parse(date);
			
		}
		
		/**
		 * This method actually works!
		 * @param dt
		 * @param zone
		 * @param dateFormat
		 * @return Date as string of given format (yes, calculated in accordance with time zone provided!)
		 */
		public static String convertGMTtoTimezone(Date dt, String zone, DateFormat simpleDateFormat) {
		    Date bob = constructGMT(dt.getTime());       
	        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(zone));
	        return simpleDateFormat.format(bob);
		}

		private static Date constructGMT(long localTimeAmount)
		   {

		       GregorianCalendar cal = new GregorianCalendar(TimeZone.getDefault());
		       cal.setTime(new Date(localTimeAmount));
		       cal.setTime(cal.getTime());
		       int timeOffset = cal.get(Calendar.ZONE_OFFSET);
		       boolean doWeUseDST = false;
		       boolean areWeInDSTRightNow = false;
		       doWeUseDST = TimeZone.getDefault().useDaylightTime();
		       areWeInDSTRightNow = TimeZone.getDefault().inDaylightTime(new Date(localTimeAmount));

		       if ( doWeUseDST && areWeInDSTRightNow )
		       {
		           int dstOffset = cal.get(Calendar.DST_OFFSET);   // adjust by one hour if needed
		           timeOffset += dstOffset;
		       }
		       // calHoursOff is a positive or negative value in milliseconds that is its
		       // time amount away from the GMT timezone

		       int hoursOffset = timeOffset / (1000*60*60);
		       int minutesOffset = 0;
		       if ( timeOffset - (1000*60*60*hoursOffset) != 0 )
		           minutesOffset = (timeOffset - (1000*60*60*hoursOffset)) / (1000*60);

		       cal.setTime(new Date(localTimeAmount));

		       cal.setTimeZone(TimeZone.getTimeZone("GMT") );
		       cal.setTime(cal.getTime());

		       Date dt = cal.getTime();     // store the java.util.Date in the member variable

		       addHours(dt, hoursOffset);     // modify the hours by the difference
		                                       // in timezones from the 'new Calendar()
		                                       // to the GMT timezone
		       addMinutes(dt, minutesOffset);
		       
		       return dt;
		   }
		
		private static final long HOUR_IN_MILLISECONDS = 1000*60*60;
		private static final long MINUTE_IN_MILLISECONDS = 1000*60;
		private static void addHours(Date dt, int hours)
		   {
		       if(hours != 0)
		       {
		           long tempMs = dt.getTime() + hours*HOUR_IN_MILLISECONDS;
		           dt.setTime(tempMs);
		       }
		   }

		private static void addMinutes(Date dt, int mins)
		   {
		       if(mins != 0)
		       {
		           long tempMs = dt.getTime() + mins*MINUTE_IN_MILLISECONDS;
		           dt.setTime(tempMs);
		       }
		   }
		
		public  static Integer get24HourIntegerFromString(String time)
		{
			if(time == null)
				return null;
			
			if(time.length() < 4)
				return null;
			
			int index = time.indexOf(":");
			String hourStr = time.substring(0, index);
			int hours = Integer.parseInt(hourStr);
			if(time.equalsIgnoreCase("12:00 am"))
				hours = 0;						
			if(time.endsWith("pm") && hours != 12)
				hours += 12;
			
			return hours;
		}
		
		public static TimeZone getLocalTimeZone(Locale locale)
		{
		    Calendar cal = Calendar.getInstance(locale);
		    return cal.getTimeZone();
		}
		
		public static String convertTimeStampInGMTtoTimeZone(Timestamp ts,String zone){
			String timeStamp = null;
			if(ts!=null){
				timeStamp= ts.toString();
				if(timeStamp.length()>=19){
					int year = Integer.parseInt(timeStamp.substring(0, 4));
					int month = Integer.parseInt(timeStamp.substring(5, 7));
					int dayOfMonth = Integer.parseInt(timeStamp.substring(8, 10));
		
					int hours = Integer.parseInt(timeStamp.substring(11, 13));
					int min = Integer.parseInt(timeStamp.substring(14, 16));
					int sec = Integer.parseInt("00");
					
					GregorianCalendar cal = new GregorianCalendar(year,month-1,dayOfMonth,hours,min,sec);
					cal.setTimeZone(TimeZone.getTimeZone(OrderConstants.GMT_ZONE));
					DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
					sdf.setTimeZone(TimeZone.getTimeZone(zone));
					timeStamp= sdf.format(cal.getTime());
				}
			}
			return timeStamp;
		}
}