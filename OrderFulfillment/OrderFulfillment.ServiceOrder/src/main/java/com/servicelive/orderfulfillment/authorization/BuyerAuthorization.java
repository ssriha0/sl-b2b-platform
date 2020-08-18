package com.servicelive.orderfulfillment.authorization;

import org.apache.commons.lang.Validate;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class BuyerAuthorization extends BaseServiceOrderAuthorization {

	protected void evaluateAuthorization(Identification id, ServiceOrder so) 
	throws ServiceOrderException{
		Validate.notNull(id.getBuyerId(),"buyer id must not be null");
		Validate.notNull(so.getBuyerId(),"buyer id must not be null");
		
		if( !id.isBuyer() || !isAuthorizedBuyer(id, so)) {
			throw new ServiceOrderException("This request must be initiated by buyer " + so.getBuyerId());
		}
	}
	
	protected boolean isAuthorizedBuyer(Identification id, ServiceOrder so) {
		return (id.getBuyerId().longValue() == so.getBuyerId().longValue()
				|| (id.getBuyerResourceId() != null && id.getBuyerResourceId().longValue() == so.getBuyerResourceId().longValue()));
	}	
}
