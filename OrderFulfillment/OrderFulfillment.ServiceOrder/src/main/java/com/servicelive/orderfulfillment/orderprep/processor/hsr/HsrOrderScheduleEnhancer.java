package com.servicelive.orderfulfillment.orderprep.processor.hsr;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderScheduleEnhancer;
import com.servicelive.orderfulfillment.orderprep.scheduling.CustomDateScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.DeliveryDateScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.IScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.NextDayScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.PromisedDateScheduleCalculator;

public class HsrOrderScheduleEnhancer extends AbstractOrderScheduleEnhancer {
    private int businessDayPad;

    @Override
    protected DateRange createServiceDateRange(ServiceOrder serviceOrder) {
    	DateRange dateRange = new DateRange();
        if(serviceOrder.isCreatedViaAPI()){
        	dateRange = createDateRangeFromSOScheduleWithTimeZone(serviceOrder);
        	calculateServiceDates(dateRange, serviceOrder);
        	return dateRange;
        }else{
        	TimeZone timeZone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
        	setServiceStartDate(dateRange, timeZone);
        	setServiceEndDate(dateRange, timeZone);
        	return dateRange;
        }
    }

    private void setServiceStartDate(DateRange dateRange, TimeZone timeZone) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(timeZone);
        cal.set(Calendar.HOUR_OF_DAY, 8); //set to 8:00 am
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date newStartDate = addDaysToDate(cal.getTime(), -1);
        //Date newStartDate = businessCalendar.addBusinessDaysToDate(new Date(), businessDayPad);
        dateRange.setStartDate(newStartDate);
    }

    private void setServiceEndDate(DateRange dateRange, TimeZone timeZone) {
        Date newEndDate = addDaysToDate(dateRange.getStartDate(), businessDayPad);

        dateRange.setEndDate(businessCalendar.findClosestEndOfBusinessDayFromDate(newEndDate, timeZone));
    }
    protected DateRange createDateRangeFromSOScheduleWithTimeZone(ServiceOrder serviceOrder) {
        Calendar calStart = serviceOrder.getServiceStartDateTimeCalendar();
        Calendar calEnd = serviceOrder.getServiceEndDateTimeCalendar();

        return new DateRange(calStart.getTime(), (calEnd != null) ? calEnd.getTime() : calStart.getTime());
    }
    
    protected void calculateServiceDates(DateRange dateRange, ServiceOrder serviceOrder ) {
        String serviceDateCalcMethod = serviceOrder.getCustomRefValue(SOCustomReference.CREF_DATE_CALC_METHOD);
        if (serviceDateCalcMethod != null) {
            IScheduleCalculator scheduleCalculator = null;
            if (serviceDateCalcMethod.equalsIgnoreCase("N")) {
                scheduleCalculator = new NextDayScheduleCalculator(businessCalendar);
            } else if (serviceDateCalcMethod.equalsIgnoreCase("P")) {
            	if (!checkServiceTimesInPast(serviceOrder.getSchedule(), serviceOrder.getServiceLocationTimeZone(), 
            				serviceOrder.getValidationHolder())) {
                scheduleCalculator = new PromisedDateScheduleCalculator(businessCalendar);
            	}
            } else if (serviceDateCalcMethod.equalsIgnoreCase("D")) {
            	if (!checkServiceTimesInPast(serviceOrder.getSchedule(), serviceOrder.getServiceLocationTimeZone(), 
        				serviceOrder.getValidationHolder())) {
                scheduleCalculator = new DeliveryDateScheduleCalculator(businessCalendar);
            	}
            } else if (serviceDateCalcMethod.equalsIgnoreCase("C")) {
                scheduleCalculator = new CustomDateScheduleCalculator(businessCalendar);
            }

            if (scheduleCalculator != null) {
                scheduleCalculator.calculateServiceDates(dateRange, serviceOrder);
            }
        }
    }
    private boolean checkServiceTimesInPast(SOSchedule serviceSchedule, String tzID, ValidationHolder validationHolder) {
        TimeZone serviceTimeZone = TimeZone.getTimeZone(tzID);
        Calendar calStart = TimeChangeUtil.getCalTimeFromParts(serviceSchedule.getServiceDate1(), serviceSchedule.getServiceTimeStart(), serviceTimeZone);

        Calendar today = new GregorianCalendar(serviceTimeZone);
        if (calStart.before(today)) {
            validationUtil.addWarnings(validationHolder, ProblemType.StartDatePast);
            return true;
            }
        return false;
            }
    
    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public void setBusinessDayPad(int businessDayPad) {
        this.businessDayPad = businessDayPad;
    }
}