package com.servicelive.orderfulfillment.signal;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Mustafa Motiwala
 *
 */
public class ResolveProblemSignal extends Signal {

    @Override
    protected void update(SOElement soe, ServiceOrder so) {
       so.setProblemDate(null);
    }
    
}
