/**
 * 
 */
package com.servicelive.orderfulfillment.signal;

import java.util.List;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * @author Mustafa Motiwala
 *
 */
public class AcceptRescheduleSignal extends RescheduleSignal {

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = super.validate(soe, soTarget);
        if(returnVal.isEmpty()){
            //There were no validation errors reported by parent.
            //Continue with specialized validation.
            SOLogging log = getServiceOrderDao().getRescheduleRequestLog(soTarget);
            switch(SignalType.valueOf(getName())){
            case BUYER_ACCEPT_RESCHEDULE:
                if(log.getRoleId()!=1)
                    returnVal.add("No rescheduling request from Provider.");
                break;
            case PROVIDER_ACCEPT_RESCHEDULE:
                if(log.getRoleId()!=5 && log.getRoleId()!=3)
                    returnVal.add("No rescheduling request from Buyer.");
                break;
            }
        }
        return returnVal;
    }

    /**
     * Template method. Overridden to provide specific functionality to
     * Accept the Rescheduling request.
     * @param requestedSchedule
     * @param so
     */
    @Override
    protected void reschedule(SOSchedule requestedSchedule, ServiceOrder so) {
        SOSchedule reschedule = so.getReschedule();
        so.setReschedule(null);
        so.setOldSchedule(so.getSchedule());
        so.setSchedule(reschedule);
        
        // R12.0 Sprint 2 Updating current appointment date and time in so_trip when buyer or provider accept a reschedule request.
        serviceOrderDao.updateSOTripForReschedule(reschedule, so.getSoId());
        //SL-20044 : clearing sub-status while accepting reschedule request
        so.setWfSubStatusId(null);
        
        //SL-19291 Deleting buyer preferred reschedule details when provider accepts reschedule request.
        String schedule=getName();
        if((OFConstants.PROVIDER_ACCEPT_RESCHEDULE).equalsIgnoreCase(schedule)){
        	so.getSOWorkflowControls().setBuyerSchedule(null);
        }
    }
}
