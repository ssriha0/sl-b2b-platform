package com.servicelive.orderfulfillment.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateRange {
    private Date startDate;
    private Date endDate;

    public DateRange() {}

    public DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isDateInRange(Date date) {
        long timeInMillis = date.getTime();
        if (startDate!=null && endDate!=null) {
            return startDate.getTime() <= timeInMillis && timeInMillis <= endDate.getTime();
        } else if (startDate!=null) {
            return isSameDay(date, startDate);
        }
        return false;
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
            && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }
}
