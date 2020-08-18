package com.newco.marketplace.webservices.dispatcher.so.order;

import java.util.Map;

public class OrderDispatchRequestorLocator {
	
	private Map<String, IOrderDispatchRequestor> dispatcherMap;
	
	
	public IOrderDispatchRequestor getDispatcher(String clientId) {
		
		return dispatcherMap.get(clientId);
	}


	public Map<String, IOrderDispatchRequestor> getDispatcherMap() {
		return dispatcherMap;
	}


	public void setDispatcherMap(Map<String, IOrderDispatchRequestor> dispatcherMap) {
		this.dispatcherMap = dispatcherMap;
	}
	

}
