package com.servicelive.bus.event.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.servicelive.bus.EventBusException;
import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.event.order.ServiceOrderEvent;

public class ServiceLiveBusEventLogBO implements IServiceLiveBusEventLogBO {
	private static final Log log = LogFactory.getLog(ServiceLiveBusEventLogBO.class);

	private IServiceLiveBusEventLogDao serviceLiveBusEventLogDao;

	public ServiceLiveBusEventLogBO() {

	}

	public void setServiceLiveBusEventLogDao(IServiceLiveBusEventLogDao serviceLiveBusEventLogDao) {
		this.serviceLiveBusEventLogDao = serviceLiveBusEventLogDao;
	}

	public boolean isEventNewForClient(ServiceLiveEvent event, String clientId) throws EventBusException {
		final String methodName = "isEventNewForClient";
		log.info(String.format("entered %s", methodName));
				
		ServiceLiveBusEventLog eventLog = this.serviceLiveBusEventLogDao.findByEventId(event.getEventId());
		boolean eventIsNew = eventLog == null;

		log.info(String.format("exiting %s", methodName));
		return eventIsNew;
	}

	public boolean logEventForClient(ServiceLiveEvent event, String clientId) throws EventBusException {
		final String methodName = "logEventForClient";
		log.info(String.format("entered %s", methodName));
		String eventType = event.getHeaderValue(EventHeader.EVENT_TYPE);
		String serviceOrderId = null;
		String orderEventType = null;
		if (event instanceof ServiceOrderEvent) {
			serviceOrderId = event.getHeaderValue(EventHeader.SERVICE_ORDER_ID);
			orderEventType = event.getHeaderValue(ServiceOrderEvent.ORDER_EVENT);
		}
		boolean completedSuccessfully =
		this.serviceLiveBusEventLogDao.save(new ServiceLiveBusEventLog(0, event.getEventId(), clientId, eventType, serviceOrderId, orderEventType));
		
		log.info(String.format("exiting %s", methodName));
		return completedSuccessfully;
	}
}
