package com.servicelive.orderfulfillment.authorization;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class AdminAuthorization extends BaseServiceOrderAuthorization {

	protected void evaluateAuthorization(Identification id, ServiceOrder so) 
	throws ServiceOrderException{
		
		if( !id.isAdmin() ) {
			throw new ServiceOrderException("This request must be initiated by a SL administrator ");
		}
	}
	
}
