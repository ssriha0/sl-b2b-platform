package com.servicelive.esb.actions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.ShcOrderTransaction;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskReponse;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.SLOrderService;
import com.servicelive.util.MarketESBUtil;

/**
 * An ESB Action responsible for Consuming ServiceLive web service for Create Draft
 * 
 * @author pbhinga
 *
 */
public class CreateDraftRequestProcessorAction extends AbstractEsbSpringAction {

	private static final String ERROR_SO_ID = "000-0000000000-00";
	private Logger logger = Logger.getLogger(CreateDraftRequestProcessorAction.class);
	SLOrderService slOrderService = null;
	private IStagingService stagingService = null;
	
	//private ServiceOrderSEIHttpBindingStub binding;
	
	/**
	 * Method to process the message that contains the Unmarshalled Object Graph
	 * @param message
	 * @return
	 * @throws Exception
	 */	
	public Message dispatchRequest(Message message) throws Exception {
		logger.info(new StringBuffer("**** Invoking CreateDraftRequestProcessorAction ---->")
			.append(message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH)));
		//Get the translated object 
//		List<CreateDraftRequest> createDraftReqListNew = (List<CreateDraftRequest>)message.getBody().get(MarketESBConstant.TRANSLATED_OBJ_GRAPH_NEW);
//		List<CreateDraftRequest> createDraftReqListUpdate = (List<CreateDraftRequest>)message.getBody().get(MarketESBConstant.TRANSLATED_OBJ_GRAPH_UPDATE);
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> createDraftReqListNew = (List<CreateDraftRequest>) message.getBody().get(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW);
		
		@SuppressWarnings("unchecked")
		List<CreateDraftRequest> createDraftReqListUpdate = (List<CreateDraftRequest>) message.getBody().get(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE);
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
		if ((createDraftReqListNew != null && createDraftReqListNew.size() > 0)
				|| (createDraftReqListUpdate != null && createDraftReqListUpdate.size() > 0)) {
			slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
			
			List<CreateDraftResponse> createDraftRespList = new ArrayList<CreateDraftResponse>();
			
			String serviceLiveWebServiceEndPointURL = "NOT A WEBSERVICE CALL";//Constants.MARKET_WS_URL;
			logger.info(new StringBuffer("=========== Invoking ServiceLive WebService URL: ")
				.append(serviceLiveWebServiceEndPointURL));
//			slOrderService.setServiceOrderEndPointUrl(serviceLiveWebServiceEndPointURL);
			
			//Invoke the WebService Call to Create Service Orders Draft
			int newSuccessCount = 0;
			for(CreateDraftRequest createDraftReq : createDraftReqListNew) {
				CreateDraftResponse createDraftResp = null;
				
				//Invoke the ServiceLive Webservice for CreateDraft operation
				if(createDraftReq.getBuyerId() > 0) {
					try {
						// dmitzel - If this statement fails, the exception handler will get a null pointer exception when it tries to access createDraftResp
						createDraftResp = slOrderService.createDraft(createDraftReq);
						boolean responseSuccess = processResponse(createDraftReq, createDraftResp, message);
						if (responseSuccess) {
							++newSuccessCount;
						}
					} catch (Exception e) {
						String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
						ExceptionHandler.handle(client, message.getBody().toString(), 
							inputFilefeedName,
							"Exception caught while processing DraftRequest", 
							createDraftResp.getClientServiceOrderId(),
							e);
					}
					createDraftRespList.add(createDraftResp);
				}
				else
				{
					String msg = "Cannot inject - invalid buyerID: " + createDraftReq.getBuyerId();
//					logger.error( msg );
					ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), 
						(String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME),
						msg, 
						message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
				}
			}
			
