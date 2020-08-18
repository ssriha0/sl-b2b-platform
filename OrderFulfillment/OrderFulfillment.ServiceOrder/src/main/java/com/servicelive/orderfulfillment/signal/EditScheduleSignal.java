package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ConditionalOfferReason;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

public class EditScheduleSignal extends Signal {
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
        List<String> returnVal = new ArrayList<String>();
        if (!(soe instanceof SOSchedule)){
			returnVal.add("Expected SOSchedule for editing schedule but found some other SOElement");
		}
        SOSchedule source = (SOSchedule) soe;
        returnVal.addAll(source.validate());
        return returnVal;
    }

    @Override
	protected void update(SOElement soe, ServiceOrder so){
    	SOSchedule source = (SOSchedule) soe;
    	//changing schedule of a service order
    	so.setSchedule(source);
    	//check if there are routed providers and they have reschedule conditional offers - clear out conditional offers
		for(RoutedProvider rp : so.getRoutedResources()){
			if (rp.getProviderResponse() == ProviderResponseType.CONDITIONAL_OFFER
					&& (rp.getProviderRespReasonId() == ConditionalOfferReason.RESCHEDULE_SERVICE_DATE.getId()
							|| rp.getProviderRespReasonId() == ConditionalOfferReason.SERVICE_DATE_AND_SPEND_LIMIT.getId())){
				rp.removeConditionalOffer();
				serviceOrderDao.update(rp);
			}
		}
    	serviceOrderDao.update(so);
    }

}
