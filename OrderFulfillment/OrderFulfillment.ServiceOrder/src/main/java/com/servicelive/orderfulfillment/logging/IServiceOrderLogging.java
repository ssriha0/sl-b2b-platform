package com.servicelive.orderfulfillment.logging;

import java.util.HashMap;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public interface IServiceOrderLogging {

	public void logServiceOrder( ServiceOrder so, HashMap<String,Object> dataMap ) throws ServiceOrderException;
	
}
