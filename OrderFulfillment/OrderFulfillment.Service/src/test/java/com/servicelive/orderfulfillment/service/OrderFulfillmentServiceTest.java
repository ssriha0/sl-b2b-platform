package com.servicelive.orderfulfillment.service;

import junit.framework.Assert;

import org.junit.Test;

import com.servicelive.orderfulfillment.ProcessingBO;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

import static org.mockito.Mockito.*;

public class OrderFulfillmentServiceTest{
	
	private OrderFulfillmentService service;
	private ProcessingBO processingBO;
	
	@Test
	public void testIsSignalAvailable(){
		service = new OrderFulfillmentService();
		processingBO = mock(ProcessingBO.class);
		service.setProcessingBO(processingBO);
		
		SignalType signalType = SignalType.ACCEPT_ORDER;
		String soId = "541-5430-5650-17";
		
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		response.setSoId(soId);
		response.setSignalAvailable(true);
		
		when(processingBO.isSignalAvailable(soId, signalType)).thenReturn(response);
		
		response = service.isSignalAvailable(soId, signalType);
		
		Assert.assertEquals(true, response.isSignalAvailable());
	}
}
