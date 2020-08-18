package com.servicelive.orderfulfillment.orderprep.scheduling;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.calendar.IBusinessCalendar;
import com.servicelive.orderfulfillment.common.DateUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class DeliveryDateScheduleCalculator implements IScheduleCalculator {
    IBusinessCalendar businessCalendar;
    ICurrentDayResolver dayResolver;

    public DeliveryDateScheduleCalculator(IBusinessCalendar businessCalendar) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = new CurrentDayResolver();
    }

    public DeliveryDateScheduleCalculator(IBusinessCalendar businessCalendar, ICurrentDayResolver dayResolver) {
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
		Calendar potentialStartCal = new GregorianCalendar(timeZone);
        potentialStartCal.setTime(dateRange.getStartDate());

        /*if (DateUtil.isDayTheSame(potentialStartCal.getTime(), today.getTime())) {
            potentialStartCal.add(Calendar.HOUR, 2);
        }*/

        addOneBusinessDayToCalendarDatePreservingTime(potentialStartCal, timeZone);

        DateUtil.roundCalendarDateToNearestQuarterHour(potentialStartCal);
        Date potentialStartDate = potentialStartCal.getTime();
        Date potentialBusinessStartDate = businessCalendar.findClosestStartOfBusinessDayFromDate(potentialStartCal.getTime(), timeZone);

        Date startDate;
        if (DateUtil.isDayTheSame(potentialStartDate, potentialBusinessStartDate)) {
            if (potentialStartDate.getTime() < potentialBusinessStartDate.getTime()) {
                startDate = potentialBusinessStartDate;
            } else {
                startDate = potentialStartDate;
            }

        } else {
            startDate = potentialBusinessStartDate;
        }

        dateRange.setStartDate(startDate);
    }

    private void addOneBusinessDayToCalendarDatePreservingTime(Calendar calDate, TimeZone timeZone) {
        int origHourOfDay = calDate.get(Calendar.HOUR_OF_DAY);
        int origMinuteOfHour = calDate.get(Calendar.MINUTE);
        calDate.setTime(businessCalendar.addBusinessDaysToDate(calDate.getTime(), 1, timeZone));
        calDate.set(Calendar.HOUR_OF_DAY, origHourOfDay);
        calDate.set(Calendar.MINUTE, origMinuteOfHour);
    }

    private void setServiceEndDate(DateRange dateRange, ServiceOrder serviceOrder) {
        Integer leadTime = LeadTimeCalculator.findMinLeadTimeFromAddOnList(serviceOrder.getAddons());
        TimeZone timeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
        Date potentialEndDate = businessCalendar.addBusinessDaysToDate(dateRange.getStartDate(), leadTime, timeZone);
		Date endDate = businessCalendar.findClosestEndOfBusinessDayFromDate(potentialEndDate, timeZone);
        dateRange.setEndDate(endDate);
    }
}
