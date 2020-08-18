package com.newco.marketplace.relayservicesnotification.service;

import java.util.Map;

import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IRelayServiceNotification {

	public void sentNotificationRelayServices(String event,String soId) throws BusinessServiceException;
	public void sentNotificationRelayServices(String event,String soId, Map<String, String> param) throws BusinessServiceException;
	public boolean isRelayServicesNotificationNeeded(Integer buyerId,String currentServiceOrderId) throws BusinessServiceException;
	public Integer getBuyerId(String soId) throws BusinessServiceException;
}
