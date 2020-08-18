package com.newco.marketplace.webservices.util;

import java.util.StringTokenizer;

import com.newco.marketplace.webservices.dto.serviceorder.EventRequest;

public class StringToWSBeanConverter {

	/*
	 * The input string will be of this format:
     * buyerId|eventReasonCode|eventTypeID|password|resourceID|serviceOrderID|userId
	 */	
	public static EventRequest convertToEventRequest(String request) {
		StringTokenizer tokenizer = new StringTokenizer(request, "|");

		EventRequest eventRequest = new EventRequest();
		eventRequest.setBuyerId(Integer.valueOf(tokenizer.nextToken()));
		eventRequest.setEventReasonCode(Long.parseLong(tokenizer.nextToken()));
		eventRequest.setEventTypeID(Long.parseLong(tokenizer.nextToken()));
		eventRequest.setPassword(tokenizer.nextToken());
		eventRequest.setResourceID(tokenizer.nextToken());
		eventRequest.setServiceOrderID(tokenizer.nextToken());
		eventRequest.setUserId(tokenizer.nextToken());		
		
		return eventRequest;
	}
}
