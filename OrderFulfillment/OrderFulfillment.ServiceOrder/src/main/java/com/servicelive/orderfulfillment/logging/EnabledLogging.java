package com.servicelive.orderfulfillment.logging;

import java.util.HashMap;

import org.apache.commons.lang.Validate;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public abstract class EnabledLogging implements IServiceOrderLogging {

	public EnabledLogging() {
		super();
	}

	public void logServiceOrder(ServiceOrder so, HashMap<String,Object> dataMap) throws ServiceOrderException 
	{
		Validate.notNull(so,"so must not be null");
		
		SOLogging log = createSOLogging(so, dataMap);
		so.addLogging(log);
	}
	
	protected abstract SOLogging createSOLogging(ServiceOrder so, HashMap<String,Object> dataMap)
	throws ServiceOrderException;

}