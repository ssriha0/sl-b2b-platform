package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.servicelive.bus.ServiceLiveEventListener;
import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.event.order.OrderEventType;
import com.servicelive.bus.event.order.ServiceOrderEvent;
import com.servicelive.esb.constant.LegacySOStatusForInHome;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class SearsHSROrderAcceptedEventHandler extends AbstractServiceOrderEventHandler
		implements ServiceLiveEventListener {

	private static final Logger logger = Logger.getLogger(SearsHSROrderAcceptedEventHandler.class);

	private SearsHSROutgoingFileWriterAction outgoingFileWriter;
	
	public SearsHSROrderAcceptedEventHandler() {
		super();
	}
	
	public SearsHSROrderAcceptedEventHandler(ConfigTree configTree) {
		super(configTree);
	}
	
	@Override
	public void initialise() throws ActionLifecycleException {
		super.initialise();
	}
	
	public Message processMessage(Message message) throws ActionProcessingException {
		final String methodName = "processMessage";
		logger.info(String.format("entered %s", methodName));

		try {
			Object messagePayload = message.getBody().get();
			ServiceOrderEvent serviceOrderEvent = super.unmarshalServiceOrderEvent(messagePayload);
			Batch batch = this.recordBatch(serviceOrderEvent);
			if(null != batch){
				message.getBody().add(MarketESBConstant.INTEGRATION_BATCH_ID, batch.getBatchId());
			}
			logger.info("Leaving processMessage()");
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
			Batch batch = this.recordBatch(event);
			if(null != batch){
				outgoingFileWriter.writeFileForBatch(batch.getBatchId());
			}
		}
		catch (Exception e) {
			this.exceptionHandler(null, e);
		}
		
		logger.info(String.format("exiting %s", methodName));
	}
	
	private Batch recordBatch(ServiceLiveEvent event) {
		
		logger.info("Inside recordBatch");
		validateServiceOrderEventOrThrowException(event);
		ServiceOrderEvent serviceOrderEvent = (ServiceOrderEvent)event;

		String orderEventName = serviceOrderEvent.getEventHeader().get(ServiceOrderEvent.ORDER_EVENT);
		if (orderEventName == null) {
			orderEventName = "UNKNOWN";
		}

		Object eventSource = serviceOrderEvent.getEventSource();
		super.validateEventSourceOrThrowException(eventSource);
		
		ServiceOrder serviceOrder = (ServiceOrder)serviceOrderEvent.getEventSource();
		logUnmarshalledServiceOrder(serviceOrder);
		logger.info("Order Event name from event:" + orderEventName);
		logger.info("Service Order Id:"+ serviceOrder.getSoId());
		logger.info("Last wf status Id:"+ serviceOrder.getLastStatusId());
		logger.info("Current Wf Status Id:"+ serviceOrder.getWfStateId());
		if(OrderEventType.CLOSED.name().equals(orderEventName)){
			logger.info("Calling insertInHomeOutBoundNotification()");
			/**This method to insert details of callclose of inhome orders to buyeroutbound Notification Table*/
		    this.getNotificationServiceCoordinator().insertInHomeOutBoundNotification(serviceOrder);
		    /**This method to insert details of status change(close) of inhome orders to buyeroutbound Notification Table*/
		     this.getNotificationServiceCoordinator().insertRecordForStatusChange(serviceOrder,orderEventName);		    
		     logger.info("After calling insertInHomeOutBoundNotification()");
		}else if(OrderEventType.ACCEPTED.name().equals(orderEventName)){
			logger.info("Calling insertRecordForStatusChange()");
		    logger.info("After calling insertInHomeOutBoundNotification()");
			return this.getIntegrationServiceCoordinator().recordHsrOrderAcceptedEventAndRelatedData(orderEventName,serviceOrder);
		}		
		 return null;
	}
	@Override
	protected Long getIntegrationId(String fileName) {
		return IntegrationName.HSR_OUTBOUND.getId();
	}

	@Override
	protected String getIntegrationName(String fileName) {
		return IntegrationName.HSR_OUTBOUND.name();
	}

	public void setOutgoingFileWriter(SearsHSROutgoingFileWriterAction outgoingFileWriter) {
		this.outgoingFileWriter = outgoingFileWriter;
	}
}
