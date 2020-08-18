package com.servicelive.orderfulfillment.orderprep.scheduling;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CurrentDayResolver implements ICurrentDayResolver {
    public Calendar getCurrentCalendarDate(String timeZoneId) {
        return new GregorianCalendar(TimeZone.getTimeZone(timeZoneId));
    }
}
