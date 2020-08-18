package com.servicelive.orderfulfillment.orderprep.processor.ri;

import com.servicelive.orderfulfillment.calendar.DateRange;
import com.servicelive.orderfulfillment.calendar.IBusinessCalendar;
import com.servicelive.orderfulfillment.common.DateUtil;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ValidationHolder;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.util.TimeChangeUtil;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderScheduleEnhancer;
import com.servicelive.orderfulfillment.orderprep.scheduling.CurrentDayResolver;
import com.servicelive.orderfulfillment.orderprep.scheduling.CustomDateScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.DeliveryDateScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.ICurrentDayResolver;
import com.servicelive.orderfulfillment.orderprep.scheduling.IScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.NextDayScheduleCalculator;
import com.servicelive.orderfulfillment.orderprep.scheduling.PromisedDateScheduleCalculator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.log4j.Logger;

public class OrderScheduleEnhancer extends AbstractOrderScheduleEnhancer {
	protected final Logger logger = Logger.getLogger(getClass());
	ICurrentDayResolver dayResolver = new CurrentDayResolver();
	
	/* public OrderScheduleEnhancer(ICurrentDayResolver dayResolver) {
	        this.dayResolver = new CurrentDayResolver();
	    }*/
	 
    @Override
    protected DateRange createServiceDateRange(ServiceOrder serviceOrder) {
        DateRange dateRange = createDateRangeFromSOScheduleWithTimeZone(serviceOrder);
        calculateServiceDates(dateRange, serviceOrder);
        
        //Following code will be executed only for Sears RI buyer(buyer id:1000). This code will update the start time as 8:00 AM
        //and end time as 5:00 PM for all orders other than same day order.
        if(null!=serviceOrder.getBuyerId() && serviceOrder.getBuyerId().equals(1000L) && !(serviceOrder.isUpdate())){
        	if(null!=dateRange){        		
            	logger.info("Date range inside OrderScheduleEnhancer for RI, start: " +dateRange.getStartDate()+ " and end date: " +dateRange.getEndDate());
            	Calendar today = dayResolver.getCurrentCalendarDate(serviceOrder.getServiceLocationTimeZone());
            	if (!DateUtil.isDayTheSame(dateRange.getStartDate(), today.getTime())) {
            		String serviceDateCalcMethod = serviceOrder.getCustomRefValue(SOCustomReference.CREF_DATE_CALC_METHOD);
            		if(null!=serviceDateCalcMethod && serviceDateCalcMethod.equalsIgnoreCase("P")){
            			Date newStartDate=new Date(dateRange.getStartDate().getTime());
                		Date newEndDate= new Date(dateRange.getStartDate().getTime());            		
                		int expectedStartTime=17;
                		int actualStartTime=dateRange.getStartDate().getHours();
                		int correctedStartTime=8+(actualStartTime-expectedStartTime);
                		int correctedEndTime=17+(actualStartTime-expectedStartTime);
                		newStartDate.setHours(correctedStartTime);
                		newEndDate.setHours(correctedEndTime);
                		//Assuming end date will be same as start date in CST/CDT updating end date as same as start date            		
                		dateRange.setStartDate(newStartDate);
                		dateRange.setEndDate(newEndDate);
                		logger.info("New date range inside OrderScheduleEnhancer for RI, start: " +dateRange.getStartDate()+ " and end date: " +dateRange.getEndDate());
            		}            		
            	}
            }        	
        }
        return dateRange;
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
}
