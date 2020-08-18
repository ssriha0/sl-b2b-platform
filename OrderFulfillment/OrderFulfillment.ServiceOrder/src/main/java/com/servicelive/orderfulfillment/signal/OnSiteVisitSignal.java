package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class OnSiteVisitSignal extends Signal {
	
	private static  final Logger logger = Logger.getLogger(OnSiteVisitSignal.class);

	@Override
	protected void update(SOElement soe, ServiceOrder so){
		if(!(soe instanceof SOOnSiteVisit)){
			throw new ServiceOrderException("Expected SOOnSiteVisit instead got " + soe.getClass().getName());
		}
		
		SOOnSiteVisit onSiteVisit = (SOOnSiteVisit) soe;
		logger.info("OnSiteVisitSignal arrival date :"+onSiteVisit.getArrivalDate());
		onSiteVisit.setServiceOrder(so);
		
		serviceOrderDao.save(onSiteVisit);
	}

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if(!(soe instanceof SOOnSiteVisit)){
			returnVal.add("Expected SOOnSiteVisit instead got " + soe.getTypeName());
		}
        return returnVal;
    }
}