			//Invoke the WebService Call to Create Service Orders Draft
			int updateSuccessCount = 0;
			if (null != createDraftReqListUpdate) {
				for(CreateDraftRequest createDraftReq : createDraftReqListUpdate) {
					CreateDraftResponse createDraftResp = null;
					//Invoke the ServiceLive Webservice for CreateDraft operation
					if(createDraftReq.getBuyerId() > 0) {
						try {
							createDraftResp = slOrderService.createDraft(createDraftReq);
							boolean responseSuccess = processResponse(createDraftReq, createDraftResp, message);
							if (responseSuccess) {
								++updateSuccessCount;
							}
						} catch (Exception e) {
							String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
							ExceptionHandler.handle(client, message.getBody().toString(), 
								inputFilefeedName,
								"Exception caught while processing DraftRequest", 
								createDraftResp.getClientServiceOrderId(),
								e);
						}
					}
				}
			}
			
			if (createDraftRespList != null && createDraftRespList.size() > 0) {
				//queue orders for routing
				message.getBody().add(MarketESBConstant.ROUTEOBJ_GRAPH, createDraftRespList);
			}
			
			
			if (null != createDraftReqListUpdate) {
				logger.info(new StringBuffer("RESPONSE - ").append(newSuccessCount)
						.append(" orders created and ").append(updateSuccessCount)
						.append(" orders updated successfully."));				
			} else {
				logger.info(new StringBuffer("RESPONSE - ").append(newSuccessCount)
						.append(" orders created successfully."));	
			}
		}
	    return message;
	}

	private boolean processResponse(CreateDraftRequest createDraftReq, CreateDraftResponse createDraftResp, Message message) 
	{		
		boolean responseSuccess = false;
		Body body = message.getBody();
		String client = (String) body.get(MarketESBConstant.CLIENT_KEY);
		
		List<WSErrorInfo>  errorsList = createDraftResp.getErrorList();
		WSErrorInfo[] errors =null;
		if(errorsList!=null){
			errors = errorsList.toArray(new WSErrorInfo[0]);
		}
				
		if ((errors != null && errors.length > 0)
				|| createDraftResp.getSLServiceOrderId() == null
				|| createDraftResp.getSLServiceOrderId().equals(ERROR_SO_ID)) {
			Map<String, String> errorMap = new HashMap<String, String>();
			if (errors != null && errors.length > 0) {
				String orderID = "";
				for (CustomRef ref : createDraftReq.getCustomRef()) {
					if (ref.getKey().equals(MarketESBConstant.CUSTOM_REF_ORDERID_STRING)) {
						orderID = ref.getValue();
						break;
					}
				}
				for (int i=0; i < errors.length; i++) {
					WSErrorInfo error = errors[i];
					if( error != null && StringUtils.isNotBlank(error.getMessage()) ) {
						logger.error(error.getMessage());
						errorMap.put(orderID + " WS Error" + Integer.toString(i), error.getMessage());
					}
				}
			}
			if( errorMap.size() > 0 )
			{
				ExceptionHandler.handle(client, new String((byte[]) body.get()), 
					(String)body.get(MarketESBConstant.FILE_FEED_NAME), 
					getClass().getName() + " reports: Error Injecting Order", 
					errorMap, 
					body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
			}
		} else {
			responseSuccess = true;
		}
		
		// For OMS order; the orders info/status/injection errors etc should be updated in staging database
		if (MarketESBConstant.Client.OMS.equals(client)) {
			this.updateStagingData(createDraftResp,errors,createDraftReq,message);
		}
		
		return responseSuccess;
	}
	
	/**
	 * Method to update the soId & error details in Staging Data
	 * @param CreateDraftResponse createDraftRes
	 * @param WSErrorInfo[] errors
	 * @param CreateDraftRequest createDraftReq
	 * @param Message message
	 */	
	private void updateStagingData(CreateDraftResponse createDraftResp,WSErrorInfo[] errors, CreateDraftRequest createDraftReq,Message message) {
		try{			
			stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
			StagingDetails stagingData = (StagingDetails) message.getBody().get(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH);
			if(stagingData != null){				
				// // Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
				List<ShcOrder> stagingOrdersList = stagingData.getStageServiceOrder();
				String orderNo = MarketESBUtil.getOMSOrderNumberFromReferenceFields(createDraftReq);
				String unitNo = MarketESBUtil.getOMSUnitNumberFromReferenceFields(createDraftReq);
				for(ShcOrder stageServiceOrder : stagingOrdersList) {
					List<ShcErrorLogging> shcErrorLoggingList = new ArrayList<ShcErrorLogging>();						
					if(stageServiceOrder.getOrderNo().equals(orderNo) && stageServiceOrder.getUnitNo().equals(unitNo)){ 
						stageServiceOrder = stagingService.getShcOrder(orderNo, unitNo);
						if(errors != null && errors.length > 0){
							for(WSErrorInfo error : errors){
								ShcErrorLogging shcErrorLogging = new ShcErrorLogging();
								if( error != null && StringUtils.isNotBlank(error.getMessage()) ) {
									List<ShcOrderTransaction> shcOrderTransactionList = new ArrayList<ShcOrderTransaction>(stageServiceOrder.getShcOrderTransactions());
									int size = shcOrderTransactionList.size();
									
									int[] transactionIDs = new int[size];
									int i = 0;
									if (shcOrderTransactionList != null) {
										for(ShcOrderTransaction shcOrderTransaction : shcOrderTransactionList){
											transactionIDs[i] = shcOrderTransaction.getShcOrderTransactionId();
											logger.info("transactionIDs[i]"+ transactionIDs[i]);
											i++;
										}
										Arrays.sort(transactionIDs);						
									
										int transactionID = transactionIDs[size-1];
										ShcOrderTransaction shcOrderTrans = new ShcOrderTransaction();
										shcOrderTrans.setShcOrderTransactionId(transactionID);
										shcErrorLogging.setShcOrderTransaction(shcOrderTrans);								
										shcErrorLogging.setErrorMessage(error.getMessage());
										if(StringUtils.isNotBlank(error.getCode())){
											shcErrorLogging.setErrorCode(error.getCode());
										}	
										shcErrorLogging.setModifiedBy(createDraftReq.getUserId());
										shcErrorLoggingList.add(shcErrorLogging);
									}
								}
							}
						}	
						
						// if any skus were re-prices and returned for update, 
						// set new selling price on the shc order
						List<CreateTaskReponse> tasksList = createDraftResp.getTasks();
						CreateTaskReponse[] tasks = null;
						if(tasksList!=null){
							tasks = tasksList.toArray(new CreateTaskReponse[0]);
						}
						if (tasks != null && tasks.length > 0) {
							// update prices based on task response
							updateTasksOnShcOrder(stageServiceOrder, tasks);
						}
						stageServiceOrder.setSoId(createDraftResp.getSLServiceOrderId());
						stagingService.stageDataAfterProcessing(stageServiceOrder,shcErrorLoggingList); 
						
					}
				}	
			}
		}
		catch(Exception ex){
			logger.error("Error in persisting stage data after processing",ex);
		}
	}
	
	private void updateTasksOnShcOrder(ShcOrder order,
			CreateTaskReponse[] tasks) {
		 
		Set<ShcOrderSku> skus = order.getShcOrderSkus();
		
		for (CreateTaskReponse task : tasks) {
			String sku = task.getJobCode();
			if (sku != null) {
				Double skuSellingPrice = task.getLaborPrice();
				BigDecimal scaledPrice = BigDecimal.valueOf(skuSellingPrice);
				scaledPrice = scaledPrice.setScale(2, RoundingMode.HALF_DOWN);
				
				for(ShcOrderSku shcSku : skus) {
					if (shcSku.getSku() != null && shcSku.getSku().equals(sku)) {
						shcSku.setInitialBidPrice(scaledPrice.doubleValue());
					}
				}
			}
		}
		order.setShcOrderSkus(skus);
		
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public CreateDraftRequestProcessorAction(ConfigTree config) { super.configTree = config; }
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public CreateDraftRequestProcessorAction() {}


}
