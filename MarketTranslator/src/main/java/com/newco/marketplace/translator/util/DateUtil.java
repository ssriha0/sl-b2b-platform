package com.newco.marketplace.translator.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	public static final long ONE_MINUTE = 60 * 1000;
	public static final Integer one_minute = Integer.valueOf(60 * 1000);
	public static final long ONE_HOUR = 60 * 60 * 1000;
	public static final Integer one_hour = Integer.valueOf(60 * 60 * 1000);
	public static final long ONE_DAY = 60 * 60 * 1000 * 24;
	public static final Integer one_day = Integer.valueOf(60 * 60 * 1000 * 24);
	
	public static Date addDaysToDate(Date date, Integer daysToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, daysToAdd.intValue());
		date = cal.getTime();
		return date;
	}

	public static Date addHoursToDate(Date date, Integer hoursToAdd) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hoursToAdd.intValue());
		date = cal.getTime();
		return date;
	}

	public static Date getDateMidnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDateMidnight(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		date = sdfTime.parse(sdf.format(date) + "-00-00-00");
		return date;
	}

	public static Integer getHourFromDate(Date in) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		Integer hour = new Integer(cal.get(Calendar.HOUR_OF_DAY));
		return hour;
	}
	
	public static String formatDate(Date in, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(in);
	}

	public static Integer getMinutesFromDate(Date in) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(in);
		Integer minute = new Integer(cal.get(Calendar.MINUTE));
		return minute;
	}

	/**
	 * Excluded dates are all Sundays and
Labor Day 	1st Monday of Sep 	Mon, Sep 1st
Thanksgiving Day 	4th Thursday of Nov
12/25
7/4
	 * @param start
	 * @param end
	 * @return
	 */
	public static Map<String, Date> getDatesAfterExcludedDays(Date start, Date end, boolean promiseDate) {
		Map<String, Date> out = new HashMap<String, Date>();
		try {
			Calendar cstart = Calendar.getInstance();
			cstart.setTime(start);
			Calendar cend = Calendar.getInstance();
			cend.setTime(end);

			int istart = cstart.get(Calendar.DAY_OF_WEEK);
			int iend = cstart.get(Calendar.DAY_OF_WEEK);
			int weeks = cend.get(Calendar.WEEK_OF_YEAR) - cstart.get(Calendar.WEEK_OF_YEAR);

			if (! promiseDate && istart == Calendar.SUNDAY) {
				start = DateUtil.addDaysToDate(start, Integer.valueOf(1));
			}
			if (iend == Calendar.SUNDAY) {
				end = DateUtil.addDaysToDate(end, Integer.valueOf(1));
			}
			//now count Sundays/Holidays between the dates
			if (weeks > 0) {
				SimpleDateFormat fmt = new SimpleDateFormat("MMddyyyy");
				int daysToAdd = weeks;
				if (isDateInRange(start, end, fmt.parse("07042008"))) daysToAdd++;
				if (isDateInRange(start, end, fmt.parse("09012008"))) daysToAdd++;
				if (isDateInRange(start, end, fmt.parse("12252008"))) daysToAdd++;

				//now check Sundays again

				end = addDaysToDate(end, Integer.valueOf(daysToAdd));
			}
			out.put("start", start);
			out.put("end", end);
		}
		catch (Exception e) {
			//FIXME should this be handled?
		}
		return out;
	}

	public static boolean isDateInRange(Date start, Date end, Date test) {
		boolean inRange = false;
		if (start.compareTo(test) < 0 && end.compareTo(test) > 0) {
			inRange = true;
		}
		return inRange;
	}

	public static boolean isSameDay(Date date1, Date date2) {
		boolean sameDay = false;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
			sameDay = true;
		}
		return sameDay;
	}

	public static boolean isSunday(Date date) {
		boolean isSunday = false;
		Calendar cstart = Calendar.getInstance();
		cstart.setTime(date);
		if (cstart.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			isSunday = true;
		}
		return isSunday;
	}

	public static boolean isHoliday(Date date) {
		try 
		{
			if( HolidayUtils
					.getHolidaysForYear(new Integer((new SimpleDateFormat("yyyy")).format(date)))
					.contains((new SimpleDateFormat("MMddyyyy")).format(date)) )
			{
				return true;
			}
		}
		catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public static boolean isExpiredDate(Date startDate){
		if(startDate == null){
			return false;
		}
		Date today = new Date();

		if(startDate.before(today)){
			return false;
		}

		return true;
	}
	public static Boolean isBusinessDay( Date d )
	{
		return new Boolean( (!isSunday(d)) && (!isHoliday(d)) );
	}


	public static String formatDate(long time, String format) {
		Date in = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(in);
}

}
