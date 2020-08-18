package com.servicelive.orderfulfillment.authorization;

import org.apache.commons.lang.Validate;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public abstract class BaseServiceOrderAuthorization implements IServiceOrderAuthorization {

	public void authorize(Identification id, ServiceOrder so) throws ServiceOrderException{
		Validate.notNull(id,"id must not be null");
		Validate.notNull(so,"so must not be null");
		this.evaluateAuthorization(id, so);
	}
	
	protected abstract void evaluateAuthorization( Identification id, ServiceOrder so ) throws ServiceOrderException;

}
