package com.servicelive.esb.actions;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.impl.HSRTranslationService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.HSRServiceOrder;
import com.servicelive.esb.dto.HSRServiceOrders;
import com.servicelive.esb.service.ExceptionHandler;


public class HSRCreateDraftRequestTranslationAction extends AbstractEsbSpringAction {
	
	Logger logger = Logger.getLogger(HSRCreateDraftRequestTranslationAction.class);
	private HSRTranslationService xlationService = null;
	
	public Message translateData(Message message){
		List<HSRServiceOrder> serviceOrderList = null;
		xlationService = (HSRTranslationService) SpringUtil.factory.getBean("hsrTranslationService");	
				
		//Read the Unmarshalled Data
		Body body = message.getBody();
		HSRServiceOrders serviceOrders = 
			(HSRServiceOrders) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		
		String client = (String) body.get(MarketESBConstant.CLIENT_KEY);
		
		//Read the Mapped CreateDraftRequest objects from message body
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> createDraftReqListNew = (List<CreateDraftRequest>) body.get(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW);
		
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> createDraftReqListUpdate = (List<CreateDraftRequest>) body.get(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE);
		
		if(serviceOrders != null)
			serviceOrderList = serviceOrders.getServiceOrders();

		if(serviceOrderList == null) {
			handleInputError(message, body, client, "ServiceOrder list null, exiting");			
			return message;
		}
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFileName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.NEW_FILE_PREFIX) )){
			if(createDraftReqListNew == null) {
					handleInputError(message, body, client, "CreateDraftRequest New lists null, exiting");			
					return message;
				}
					
			for (CreateDraftRequest createDraftRequest : createDraftReqListNew) {
				try {
					xlationService.translateOrder(createDraftRequest, client);
				} catch (Exception e) {
					logger.error("Exception in Translation", e);
				}
			}
			logger.info("******** Translation successfully completed for "+serviceOrderList.size()+" ServiceOrder objects.");
		}
		
		if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.UPDATE_FILE_PREFIX) )){
			if(createDraftReqListUpdate == null) {
					handleInputError(message, body, client, "CreateDraftRequest Update lists null, exiting");			
					return message;
				}
					
			for (CreateDraftRequest createDraftRequest : createDraftReqListUpdate) {
				try {
					xlationService.translateUpdateOrder(createDraftRequest);
				} catch (Exception e) {
					logger.error("Exception in Translation", e);
				}
			}
			logger.info("******** Translation successfully completed for "+serviceOrderList.size()+" ServiceOrder objects.");
		}
		
		return message;
	}
	
	private void handleInputError(Message message, Body body, String client, String errorMsg) {
		String inputFilefeedName = (String)body.get(MarketESBConstant.FILE_FEED_NAME);
		logger.error( errorMsg );
		ExceptionHandler.handle(client, new String((byte[]) body.get()), 
			inputFilefeedName,
			errorMsg, 
			message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public HSRCreateDraftRequestTranslationAction(ConfigTree config) {
		super.configTree = config;
	}
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public HSRCreateDraftRequestTranslationAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
		
	}

	
}
