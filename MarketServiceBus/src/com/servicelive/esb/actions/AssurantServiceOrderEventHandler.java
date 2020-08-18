/**
 * 
 */
package com.servicelive.esb.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.bus.ServiceLiveEventListener;
import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.LegacySOSubStatus;

/**
 * @author sahmed
 * 
 */
public class AssurantServiceOrderEventHandler extends AbstractServiceOrderEventHandler
	implements ServiceLiveEventListener {
	
	private static final Logger logger = Logger.getLogger(AssurantServiceOrderEventHandler.class);
	private static final Map<LegacySOSubStatus, String> serviceLiveStatusToAssurantUpdateMap;
	private static final Map<String, String> assurantBuyerNotificationStatusMap;
	static {
		serviceLiveStatusToAssurantUpdateMap = new HashMap<LegacySOSubStatus, String>(5);
		serviceLiveStatusToAssurantUpdateMap.put(LegacySOSubStatus.PART_BACK_ORDERED, "Parts Ordered");
		serviceLiveStatusToAssurantUpdateMap.put(LegacySOSubStatus.PART_SHIPPED, "Part Shipped");
		
		assurantBuyerNotificationStatusMap = new HashMap<String, String>(10);
		assurantBuyerNotificationStatusMap.put("1", "Send Back - Info");
		assurantBuyerNotificationStatusMap.put("2", "Cancelled");
		assurantBuyerNotificationStatusMap.put("3", "Cancelled");
		assurantBuyerNotificationStatusMap.put("4", "Service Denied");
		assurantBuyerNotificationStatusMap.put("5", "Customer Delayed Service");
		assurantBuyerNotificationStatusMap.put("6", "Information Update");
		assurantBuyerNotificationStatusMap.put("7", "Send Back - Liaison");
	}
	
	private AssurantWriteOutgoingFileAction assurantWriteOutgoingFileAction;
	
	public AssurantServiceOrderEventHandler() {
		super();
	}
	
	public AssurantServiceOrderEventHandler(ConfigTree configTree) throws ConfigurationException {
		super(configTree);
	}
	
	public Message processMessage(Message message) throws ActionProcessingException {
		final String methodName = "processMessage";
		logger.info(String.format("entered %s", methodName));

		try {
			Object messagePayload = message.getBody().get();
			ServiceOrderEvent serviceOrderEvent = super.unmarshalServiceOrderEvent(messagePayload);
			this.doHandleEvent(serviceOrderEvent);
		}
		catch (Exception e) {
			throw new ActionProcessingException("Error occurred while processing message.", e);
		}
		
		logger.info(String.format("exiting %s", methodName));
		return message;
	}
	
	public void handleEvent(ServiceLiveEvent event) {
		final String methodName = "handleEvent";
		logger.info(String.format("entered %s", methodName));

		try {
			this.doHandleEvent(event);
		}
		catch (Exception e) {
			this.exceptionHandler(null, e);
			
			ServiceOrderEvent serviceOrderEvent = (ServiceOrderEvent)event;
			ServiceOrder serviceOrder = (ServiceOrder)serviceOrderEvent.getEventSource();
			
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("SO %s has %d parts", serviceOrder
					.getSoId(), serviceOrder.getParts().size()));
			for (SOPart part : serviceOrder.getParts()) {
				sb.append(ToStringBuilder.reflectionToString(part));
			}
			ExceptionHandler.handleAssurantServiceOrderEventHandlerException(sb.toString(), e);
		}
		
		logger.info(String.format("exiting %s", methodName));
	}

	private void doHandleEvent(ServiceLiveEvent event) {
		validateServiceOrderEventOrThrowException(event);
		ServiceOrderEvent serviceOrderEvent = (ServiceOrderEvent)event;
		
		Object eventSource = serviceOrderEvent.getEventSource();
		super.validateEventSourceOrThrowException(eventSource);
		
		ServiceOrder serviceOrder = (ServiceOrder)serviceOrderEvent.getEventSource();
		logUnmarshalledServiceOrder(serviceOrder);
		
		String orderEventName = serviceOrderEvent.getEventHeader().get(ServiceOrderEvent.ORDER_EVENT);
		OrderEventType orderEventType = extractOrderEventType(orderEventName, null);
		String subStatusName = null;
		// SL - 17492
		String resolutionComments = serviceOrder.getResolutionDs();
		String incidentActionDescription = StringUtils.EMPTY;
		if (orderEventType != null) {
			 if (orderEventType == OrderEventType.BUYER_NOTIFICATION) {
				 String substatusActionId = serviceOrderEvent.getEventHeader().get(EventHeader.SUBSTATUS_ACTION.toString());
				 subStatusName = getMappedAssurantBuyerNotificationStatus(substatusActionId);
				 incidentActionDescription = serviceOrderEvent.getEventHeader().get(EventHeader.SUBSTATUS_REASON.toString());
			 } else if (orderEventType == OrderEventType.SUBSTATUS_CHANGE) {
				 subStatusName = getMappedAssurantSubStatus(serviceOrder.getWfSubStatusId());
			 } else if (orderEventType == OrderEventType.CREATED) {
				 subStatusName = serviceOrderEvent.getEventHeader().get(EventHeader.ORDER_CREATION_TYPE.toString());
			 }
			 else if (eventIsForCancellation(orderEventType)) {
				if (!this.getIntegrationServiceCoordinator().getIntegrationBO().wasCancellationRequestReceived(serviceOrder.getSoId())) {
					 // Ignore the event if it is not in response to a cancellation request received from Assurant
					logger.debug(String.format("Ignoring event with type '%s' for serviceLiveOrderId '%s' since there is no prior cancellation request from Assurant for the same service order", orderEventType.toString(), serviceOrder.getSoId()));
					return;
				}
				 
			 }
		}

		// Put the service order data in the integration db, and then call the next action
		Batch batch = this.getIntegrationServiceCoordinator().recordAssurantServiceOrderEventData(serviceOrder, orderEventName, subStatusName, incidentActionDescription);
		long batchId = batch.getBatchId();
		
		this.assurantWriteOutgoingFileAction.writeOutgoingFile(batchId, orderEventType, subStatusName, incidentActionDescription,resolutionComments);
	}

	private boolean eventIsForCancellation(OrderEventType orderEventType) {
		return orderEventType == OrderEventType.CANCELLED || orderEventType == OrderEventType.VOIDED || orderEventType == OrderEventType.DELETED;
	}
	
	private String getMappedAssurantSubStatus(Integer subStatusId) {
		if (subStatusId == null) {
			return "Unmapped subStatusId: null";
		}
		
		LegacySOSubStatus subStatus = LegacySOSubStatus.fromId(subStatusId);
		if (subStatus == null) {
			return "Unmapped subStatusId: " + subStatusId.toString();
		}
		else if (serviceLiveStatusToAssurantUpdateMap.containsKey(subStatus)) {
			return serviceLiveStatusToAssurantUpdateMap.get(subStatus);
		}
		else {
			return "Unmapped subStatus: " + subStatus.toString();
		}
	}

	private String getMappedAssurantBuyerNotificationStatus(String status) {
		if (status != null && assurantBuyerNotificationStatusMap.containsKey(status)) {
			return assurantBuyerNotificationStatusMap.get(status);
		}
		return "Unmapped buyer notification status: " + status;
	}
	
	public void setAssurantWriteOutgoingFileAction(
			AssurantWriteOutgoingFileAction assurantWriteOutgoingFileAction) {
		this.assurantWriteOutgoingFileAction = assurantWriteOutgoingFileAction;
	}

	public AssurantWriteOutgoingFileAction getAssurantWriteOutgoingFileAction() {
		return assurantWriteOutgoingFileAction;
	}

	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.ASSURANT_OUTBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.ASSURANT_OUTBOUND.name();
	}
}
