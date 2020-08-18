package com.servicelive.bus.event.log;

import com.servicelive.bus.EventBusException;
import com.servicelive.bus.event.ServiceLiveEvent;

public interface IServiceLiveBusEventLogBO {
	public boolean isEventNewForClient(ServiceLiveEvent event, String clientId) throws EventBusException;
	public boolean logEventForClient(ServiceLiveEvent event, String clientId) throws EventBusException;
}
