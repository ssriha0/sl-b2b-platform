/**
 * 
 */
package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.bus.ServiceLiveEventListener;
import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author sahmed
 *
 */
public class SearsRIOrderClosedEventHandler extends AbstractServiceOrderEventHandler  
	implements ServiceLiveEventListener {
	
	private static final Logger logger = Logger.getLogger(SearsRIOrderClosedEventHandler.class);
	
	public SearsRIOrderClosedEventHandler() {
		super();
	}
	public SearsRIOrderClosedEventHandler(ConfigTree configTree) throws ConfigurationException {
		super(configTree);
	}

	public Message processMessage(Message message) throws ActionProcessingException {
		final String methodName = "processMessage";
		logger.info(String.format("entered %s", methodName));

		try {
			Object messagePayload = message.getBody().get();
			ServiceOrderEvent serviceOrderEvent = unmarshalServiceOrderEvent(messagePayload);
			this.doHandleEvent(serviceOrderEvent);
		}
		catch (Exception e) {
			throw new ActionProcessingException("Error occurred while processing message.", e);
		}

		logger.info(String.format("exiting %s", methodName));
		return message;
	}

	public void handleEvent(ServiceLiveEvent event) {
		final String methodName = "processMessage";
		logger.info(String.format("entered %s", methodName));

		try {
			this.doHandleEvent(event);
		}
		catch (Exception e) {
			this.exceptionHandler(null, e);
		}

		logger.info(String.format("exiting %s", methodName));
	}
	
	private void doHandleEvent(ServiceLiveEvent event) {
		ServiceOrderEvent serviceOrderEvent = (ServiceOrderEvent)event;
		ServiceOrder serviceOrder = (ServiceOrder)serviceOrderEvent.getEventSource();
		super.logUnmarshalledServiceOrder(serviceOrder);
		String orderEventName = serviceOrderEvent.getEventHeader().get(ServiceOrderEvent.ORDER_EVENT);
		if(OrderEventType.CLOSED.name().equals(orderEventName)) {
			this.getIntegrationServiceCoordinator().recordSearsRiOrderClosedEventAndRelatedData(serviceOrderEvent, serviceOrder);
		}
		else if(OrderEventType.CANCELLED.name().equals(orderEventName)||OrderEventType.VOIDED.name().equals(orderEventName)||
				OrderEventType.DELETED.name().equals(orderEventName)){
			this.getIntegrationServiceCoordinator().recordSearsRiOrderCancelEventAndRelatedData(serviceOrderEvent, serviceOrder);
		}
	}
	
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.RI_OUTBOUND.getId();
	}
	
	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.RI_OUTBOUND.name();
	}	
}
