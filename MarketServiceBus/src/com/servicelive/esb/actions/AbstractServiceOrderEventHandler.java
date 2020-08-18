/**
 * 
 */
package com.servicelive.esb.actions;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;

import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author sahmed
 *
 */
public abstract class AbstractServiceOrderEventHandler extends AbstractIntegrationSpringAction {
	private static final Logger logger = Logger.getLogger(AbstractServiceOrderEventHandler.class);
	public AbstractServiceOrderEventHandler() { super(); }
	public AbstractServiceOrderEventHandler(ConfigTree configTree) { super(configTree); }
	
	protected ServiceOrderEvent unmarshalServiceOrderEvent(Object xmlSerializedServiceOrderEvent) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("attempting to unmarshall service order event XML: %s", xmlSerializedServiceOrderEvent));
		}

		ServiceOrderEvent serviceOrderEvent;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {ServiceLiveEvent.class, ServiceOrderEvent.class, ServiceOrder.class});
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader xmlMessageBuffer = new StringReader(xmlSerializedServiceOrderEvent.toString());
			serviceOrderEvent = (ServiceOrderEvent) unmarshaller.unmarshal(xmlMessageBuffer);
		} catch (JAXBException e) {
			String errorMessage = "Exception attempting to deserialize service order event!";
			logger.error(errorMessage, e);
			throw new RuntimeException(errorMessage, e);
		}

		return serviceOrderEvent;
}
	
	protected void logUnmarshalledServiceOrder(ServiceOrder so) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("SO %s has %d parts", so
					.getSoId(), so.getParts().size()));
			for (SOPart part : so.getParts()) {
				logger.debug(ToStringBuilder.reflectionToString(part));
			}
		}
	}
	
	protected void validateEventSourceOrThrowException(Object eventSource) {
		if (!(eventSource instanceof ServiceOrder)) {
			String errorMessage = String.format(
					"The event source of a ServiceOrderEvent is not an instance of %s",
					ServiceOrder.class.getName());
			logger.warn(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}
	
	protected void validateServiceOrderEventOrThrowException(ServiceLiveEvent event) {
		if (!(event instanceof ServiceOrderEvent)) {
			String errorMessage = String.format(
					"The event '%s' is not an instance of %s", event,
					ServiceOrder.class.getName());
			logger.warn(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}
	
	protected OrderEventType extractOrderEventType(String orderEventName, OrderEventType defaultValue) {
		OrderEventType orderEventType;
		if (orderEventName == null) {
			orderEventType = defaultValue;
		}
		else {
			 orderEventType = OrderEventType.valueOf(orderEventName);
		}
		return orderEventType;
	}
	
}
