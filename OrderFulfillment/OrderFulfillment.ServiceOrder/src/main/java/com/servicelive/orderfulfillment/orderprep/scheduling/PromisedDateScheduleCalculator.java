package com.servicelive.orderfulfillment.orderprep.scheduling;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.calendar.IBusinessCalendar;
import com.servicelive.orderfulfillment.common.DateUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class PromisedDateScheduleCalculator implements IScheduleCalculator {
    IBusinessCalendar businessCalendar;
    ICurrentDayResolver dayResolver;

    public PromisedDateScheduleCalculator(IBusinessCalendar businessCalendar) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = new CurrentDayResolver();
    }

    public PromisedDateScheduleCalculator(IBusinessCalendar businessCalendar, ICurrentDayResolver dayResolver) {
        this.businessCalendar = businessCalendar;
        this.dayResolver = dayResolver;
    }

    public DateRange calculateServiceDates(DateRange dateRange, ServiceOrder serviceOrder) {
        setServiceStartDate(dateRange, serviceOrder);
        return dateRange;
    }

    private void setServiceStartDate(DateRange dateRange, ServiceOrder serviceOrder) {
        Calendar today = dayResolver.getCurrentCalendarDate(serviceOrder.getServiceLocationTimeZone());
        TimeZone timeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
		Calendar potentialStartCal = new GregorianCalendar(timeZone);
        potentialStartCal.setTime(dateRange.getStartDate());
        if (DateUtil.isDayTheSame(potentialStartCal.getTime(), today.getTime())) {
            //potentialStartCal.add(Calendar.HOUR, 2);
            DateUtil.roundCalendarDateToNearestQuarterHour(potentialStartCal);
            Date potentialStartDate = potentialStartCal.getTime();
            Date potentialBusinessStartDate = businessCalendar.findClosestStartOfBusinessDayFromDate(potentialStartCal.getTime(), 
            		timeZone);

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

            // For PromisedDate calculations, start and end date should always be the same.  If they are not the same, change
            // the year, month and day of the end date to match the start date.
            Calendar potentialEndCal = new GregorianCalendar(timeZone);
            potentialEndCal.setTime(dateRange.getEndDate());
            if (!DateUtil.isDayTheSame(startDate, potentialEndCal.getTime(), timeZone)) {
            	Calendar startDateCal = new GregorianCalendar(timeZone);
            	startDateCal.setTime(startDate);
            	potentialEndCal.set(startDateCal.get(Calendar.YEAR), startDateCal.get(Calendar.MONTH), startDateCal.get(Calendar.DAY_OF_MONTH));
            	dateRange.setEndDate(potentialEndCal.getTime());
            }

            dateRange.setStartDate(startDate);
        }
    }
}
