package com.servicelive.esb.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteResponse;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.SLOrderService;

public class ClientNoteProcessorAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(CreateDraftRequestProcessorAction.class);
	SLOrderService slOrderService = null;
	
	//private ServiceOrderSEIHttpBindingStub binding;
	
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
		ClientServiceOrderNoteRequest request = (ClientServiceOrderNoteRequest) body.get(MarketESBConstant.TRANSLATED_NOTE_OBJ_GRAPH);
		if (request != null) {
			slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
			
			String serviceLiveWebServiceEndPointURL = Constants.MARKET_WS_URL;
			logger.info("\n\n=========== ClientNoteProcessorAction: Invoking ServiceLive WebService URL: "+serviceLiveWebServiceEndPointURL);
			slOrderService.setServiceOrderEndPointUrl(serviceLiveWebServiceEndPointURL);
			
			//Invoke the WebService Call to Create Client Notes
			ClientServiceOrderNoteResponse noteResp = null;
			//Invoke the ServiceLive Webservice for ClientNote operation
			if(request.getBuyerId() > 0) {
				try {
					noteResp = slOrderService.addClientNote(request);
					processResponse(request, noteResp, message);
				} catch (Exception e) {
					String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
					ExceptionHandler.handle(client, new String((byte[]) body.get()), inputFilefeedName,
						getClass().getName() + " reports: Error invoking ServiceLive webservice for creating Client Note", 
						message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
				}
			}
			
			logger.info("******* ----> SUCCESS - client note for " + request.getOrderIDString() + " created successfully.");
		}
	    return message;
	}
	
	private void processResponse(ClientServiceOrderNoteRequest request, ClientServiceOrderNoteResponse response, Message message) {
		if (	(response.getErrorList() != null
				&& response.getErrorList().size() > 0)
				|| 
				(response.getSLServiceOrderId() == null 
				|| response.getSLServiceOrderId().equals("000-0000000000-00"))) {//TODO Constant
			Map<String, String> errors = new HashMap<String, String>();
			if (response.getErrorList() != null && response.getErrorList().size() > 0) {
				String orderID = request.getOrderIDString();
				for (int i=0; i < response.getErrorList().size(); i++) {
					WSErrorInfo error = response.getErrorList().get(i);
					errors.put(orderID + " WS Error" + Integer.toString(i), error.getMessage());
				}
			}
			String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
			ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), 
				(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME), 
				getClass().getName() + " reports: Error Injecting Client Note",
				message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
		}
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public ClientNoteProcessorAction(ConfigTree config) { super.configTree = config; }

	/**
	 * Default Constructor for JUnit test cases
	 */
	public ClientNoteProcessorAction() {}

}
