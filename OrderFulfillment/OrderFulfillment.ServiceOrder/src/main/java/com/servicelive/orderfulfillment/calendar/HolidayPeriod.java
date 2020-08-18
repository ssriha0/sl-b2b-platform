package com.servicelive.orderfulfillment.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HolidayPeriod {
    DateRange dateRange;

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
        fixDateRangeStart(this.dateRange);
        fixDateRangeEnd(this.dateRange);
    }

    private void fixDateRangeStart(DateRange dateRange) {
        Date startDate = dateRange.getStartDate();
        if (startDate==null) return;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        dateRange.setStartDate(calendar.getTime());
    }

    private void fixDateRangeEnd(DateRange dateRange) {
        Date startDate = dateRange.getStartDate();
        Date endDate = dateRange.getEndDate();

        if (endDate==null && startDate==null) {
            return;
        }
        Calendar calendar = new GregorianCalendar();
        if (endDate==null) {
            calendar.setTime(startDate);
        } else {
            calendar.setTime(endDate);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        dateRange.setEndDate(calendar.getTime());
    }
}
