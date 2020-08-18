package com.servicelive.esb.actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingResponse;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.SLOrderService;

public class AssurantAckProcessorAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(AssurantAckProcessorAction.class);
	private IApplicationPropertiesService appPropService = null;
	SLOrderService slOrderService = null;
	
	/**
	 * Method to process the message that contains the Unmarshalled Object Graph
	 * @param message
	 * @return
	 * @throws Exception
	 */	
	public Message dispatchRequest(Message message) throws Exception {
		//Get the translated object 
		Body body = message.getBody();
		String client = (String) body.get(MarketESBConstant.CLIENT_KEY);
		UpdateIncidentTrackingRequest request = (UpdateIncidentTrackingRequest) body.get(MarketESBConstant.TRANSLATED_ACK_OBJ_GRAPH);
		if (request != null) {
			slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
			appPropService = (IApplicationPropertiesService) SpringUtil.factory.getBean("ApplicationPropertiesService");
			slOrderService.setServiceOrderEndPointUrl(appPropService.getServiceLiveEndpoint());
			//slOrderService.setServiceOrderEndPointUrl("http://localhost:8080/marketws/services/ServiceOrderSEI");
			
			//Invoke the WebService Call to update so incident tracking table
			UpdateIncidentTrackingResponse ackResp = null;
			//Invoke the ServiceLive Webservice for update so incident tracking operation
			if(request.getBuyerId() > 0) {
				try {
					ackResp = slOrderService.updateIncidentTrackingWithAck(request);
					processResponse(request, ackResp, message);
				} catch (Exception e) {
					String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
					ExceptionHandler.handle(client, new String((byte[]) body.get()), inputFilefeedName,
						getClass().getName() + " reports: Error invoking ServiceLive webservice to update incident tracking with acknowldgement", 
						message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
				}
			}
		}
	    return message;
	}
	
	private void processResponse(UpdateIncidentTrackingRequest request, UpdateIncidentTrackingResponse response, Message message) {
		if (response.getErrorList() != null  && response.getErrorList().size() > 0){
			WSErrorInfo error = response.getErrorList().get(0);
			String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
			ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), 
				(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME), 
				getClass().getName() + " reports: Error Injecting Incident Acknowledgement.", error.getMessage(),
				message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
			logger.error(error.getMessage());
		}
		else
			logger.info("******* ----> SUCCESS - Acknowledgement file for Incident " + request.getClientIncidentid() + 
					" and status '" + request.getIncidentStatus() +"' has been processed successfully.");
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public AssurantAckProcessorAction(ConfigTree config) { super.configTree = config; }

	/**
	 * Default Constructor for JUnit test cases
	 */
	public AssurantAckProcessorAction() {
		// do nothing
	}

}
