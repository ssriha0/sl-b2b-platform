package com.servicelive.orderfulfillment.group.signal;

import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

public class WidthdrawGroupConditionalOfferSignal extends GroupSignal {

	protected void update(SOElement soe, SOGroup soGroup){
        RoutedProvider source = (RoutedProvider)soe;
		
		GroupRoutedProvider target = soGroup.getRoutedProvider(source.getProviderResourceId());
		
		target.setProviderResponse(ProviderResponseType.WITHDRAW_CONDITIONAL_OFFER);
        target.setProviderRespReasonId(null);
        target.setSchedule(null);
        target.setOfferExpirationDate(null);
        
        serviceOrderDao.update(target);
	}
}
