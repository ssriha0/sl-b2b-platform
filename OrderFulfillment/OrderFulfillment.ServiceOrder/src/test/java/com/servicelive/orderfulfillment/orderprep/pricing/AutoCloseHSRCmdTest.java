package com.servicelive.orderfulfillment.orderprep.pricing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class AutoCloseHSRCmdTest{
	ServiceOrderDao serviceOrderDao;;
	
	 @Test
	public void execute() {
		
		 serviceOrderDao = mock(ServiceOrderDao.class);
		 
		Integer timeInterval = 60;
	    when(serviceOrderDao.getInhomeAutoCloseTimeInterval(OrderfulfillmentConstants.TIME_INTERVAL)).thenReturn(timeInterval);

		 
	 }


}

