package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOScheduleHistory;
import com.servicelive.orderfulfillment.domain.SOScheduleStatus;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

/**
 * @author Infosys
 *
 */
public class UnMatchedPostSignal extends Signal {

    /**
     * @param soe
     * @param so
     */
    protected void update(SOElement soe, ServiceOrder so)  {
        logger.debug("Begin update Method of UnMatchedPostSignal:");
        
        logger.debug("End update Method of UnMatchedPostSignal.");
    }   
}
