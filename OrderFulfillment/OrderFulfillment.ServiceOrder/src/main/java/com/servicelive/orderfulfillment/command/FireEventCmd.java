package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.bus.EventAdapter;
import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Mustafa Motiwala Date: Mar 30, 2010 Time: 1:21:29 PM
 */
public class FireEventCmd extends SOCommand {

	protected EventAdapter eventAdapter;

	@Override
	public void execute(Map<String, Object> processVariables) {

		final String methodName = "execute";
		logger.info(String.format("Entered method %s", methodName));

		String stateName = SOCommandArgHelper.extractStringArg(processVariables, 1);
		ServiceOrder so = getServiceOrder(processVariables);
		
		// Adding buyer check for Event creation as Event is not required for buyers other than 1000 & 3000
		if (so.getBuyerId() == 1000 || so.getBuyerId() == 3000) {
			
			ServiceOrderEvent event = createEvent(stateName, so);
			if (null != event) {
				logger.info(String.format("About to raise event: %s", event));
				logger.info("Event Name already triggered is" + stateName);
				logger.info("Service Order Id:" + so.getSoId());
				logger.info("Service Order last wf status id:" + so.getLastStatusId());
				logger.info("Service Order Last Wf status :" + so.getLastStatus());
				logger.info("Current service Order Wf status Id:" + so.getWfStateId());
				logger.info("Current service Order Wf status :" + so.getWfStatus());

				eventAdapter.raiseEvent(event);
			}
		} else {
			logger.info("Not Creating Event for :" + so.getBuyerId());
		}
		logger.info(String.format("Exiting method %s", methodName));
	}

	/**
	 *
	 * @param stateName
	 * @param so
	 * @return
	 */
	protected ServiceOrderEvent createEvent(String stateName, ServiceOrder so) {
		ServiceOrderEvent returnVal = createOrderEvent(so);

		LegacySOStatus status = LegacySOStatus.valueOf(stateName);

		switch (status) {
		case DRAFT:
			returnVal.addHeader(EventHeader.ORDER_CREATION_TYPE, ServiceOrderEvent.ORDER_CREATION_TYPE_NEW);
			returnVal.setEventType(OrderEventType.CREATED);
			break;
		case POSTED:
			returnVal.setEventType(OrderEventType.POSTED);
			break;
		case ACCEPTED:
			returnVal.setEventType(OrderEventType.ACCEPTED);
			break;
		case ACTIVE:
			returnVal.setEventType(OrderEventType.ACTIVATED);
			break;
		case CLOSED:
			returnVal.setEventType(OrderEventType.CLOSED);
			break;
		case COMPLETED:
			returnVal.setEventType(OrderEventType.COMPLETED);
			break;
		case PROBLEM:
			returnVal.setEventType(OrderEventType.PROBLEM);
			break;
		case DELETED:
			returnVal.setEventType(OrderEventType.DELETED);
			break;
		case VOIDED:
			returnVal.setEventType(OrderEventType.VOIDED);
			break;
		case CANCELLED:
			returnVal.setEventType(OrderEventType.CANCELLED);
			break;
		case PENDINGCANCEL:
			returnVal.setEventType(OrderEventType.PENDINGCANCEL);
			break;
		/*
		 * This case is added for inhome orders for Notifying NPS using
		 * sendmessage webservice
		 */
		case EXPIRED:
			returnVal.setEventType(OrderEventType.EXPIRED);
			break;

		}
		return returnVal;
	}

	protected ServiceOrderEvent createOrderEvent(ServiceOrder so) {
		ServiceOrderEvent returnVal = new ServiceOrderEvent(so.getSoId(), so);
		returnVal.addHeader(EventHeader.BUYER_COMPANY_ID, String.valueOf(so.getBuyerId()));
		returnVal.addHeader(EventHeader.BUYER_RESOURCE_ID, String.valueOf(so.getBuyerResourceId()));
		return returnVal;
	}

	public void setEventAdapter(EventAdapter eventAdapter) {
		this.eventAdapter = eventAdapter;
	}
}
