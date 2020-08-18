package com.servicelive.orderfulfillment.calendar.binding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class BusinessCalendarDayConfig {
    private static final String HOUR_FORMAT = "HH:mm";
    private static final char timeSeparator = '-';
    private String hoursString;

    public String getHoursString() {
        return hoursString;
    }

    public void setHoursString(String hoursString) {
        this.hoursString = hoursString;
    }

    public int getFromPartInMinutesFromMidnight() throws ParseException {
        DateFormat hourFormat = new SimpleDateFormat(HOUR_FORMAT);
        Date from = hourFormat.parse(extractFromPart());
        return getMinutesFromMidnightForDate(from);
    }

    public int getToPartInMinutesFromMidnight() throws ParseException {
        DateFormat hourFormat = new SimpleDateFormat(HOUR_FORMAT);
        Date to = hourFormat.parse(extractToPart());
        return getMinutesFromMidnightForDate(to);
    }

    protected int getMinutesFromMidnightForDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return (hour * 60) + minute;
    }

    protected String extractFromPart() throws ParseException {
        String[] rangeParts = StringUtils.split(hoursString, timeSeparator);
        if (rangeParts.length != 2) throw new ParseException("error splitting hours range", 0);
        return rangeParts[0].trim();
    }

    protected String extractToPart() throws ParseException {
        String[] rangeParts = StringUtils.split(hoursString, timeSeparator);
        if (rangeParts.length != 2) throw new ParseException("error splitting hours range", 0);
        return rangeParts[1].trim();
    }
}
