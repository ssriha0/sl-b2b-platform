package com.newco.webservice.test;

import com.newco.marketplace.webservices.dto.serviceorder.EventRequest;
import com.newco.marketplace.webservices.util.StringToWSBeanConverter;

public class StringToWSBeanConverterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// buyerId|eventReasonCode|eventTypeID|password|resourceID|serviceOrderID|userId
		//                0|2050|127-8859-6648-18|2050|ivr|ivr|1
		String input = "123|1|2|password|resourceID|serviceOrderID|userId";
		EventRequest eventRequest = StringToWSBeanConverter.convertToEventRequest(input);
		System.out.println("Buyer ID: " + eventRequest.getBuyerId());
		System.out.println("Event Reason Code: " + eventRequest.getEventReasonCode());
		System.out.println("Event Type ID: " + eventRequest.getEventTypeID());
		System.out.println("Resource ID: " + eventRequest.getResourceID());
		System.out.println("Service Order ID: " + eventRequest.getServiceOrderID());
		System.out.println("User ID: " + eventRequest.getUserId());	 
	}

}
