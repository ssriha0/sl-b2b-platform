package com.servicelive.orderfulfillment.orderprep.scheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.calendar.IBusinessCalendar;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class NextDayScheduleCalculator implements IScheduleCalculator {
    IBusinessCalendar businessCalendar;
    ICurrentDayResolver dayResolver;

    public NextDayScheduleCalculator(IBusinessCalendar businessCalendar) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = new CurrentDayResolver();
    }

    public NextDayScheduleCalculator(IBusinessCalendar businessCalendar, ICurrentDayResolver dayResolver) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = dayResolver;
    }

    public DateRange calculateServiceDates(DateRange dateRange, ServiceOrder serviceOrder) {
        Calendar today = dayResolver.getCurrentCalendarDate(serviceOrder.getServiceLocationTimeZone());
        TimeZone timeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
        Date nextBusinessDay = businessCalendar.addBusinessDaysToDate(today.getTime(), 1, timeZone);
        Date newStartDate = businessCalendar.findClosestStartOfBusinessDayFromDate(nextBusinessDay, timeZone);
        Date newEndDate = businessCalendar.findClosestEndOfBusinessDayFromDate(newStartDate, timeZone);
        return new DateRange(newStartDate, newEndDate);
    }
}
