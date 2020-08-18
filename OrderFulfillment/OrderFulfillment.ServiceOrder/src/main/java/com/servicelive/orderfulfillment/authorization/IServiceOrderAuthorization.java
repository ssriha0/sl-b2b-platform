package com.servicelive.orderfulfillment.authorization;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public interface IServiceOrderAuthorization {

	public void authorize( Identification id, ServiceOrder so ) throws ServiceOrderException;
}
