package com.servicelive.orderfulfillment.authorization;

import org.apache.commons.lang.Validate;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class RoutedProviderAuthorization extends BaseServiceOrderAuthorization {

	protected void evaluateAuthorization(Identification id, ServiceOrder so)
			throws ServiceOrderException {
		Validate.notNull(id.getProviderResourceId(),"provider resource id must not be null");
		Validate.notNull(so.getRoutedResources(),"routed resources must not be null");
		
		if( !id.isProvider() || !isRoutedProvider(id, so) ) {
			throw new ServiceOrderException("This request must be initiated by a routed provider");
		}
	}
	
	protected boolean isRoutedProvider(Identification id, ServiceOrder so) {
		for( RoutedProvider p : so.getRoutedResources()) {
			if( p.getVendorId().longValue() == id.getProviderId().longValue()) {
				return true;
			}
		}
		return false;
	}

}
