package com.servicelive.orderfulfillment.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.DateUtil;

public class BusinessCalendar implements IBusinessCalendar {
    protected final Logger logger = Logger.getLogger(getClass());

    String name;
    BusinessCalendarDay[] businessCalDays = null;
    HolidayPeriod[] holidays = null;

    public BusinessCalendar(IBusinessCalendarInitializer businessCalendarInitializer) {
        businessCalendarInitializer.initialize(this);
    }

    public Date findClosestStartOfBusinessDayFromDate(Date date, TimeZone targetTimeZone) {
    	BusinessCalendarDay businessCalendarDay = findBusinessCalendarDay(date, targetTimeZone);
        if (businessCalendarDay != null && businessCalendarDay.timeRange != null) {
            if (getMinutesAfterMidnight(date, targetTimeZone) < businessCalendarDay.timeRange.getEndTimeInMinutes()) {
                return getNewDateWithMinutesAdjustedFromMidnight(date, businessCalendarDay.timeRange.getStartTimeInMinutes(), targetTimeZone);
            }
        }

        return addBusinessDaysToBusinessDate(date, 1, targetTimeZone);
    }

    public Date findClosestEndOfBusinessDayFromDate(Date date, TimeZone targetTimeZone) {
    	Date closestBusinessDate = findClosestStartOfBusinessDayFromDate(date, targetTimeZone);
        BusinessCalendarDay businessCalDate = findBusinessCalendarDay(closestBusinessDate, targetTimeZone);
        int minutesAfterMidnightForEndOfBusinessDay = businessCalDate.getTimeRange().getEndTimeInMinutes();
        return getNewDateWithMinutesAdjustedFromMidnight(closestBusinessDate, minutesAfterMidnightForEndOfBusinessDay, targetTimeZone);
    }

    public Date addBusinessDaysToDate(Date date, int days, TimeZone targetTimeZone) {
    	Date startBusinessDate = findClosestStartOfBusinessDayFromDate(date, targetTimeZone);
        if (DateUtil.isDayTheSame(date, startBusinessDate, targetTimeZone)) {
            return addBusinessDaysToBusinessDate(startBusinessDate, days, targetTimeZone);
        }
        return startBusinessDate;
    }

    private Date addBusinessDaysToBusinessDate(Date date, int days, TimeZone timeZone) {
        Date busDate = date;
        for (int daysleft=days; daysleft>0; daysleft--) {
            busDate = findNextBusinessCalendarDayStart(busDate, timeZone);
        }
        return busDate;
    }

    private BusinessCalendarDay findBusinessCalendarDay(Date date, TimeZone timeZone) {
        int weekDayIndex = findBusinessCalendarDayIndex(date, timeZone);
        return businessCalDays[weekDayIndex];
    }

    private int findBusinessCalendarDayIndex(Date date, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private int getMinutesAfterMidnight(Date date, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTime(date);
        return (calendar.get(Calendar.HOUR_OF_DAY) * 60) + calendar.get(Calendar.MINUTE);
    }

    private Date findNextBusinessCalendarDayStart(Date date, TimeZone timeZone) {
        int weekDayIndex = findBusinessCalendarDayIndex(date, timeZone);
        BusinessCalendarDayTimeRange businessTimeRange = null;

        int dayCount = 0;
        while (businessTimeRange == null) {
            dayCount++;
            weekDayIndex = getNextDayOfTheWkIdx(weekDayIndex);
            businessTimeRange = businessCalDays[weekDayIndex].getTimeRange();
        }

        Date businessDate = addRegularDaysToDate(date, dayCount, timeZone);
        if (isHoliday(businessDate)) {
            businessDate = findNextBusinessCalendarDayStart(businessDate, timeZone);
        }
        
        return getNewDateWithMinutesAdjustedFromMidnight(businessDate, businessTimeRange.getStartTimeInMinutes(), timeZone);
    }

    private Date getNewDateWithMinutesAdjustedFromMidnight(Date date, int minutesFromMidnight, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, minutesFromMidnight);
        return calendar.getTime();
    }

    private int getNextDayOfTheWkIdx(int startingIdx) {
        if (startingIdx == 7) {
            return 1;
        } else {
            return startingIdx + 1;
        }
    }

    private boolean isHoliday(Date date) {
        if (holidays != null) {
            for (HolidayPeriod holiday : holidays) {
                if (holiday.getDateRange().isDateInRange(date)) return true;
            }
        }
        return false;
    }

    private Date addRegularDaysToDate(Date date, int numOfDays, TimeZone timeZone) {
        Calendar calendar = new GregorianCalendar(timeZone);
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numOfDays);
        return calendar.getTime();
    }
}
