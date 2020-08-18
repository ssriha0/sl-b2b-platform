package com.servicelive.orderfulfillment.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtil {
    public static boolean isDayTheSame(Date date1, Date date2) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isDayTheSame(Date date1, Date date2, TimeZone timeZone) {
        Calendar cal1 = new GregorianCalendar(timeZone);
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar(timeZone);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static void roundCalendarDateToNearestQuarterHour(Calendar calDateToBeRounded) {
        int minutes = calDateToBeRounded.get(Calendar.MINUTE);
        calDateToBeRounded.set(Calendar.MINUTE, roundDownToNearestMultiple(minutes, 15));
    }

    private static int roundDownToNearestMultiple(int numToRound, int nearestMultiple) {
        int mult = numToRound / nearestMultiple;
        return mult * nearestMultiple;
    }
}
