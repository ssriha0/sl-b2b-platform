/**
 * 
 */
package com.servicelive.orderfulfillment.signal;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Mustafa Motiwala
 *
 */
public class RejectRescheduleSignal extends RescheduleSignal {

    @Override
    protected void reschedule(SOSchedule requestedSchedule, ServiceOrder so) {
        so.setReschedule(null);
        so.setWfSubStatusId(null);
        
        //SL-19291 Deleting buyer preferred reschedule date details from so_workflow_controls when buyer cancels reschedule request.
        String schedule=getName();
        
        //SL-19050
        //Code added to change substatus of SO to 'Buyer Rechedule Required' when provider rejects a reschedule request
        if((OFConstants.PROVIDER_REJECT_RESCHEDULE).equalsIgnoreCase(schedule)){
        	  so.setWfSubStatusId(OFConstants.BUYER_RESCHEDULE_REQUIRED);
        }
        
        if((OFConstants.BUYER_CANCEL_RESCHEDULE).equalsIgnoreCase(schedule)){
        	so.getSOWorkflowControls().setBuyerSchedule(null);
        }
    }

}
