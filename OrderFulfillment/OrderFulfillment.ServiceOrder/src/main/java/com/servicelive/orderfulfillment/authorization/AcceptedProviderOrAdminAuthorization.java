package com.servicelive.orderfulfillment.authorization;

import org.apache.commons.lang.Validate;


import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class AcceptedProviderOrAdminAuthorization extends BaseServiceOrderAuthorization {

	protected void evaluateAuthorization(Identification id, ServiceOrder so)
			throws ServiceOrderException {
		Validate.notNull(so.getAcceptedProviderId(),"accepted provider id must not be null");
		
		if(!id.isAdmin()){
		Validate.notNull(id.getProviderId(),"provider id must not be null");
		
		if( !id.isProvider() || !isAcceptedProvider(id, so) ) {
			throw new ServiceOrderException("This request must be initiated by the accepted provider");
		}
		}
	}
	
	protected boolean isAcceptedProvider(Identification id, ServiceOrder so) {
		return( id.getProviderId().longValue() == so.getAcceptedProviderId().longValue());
	}
}
