package com.servicelive.orderfulfillment.decision;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class CancellationPenaltyCheck extends AbstractServiceOrderDecision {
    /*** not needed but takes away our warnings ***/
    private static final long serialVersionUID = 1L;

    public static final int DFLT_PENALTY_HOURS_THRESHOLD = 24;

    public enum CancellationType {
        CancelWithPenalty,
        CancelWithoutPenalty
    }

    public String decide(OpenExecution execution) {
        ServiceOrder serviceOrder = getServiceOrder(execution);
        if (isServiceDateTimePastThresholdInHours(serviceOrder, DFLT_PENALTY_HOURS_THRESHOLD)) {
            return CancellationType.CancelWithPenalty.name();
        }
        return CancellationType.CancelWithoutPenalty.name();
    }

    private boolean isServiceDateTimePastThresholdInHours(ServiceOrder serviceOrder, int penaltyThresholdInHours) {
        Calendar calSvcStart = serviceOrder.getServiceStartDateTimeCalendar();
        if (calSvcStart != null){
	        Calendar calTimeAtThreshold = getCalTimeAtThreshold(calSvcStart, penaltyThresholdInHours);
	        Calendar currCalTime = new GregorianCalendar(calSvcStart.getTimeZone());
	
	        return currCalTime.after(calTimeAtThreshold);
	    }
        return false;
    }

    private Calendar getCalTimeAtThreshold(Calendar calSvcStart, int penaltyThresholdInHours) {
        Calendar cal = new GregorianCalendar(calSvcStart.getTimeZone());
        cal.set(calSvcStart.get(Calendar.YEAR),
                calSvcStart.get(Calendar.MONTH),
                calSvcStart.get(Calendar.DAY_OF_MONTH),
                calSvcStart.get(Calendar.HOUR_OF_DAY),
                calSvcStart.get(Calendar.MINUTE),
                calSvcStart.get(Calendar.SECOND)
        );
        cal.add(Calendar.HOUR, -penaltyThresholdInHours);
        return cal;
    }
}