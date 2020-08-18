package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.springframework.beans.BeansException;

import com.newco.marketplace.translator.business.IHSRStagingService;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.SLOrderService;
import com.servicelive.util.MarketESBUtil;

public class HSRCreateDraftRequestProcessorAction extends AbstractEsbSpringAction{

	Logger logger = Logger.getLogger(HSRCreateDraftRequestProcessorAction.class);
	private SLOrderService slOrderService = null;
	private IHSRStagingService stagingService = null;
	

	public Message dispatchRequest(Message message) throws BeansException, ActionLifecycleException{
		
		slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
		stagingService = (IHSRStagingService) getBeanFactory().getBean(MarketESBConstant.HSR_STAGING_SERVICE);
		
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFileName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.NEW_FILE_PREFIX) )){
			dispatchNewRequests(message);
		}else if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.UPDATE_FILE_PREFIX) )){
			dispatchUpdateRequests(message);		
		}
		
		return message;
	}
	
	private void dispatchNewRequests(Message message) {
		List<CreateDraftResponse> createDraftRespList = new ArrayList<CreateDraftResponse>();
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);	
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> receivedRequests = (List<CreateDraftRequest>) message.getBody().get(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW);
		
		for(CreateDraftRequest createDraftReq : receivedRequests) {
			CreateDraftResponse createDraftResp = null;
			
			try {
				createDraftReq.setClientId(client);
				createDraftResp = slOrderService.createDraft(createDraftReq);
				stageResponse(createDraftReq, createDraftResp);
				
			} catch (Exception e) {
				String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
				ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), 
					inputFilefeedName, "Exception caught while processing DraftRequest", createDraftReq, e);
			}
			createDraftRespList.add(createDraftResp);
		}
		
		return;
	}
	
	private void dispatchUpdateRequests(Message message) {
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);	
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> receivedRequests = (List<CreateDraftRequest>) message.getBody().get(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE);
		if (receivedRequests == null || receivedRequests.isEmpty()) {
			return;
		}
		
		for(CreateDraftRequest createDraftReq : receivedRequests) {
			
			try {
				createDraftReq.setClientId(client);
				slOrderService.updateOrder(createDraftReq);
				
			} catch (Exception e) {
					String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
					ExceptionHandler.handle(client, "No attachment. Check file at SL server", 
							inputFilefeedName, "Exception caught while processing DraftRequest", createDraftReq, e);
				}
			}
	}

	
	private void stageResponse(CreateDraftRequest createDraftReq,
			CreateDraftResponse createDraftResp) {
		
		String orderNo = MarketESBUtil.getOMSOrderNumberFromReferenceFields(createDraftReq);
		String unitNo = MarketESBUtil.getOMSUnitNumberFromReferenceFields(createDraftReq);

		try {
			stagingService.updateHSRStageOrderWithSoId(orderNo, unitNo, createDraftResp.getSLServiceOrderId());
		} catch (Exception e) {
			logger.error("Error in persisting stage data after draft is created ", e);
		}
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public HSRCreateDraftRequestProcessorAction(ConfigTree config) {
		super.configTree = config;
		System.out.println("System exe reached HSRCreateDraftRequestProcessorAction");
		logger.info("came  here HSRCreateDraftRequestProcessorAction");		
	}
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public HSRCreateDraftRequestProcessorAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
		
	}
	
	
}
