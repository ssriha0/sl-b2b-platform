package com.newco.marketplace.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TimeChangeUtil {

    public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");

	private static Logger logger = Logger.getLogger(TimeChangeUtil.class);
	public static final String DATE_PART = "DatePart";
	public static final String TIME_PART = "TimePart";
    public static final String FMT_TIME_PART = "hh:mm a";
    public static final String FMT_TIME_PART_SEC = "HH:mm:ss";
    public static final String FMT_DATE_PART_SHORT = "yyyy-MM-dd";
    private static final int FMT_YEAR_START_IDX = 0;
    private static final int FMT_YEAR_END_IDX = 4;
    private static final int FMT_MON_START_IDX = 5;
    private static final int FMT_MON_END_IDX = 7;
    private static final int FMT_DAY_START_IDX = 8;
    private static final int FMT_DAY_END_IDX = 10;
    public static final String FMT_DATE_PART_LONG = FMT_DATE_PART_SHORT + " " + FMT_TIME_PART;
	

    public static Calendar getCalTimeFromParts(Date datePart, TimeZone timeZone) {
    	DateFormat timeFormat = new SimpleDateFormat( FMT_TIME_PART );
        String timePart = timeFormat.format(datePart);
        return getCalTimeFromParts(datePart, timePart, timeZone);
    }
    
    public static Calendar getCalTimeFromParts(Date datePart, String timePart, TimeZone timeZone) {
        DateFormat shortDateFormat = new SimpleDateFormat(FMT_DATE_PART_SHORT);
        DateFormat timeFormat = new SimpleDateFormat(FMT_TIME_PART);
        String dateString = shortDateFormat.format(datePart);
        timeFormat.getCalendar();

        int year = extractYear(dateString);
        int month = extractMonth(dateString) - 1;
        int dayOfMonth = extractDayOfMonth(dateString);
        int hourOfDay = determineHourOfDay(timePart);
        int min = extractMin(timePart);
        int sec = extractSec(timePart);

        Calendar calTime = new GregorianCalendar(timeZone);
        calTime.clear();
        calTime.set(year, month, dayOfMonth, hourOfDay, min, sec);

        return calTime;
    }
    
    public static Calendar getCalTimeFromPartsWithSeconds(Date datePart, String timePart, TimeZone timeZone) {
        DateFormat shortDateFormat = new SimpleDateFormat(FMT_DATE_PART_SHORT);
        DateFormat timeFormat = new SimpleDateFormat(FMT_TIME_PART_SEC);
        String dateString = shortDateFormat.format(datePart);
        timeFormat.getCalendar();

        int year = extractYear(dateString);
        int month = extractMonth(dateString) - 1;
        int dayOfMonth = extractDayOfMonth(dateString);
        int hourOfDay = determineHourOfDaySec(timePart);
        int min = extractMinSec(timePart);
        int sec = extractSecSec(timePart);

        Calendar calTime = new GregorianCalendar(timeZone);
        calTime.clear();
        calTime.set(year, month, dayOfMonth, hourOfDay, min, sec);

        return calTime;
    }

    static int extractYear(String dateString) {
        return Integer.parseInt(dateString.substring(FMT_YEAR_START_IDX, FMT_YEAR_END_IDX));
    }

    static int extractMonth(String dateString) {
        return Integer.parseInt(dateString.substring(FMT_MON_START_IDX, FMT_MON_END_IDX));
    }

    static int extractDayOfMonth(String dateString) {
        return Integer.parseInt(dateString.substring(FMT_DAY_START_IDX, FMT_DAY_END_IDX));
    }

    static int determineHourOfDay(String timeString) {
        return extractTimePart(timeString, Calendar.HOUR_OF_DAY);
    }

    static int extractMin(String timeString) {
        return extractTimePart(timeString, Calendar.MINUTE);
    }

    static int extractSec(String timeString) {
        return extractTimePart(timeString, Calendar.SECOND);
    }
    
    static int determineHourOfDaySec(String timeString) {
        return extractTimePartSec(timeString, Calendar.HOUR_OF_DAY);
    }

    static int extractMinSec(String timeString) {
        return extractTimePartSec(timeString, Calendar.MINUTE);
    }

    static int extractSecSec(String timeString) {
        return extractTimePartSec(timeString, Calendar.SECOND);
    }

    private static int extractTimePart(String timeString, int calFldTyp) {
    	if (StringUtils.isBlank(timeString)) {
    		logger.error("extractTimePart: timeString is blank.");
    		return 0;
    	}    	
    	
        SimpleDateFormat timeFormat = new SimpleDateFormat(FMT_TIME_PART);
        try {
            timeFormat.parse(timeString);
        } catch (ParseException e) {
            logger.error("error parsing timeString using format" + FMT_TIME_PART, e);
            return 0;
        }
        return timeFormat.getCalendar().get(calFldTyp);
    }
    
    private static int extractTimePartSec(String timeString, int calFldTyp) {
    	if (StringUtils.isBlank(timeString)) {
    		logger.error("extractTimePart: timeString is blank.");
    		return 0;
    	}    	
    	
        SimpleDateFormat timeFormat = new SimpleDateFormat(FMT_TIME_PART_SEC);
        try {
            timeFormat.parse(timeString);
        } catch (ParseException e) {
            logger.error("error parsing timeString using format" + FMT_TIME_PART_SEC, e);
            return 0;
        }
        return timeFormat.getCalendar().get(calFldTyp);
    }
    
    public static Date getDate(Calendar source, TimeZone timeZone){
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    	Calendar nCal = changeTimeZone(source, timeZone);
    	String datePart = String.format("%1$tY-%1$tm-%1$td 12:00 am",nCal);
    	try {
			return sdf.parse(datePart);
		} catch (ParseException e) {
			//nothing to do
		}
		return nCal.getTime();
    }
    
    public static Date getDateSec(Calendar source, TimeZone timeZone){
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar nCal = changeTimeZone(source, timeZone);
    	String datePart = String.format("%1$tY-%1$tm-%1$td 12:00:00",nCal);
    	try {
			return sdf.parse(datePart);
		} catch (ParseException e) {
			//nothing to do
		}
		return nCal.getTime();
    }
    
    public static String getTimeString(Calendar dateTime, TimeZone timeZone){
    	return String.format("%1$tI:%1$tM %1$Tp", changeTimeZone(dateTime,timeZone));
    }

    public static String getTimeStringSec(Calendar dateTime, TimeZone timeZone){
    	return String.format("%1$tH:%1$tM:%1$tS", changeTimeZone(dateTime,timeZone));
    }
    public static Calendar changeTimeZone(Calendar source, TimeZone targetTZ){
        logger.debug(String.format("Received conversion request for: %1$tY/%1$tb/%1$td - %1$tH:%1$tM %1$tZ",source));
        Calendar returnVal = Calendar.getInstance(targetTZ);
        returnVal.setTimeInMillis(source.getTimeInMillis());
        logger.debug(String.format("Returning conversion request for: %1$tY/%1$tb/%1$td - %1$tH:%1$tM %1$tZ",returnVal));
        return returnVal;
    }
    
    public static Date convertDateBetweenTimeZones(Date sourceDate, TimeZone sourceTimeZone, TimeZone destTimeZone) {	
    	Calendar sourceCal = getCalTimeFromParts(sourceDate, sourceTimeZone);
    	logger.debug(String.format("convertDateBetweenTimeZones - sourceCal: %1$tY/%1$tb/%1$td - %1$tH:%1$tM %1$tZ",sourceCal));
    	Calendar destCal = changeTimeZone(sourceCal, destTimeZone);
    	logger.debug(String.format("convertDateBetweenTimeZones - destCal: %1$tY/%1$tb/%1$td - %1$tH:%1$tM %1$tZ",destCal));
    	
    	int year = destCal.get(Calendar.YEAR);
    	int month = destCal.get(Calendar.MONTH);
    	int dayOfMonth = destCal.get(Calendar.DAY_OF_MONTH);
    	int hourOfDay = destCal.get(Calendar.HOUR_OF_DAY);
    	int min = destCal.get(Calendar.MINUTE);
    	
    	// Return a date with the time set in the destination time zone.  The date object does not have 
    	// timezone set, so it can be saved to the database with whatever values are set here.
    	Calendar returnCal = new GregorianCalendar();
    	returnCal.set(year, month, dayOfMonth, hourOfDay, min);
    	logger.debug(String.format("convertDateBetweenTimeZones - returnCal: %1$tY/%1$tb/%1$td - %1$tH:%1$tM %1$tZ",returnCal));
    	return returnCal.getTime();
    }

}

