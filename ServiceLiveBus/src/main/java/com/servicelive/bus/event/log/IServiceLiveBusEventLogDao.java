package com.servicelive.bus.event.log;

import com.servicelive.bus.EventBusException;

public interface IServiceLiveBusEventLogDao {
	public boolean save(ServiceLiveBusEventLog serviceLiveBusEventLog) throws EventBusException;
	public ServiceLiveBusEventLog getById(long serviceLiveBusEventLogId) throws EventBusException;
	public ServiceLiveBusEventLog findByEventAndClientIds(String eventId, String clientId) throws EventBusException;
	public ServiceLiveBusEventLog findByEventId(String eventId) throws EventBusException;
}
