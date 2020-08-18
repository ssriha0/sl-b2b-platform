/**
 * 
 */
package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Mustafa Motiwala
 *
 */
public abstract class RescheduleSignal extends Signal {

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if(!(soe instanceof SOSchedule)){
            returnVal.add("Invalid data passed to request. Expected SOSchedule received: " + soe.getTypeName());
        }
        return returnVal;
    }

    @Override
    protected void update(SOElement soe, ServiceOrder so) {
        reschedule((SOSchedule)soe, so);
        getServiceOrderDao().update(so);
    }
    
    abstract protected void reschedule(SOSchedule requestedSchedule, ServiceOrder so);
}
