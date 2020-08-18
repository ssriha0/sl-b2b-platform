package com.servicelive.orderfulfillment.logging;

import java.util.HashMap;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class DisabledLogging implements IServiceOrderLogging {

	public DisabledLogging() {
		super();
	}

	public void logServiceOrder(ServiceOrder so, HashMap<String,Object> dataMap) throws ServiceOrderException {
		// do nothing		
	}

}
