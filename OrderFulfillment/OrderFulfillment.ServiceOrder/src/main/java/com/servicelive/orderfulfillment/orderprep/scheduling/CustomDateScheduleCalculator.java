package com.servicelive.orderfulfillment.orderprep.scheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.calendar.IBusinessCalendar;
import com.servicelive.orderfulfillment.common.DateUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class CustomDateScheduleCalculator implements IScheduleCalculator {
    IBusinessCalendar businessCalendar;
    ICurrentDayResolver dayResolver;

    public CustomDateScheduleCalculator(IBusinessCalendar businessCalendar) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = new CurrentDayResolver();
    }

    public CustomDateScheduleCalculator(IBusinessCalendar businessCalendar, ICurrentDayResolver dayResolver) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = dayResolver;
    }

    public DateRange calculateServiceDates(DateRange dateRange, ServiceOrder serviceOrder) {
        setServiceStartDate(dateRange, serviceOrder);
        setServiceEndDate(dateRange, serviceOrder);
        return dateRange;
    }

    private void setServiceStartDate(DateRange dateRange, ServiceOrder serviceOrder) {
        Calendar today = dayResolver.getCurrentCalendarDate(serviceOrder.getServiceLocationTimeZone());

        TimeZone timeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
		Date startDate = businessCalendar.findClosestStartOfBusinessDayFromDate(today.getTime(), timeZone);

        if (DateUtil.isDayTheSame(startDate, today.getTime())) {
            startDate = businessCalendar.addBusinessDaysToDate(startDate, 1, timeZone);
        }

        dateRange.setStartDate(startDate);
    }

    private void setServiceEndDate(DateRange dateRange, ServiceOrder serviceOrder) {
        Integer leadTime = LeadTimeCalculator.findMinLeadTimeFromAddOnList(serviceOrder.getAddons());
        TimeZone timeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
        Date potentialEndDate = businessCalendar.addBusinessDaysToDate(dateRange.getStartDate(), leadTime, timeZone);
		Date endDate = businessCalendar.findClosestEndOfBusinessDayFromDate(potentialEndDate, timeZone);
        dateRange.setEndDate(endDate);
    }
}
