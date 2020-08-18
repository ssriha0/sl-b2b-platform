package com.servicelive.orderfulfillment.group.signal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/05/22
 * New signal for WidthdrawGroupConditionalOffer, accepting a resource list
 */
public class WidthdrawGroupConditionalOfferResourceListSignal extends GroupSignal {

	private static final Logger LOGGER = Logger.getLogger(WidthdrawGroupConditionalOfferResourceListSignal.class);
	
	protected void update(final SOElement soe, final SOGroup soGroup){
		LOGGER.info("inside WidthdrawGroupConditionalOfferResourceListSignal.update()");
		List<SOElement> soeList=new ArrayList<SOElement>();
		if(soe instanceof RoutedProvider){
			soeList.add(soe);
		}
		else{
			final SOElementCollection soeCollection=(SOElementCollection)soe;
			soeList=soeCollection.getElements();
		}
		for(SOElement soElement: soeList){
			final RoutedProvider source = (RoutedProvider)soElement;
			final GroupRoutedProvider target = soGroup.getRoutedProvider(source.getProviderResourceId());
			target.setProviderResponse(ProviderResponseType.WITHDRAW_CONDITIONAL_OFFER);
			target.setProviderRespReasonId(null);
			target.setSchedule(null);
			target.setOfferExpirationDate(null);

			serviceOrderDao.update(target);

		}
		LOGGER.info("leaving WidthdrawGroupConditionalOfferResourceListSignal.update()");
	}
}
