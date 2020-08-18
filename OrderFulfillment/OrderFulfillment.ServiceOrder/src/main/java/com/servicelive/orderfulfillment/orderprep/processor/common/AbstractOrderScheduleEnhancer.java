package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.calendar.IBusinessCalendar;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;


public abstract class AbstractOrderScheduleEnhancer extends AbstractOrderEnhancer {
    protected IBusinessCalendar businessCalendar;
    private int businessDayPad;
    private QuickLookupCollection quickLookupCollection;

	public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
		String tzID = getServiceTimeZoneId(serviceOrder);
		serviceOrder.setServiceLocationTimeZone(tzID);

		if (null != serviceOrder.getSchedule()) {
			DateRange dateRange = createServiceDateRange(serviceOrder);
			if (dateRange != null) {
				setServiceTimeInfoInOrder(serviceOrder, dateRange, tzID);
				setServiceScheduleType(serviceOrder);
			}
		} else if (null != serviceOrder.getSoServiceDatetimeSlot()) {
			// new flow
			// TODO : implement business rules for multiple slots
		} else {
			logger.error("Not able to apply Schedule Enhancer on Service Order");
		}
	}

    protected abstract DateRange createServiceDateRange(ServiceOrder serviceOrder);

    private String getServiceTimeZoneId(ServiceOrder serviceOrder) {
        String tzID = null;

        if (serviceOrder.getServiceLocation() != null
                && StringUtils.isNotBlank(serviceOrder.getServiceLocation().getZip())) {
            tzID = quickLookupCollection.getTimeZoneLookup().getTimeZone(serviceOrder.getServiceLocation().getZip());
        }else{
            validationUtil.addErrors(serviceOrder.getValidationHolder(), ProblemType.CannotFindTimeZone);
        }

        TimeZone serviceTimeZone = (tzID==null) ? TimeZone.getTimeZone("GMT") : TimeZone.getTimeZone(tzID);
        if (serviceTimeZone.equals(TimeZone.getTimeZone("GMT"))) {
            tzID = "GMT";
        }

        return tzID;
    }

    private void setServiceTimeInfoInOrder(ServiceOrder serviceOrder, DateRange dateRange, String tzID) {
        TimeZone serviceTimeZone = TimeZone.getTimeZone(tzID);
        int offsetHours = getHoursOffsetFromGMT(serviceTimeZone, dateRange.getStartDate());
        serviceOrder.setServiceDateTimezoneOffset(offsetHours);
        serviceOrder.setServiceLocationTimeZone(tzID);

        SOSchedule schedule = serviceOrder.getSchedule();
        Calendar startDateTime = new GregorianCalendar();
        startDateTime.setTime(dateRange.getStartDate());
        schedule.setServiceDate1(TimeChangeUtil.getDate(startDateTime, serviceTimeZone));
        schedule.setServiceTimeStart(TimeChangeUtil.getTimeString(startDateTime, serviceTimeZone));
        
        Calendar endDateTime = new GregorianCalendar();
        endDateTime.setTime(dateRange.getEndDate());
        schedule.setServiceDate2(TimeChangeUtil.getDate(endDateTime, serviceTimeZone));
        schedule.setServiceTimeEnd(TimeChangeUtil.getTimeString(endDateTime, serviceTimeZone));
    }

    protected void setServiceScheduleType(ServiceOrder serviceOrder) {
    	// SL-17196 : If the schedule type is already set in the request, use it.
    	SOScheduleType scheduleType = serviceOrder.getSchedule().getServiceDateTypeId();
    	if(null!= scheduleType ){
    		 serviceOrder.getSchedule().setServiceDateTypeId(scheduleType);
    	}else{
    		 serviceOrder.getSchedule().setServiceDateTypeId(SOScheduleType.DATERANGE);
    	}
       
    }

    private int getHoursOffsetFromGMT(TimeZone tz, Date serviceDate) {
        return tz.getOffset(serviceDate.getTime()) / 3600000;
    }
    
    protected DateRange createDateRangeFromBusinessDayPad(ServiceOrder serviceOrder){
        DateRange dateRange = new DateRange();
        setServiceStartDate(dateRange, TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone()));
        setServiceEndDate(dateRange, TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone()));
        return dateRange;
    }
    
    private void setServiceStartDate(DateRange dateRange, TimeZone timeZone) {
        Date newStartDate = businessCalendar.addBusinessDaysToDate(new Date(), businessDayPad, timeZone);
        dateRange.setStartDate(newStartDate);
    }

    private void setServiceEndDate(DateRange dateRange, TimeZone timeZone) {
        Date newEndDate = addDaysToDate(dateRange.getStartDate(), businessDayPad);
        newEndDate = businessCalendar.findClosestEndOfBusinessDayFromDate(newEndDate, timeZone);
        dateRange.setEndDate(newEndDate);
    }

    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    protected DateRange createDateRangeFromSOScheduleWithTimeZone(ServiceOrder serviceOrder) {
        Calendar calStart = serviceOrder.getServiceStartDateTimeCalendar();
        Calendar calEnd = serviceOrder.getServiceEndDateTimeCalendar();

        return new DateRange(calStart.getTime(), (calEnd != null) ? calEnd.getTime() : calStart.getTime());
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setBusinessCalendar(IBusinessCalendar businessCalendar) {
        this.businessCalendar = businessCalendar;
    }

    public void setBusinessDayPad(int businessDayPad) {
        this.businessDayPad = businessDayPad;
    }

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }
}
