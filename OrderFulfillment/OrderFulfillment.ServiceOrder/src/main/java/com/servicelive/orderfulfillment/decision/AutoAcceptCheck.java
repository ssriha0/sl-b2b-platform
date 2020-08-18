package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;

/**
 * User: Mustafa Motiwala Date: Mar 18, 2010 Time: 11:57:35 AM
 */
public class AutoAcceptCheck extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1685517934179070613L;
	private static long ONE_HOUR = 60 * 60 * 1000L;
	public String decide(OpenExecution openExecution) {
		ServiceOrder so = getServiceOrder(openExecution);
		SOSchedule reschedule = so.getReschedule();
		SOSchedule schedule = so.getSchedule();
		if (so.getSOWorkflowControls() == null || so.getSOWorkflowControls().getAutoAcceptRescheduleRequestIndicator()==null
				||so.getWfSubStatusId() == null) {//SL-19604 
			//reason code condition removed
			//SL-18893 
			//Clearing the SubStatus when Reschedule Request is placed by provider.
	        so.setWfSubStatusId(null);
			return "noaction";
		} else {
			//SL-18893 Clearing the SubStatus while auto accepting Reschedule Request.
	        so.setWfSubStatusId(null);
			SOWorkflowControls soWorkflowControls = so.getSOWorkflowControls();
			boolean autoAccept = soWorkflowControls
					.getAutoAcceptRescheduleRequestIndicator();
			if (autoAccept) {
				int count = soWorkflowControls.getAutoAcceptRescheduleRequestCount();
				int days = soWorkflowControls.getAutoAcceptRescheduleRequestDays();
				if (count > 0) {
					Date reschedDate1 = reschedule.getServiceDate1();
					Date reschedDate2 = reschedule.getServiceDate2();
					Date serviceDate1 = schedule.getServiceDate1();
					Date serviceDate2 = schedule.getServiceDate2();
					long reschedDays1 = 0L;
					long reschedDays2 = 0L;
					if(reschedDate2!=null && serviceDate2==null){
						reschedDays2 = getDaysBetweenDates(serviceDate1,reschedDate2);
					}
					reschedDays1 = getDaysBetweenDates(serviceDate1,reschedDate1);
					if (reschedDate2 != null && null != serviceDate2) {
						reschedDays2 = getDaysBetweenDates(serviceDate2,reschedDate2);
						
					}
					if ((reschedDays1 <= days) && reschedDays2 <= days ) {
						return "accept";
					} else {
						return "reject";
					}
					
				} else {

					return "reject";
				}
			} else {
				return "noaction";
			}

		}

	}
	public static long getDaysBetweenDates(Date startDate, Date endDate) {

		return ((endDate.getTime() - startDate.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}
}
