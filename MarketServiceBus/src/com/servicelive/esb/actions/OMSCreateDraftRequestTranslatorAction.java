package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IBuyerSkuService;
import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.business.ITranslationService;
import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.ShcOrderTransaction;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.JobCode;
import com.servicelive.esb.dto.JobCodes;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.util.MarketESBUtil;

/**
 * An ESB Action responsible for mapping the input object graph into a list of 
 * CreateDraftRequest objects
 * @author pbhinga
 *
 */
public class OMSCreateDraftRequestTranslatorAction extends AbstractEsbSpringAction {
	
	private Logger logger = Logger.getLogger(OMSCreateDraftRequestTranslatorAction.class);
	private ITranslationService xlationService = null;
	private IStagingService stagingService = null; 
	private IBuyerSkuService skuService = null;
	
	/**
	 * Method to process the message that contains the Unmarshalled Object Graph
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public Message translateData(Message message)
	{
		logger.info(new StringBuffer("**** Invoking OMSCreateDraftRequestTranslatorAction ---->")
			.append(message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH)));
		List<ServiceOrder> serviceOrderList = null;
		xlationService = (ITranslationService) SpringUtil.factory.getBean("TranslationService");	
		stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
		
		//Read the Unmarshalled Data (with Leadtime data populated by SST WS) from message body
		Body body = message.getBody();
		ServiceOrders serviceOrders = 
			(ServiceOrders) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		
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
		if(createDraftReqListNew == null && createDraftReqListUpdate == null) {
			handleInputError(message, body, client, "CreateDraftRequest New & Update lists null, exiting");			
			return message;
		}		
		//Size of createDraftRequestList and serviceOrderList should always be the same.
		if(serviceOrderList.size() != (createDraftReqListNew.size() + createDraftReqListUpdate.size())) {
			handleInputError(message, body, client, "ServiceOrder List size mismatch, exiting");	
			return message;
		}	
		
		List<ShcOrder> stagingOrdersList = new ArrayList<ShcOrder>(serviceOrderList.size());
		
		// process new drafts first
		stagingOrdersList.addAll(processDraftRequests(message, serviceOrderList, createDraftReqListNew, MarketESBConstant.TX_MODE_NEW));
				
		// process updated drafts
		stagingOrdersList.addAll(processDraftRequests(message, serviceOrderList, createDraftReqListUpdate, MarketESBConstant.TX_MODE_UPDATE));

		// set all newly unmarshalled and staged orders in session
		StagingDetails stagingData = (StagingDetails) body.get(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH);
		stagingData.setStageServiceOrder(stagingOrdersList);
		body.add(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH, stagingData);		
		logger.debug(new StringBuffer("******** Translation successfully completed for ").append(serviceOrderList.size()).append(" ServiceOrder objects."));
		return message;
	}

	private List<ShcOrder> processDraftRequests(Message message, List<ServiceOrder> serviceOrderList, List<CreateDraftRequest> createDraftReqList, String txnType) {
		
		List<ShcOrder> result = new ArrayList<ShcOrder>(createDraftReqList.size());
		
		for (CreateDraftRequest createDraftRequest : createDraftReqList) {
			
			result.add(processDraftRequest(message, serviceOrderList, createDraftRequest, txnType));
		}
		
		return result;
	}

	private ShcOrder processDraftRequest(Message message, List<ServiceOrder> serviceOrderList, CreateDraftRequest createDraftRequest, String txnType) {
		
		List<SkuPrice> skus;
		String orderId = MarketESBUtil.constructOrderIdUtil(createDraftRequest.getOrderNumber(), createDraftRequest.getUnitNumber());
		
		ServiceOrder order = getSoFromDraft(serviceOrderList, orderId, txnType);
		logger.debug("Processing Order Id: " + orderId);
		
		skus = getSKUprice(order);			
		
		ShcOrder result = processOrderForStaging(createDraftRequest,skus, message, orderId, txnType);
		
		//we are looking for the specific order and will translate with the skus we requested
		//try new first then the update collection
		try
		{
			translateDrafts(orderId, createDraftRequest, skus, message);		
		}
		catch( Exception e )
		{
			String errorMsg = "Exception caught for " + orderId 
				+ " in file " + (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
			logger.error( errorMsg, e );
		}
		return result;
	}

	private void handleInputError(Message message, Body body, String client, String errorMsg) {
		String inputFilefeedName = (String)body.get(MarketESBConstant.FILE_FEED_NAME);
		logger.error( errorMsg );
		ExceptionHandler.handle(client, new String((byte[]) body.get()), 
			inputFilefeedName,
			errorMsg, 
			message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
	}
	

	private ServiceOrder getSoFromDraft(List<ServiceOrder> orders, String orderId, String txnType) {
		ServiceOrder result = null;
		
		for (ServiceOrder order : orders) {			
			String objOrderId = MarketESBUtil.constructOrderIdUtil(order.getServiceOrderNumber(), order.getServiceUnitNumber());			
			if (objOrderId.equals(orderId) && order.getTransactionType().equals(txnType)) {
				result = order;
				break;
			}
		}
		return result;
	}
	
	private ShcOrder getOrderByDraft(CreateDraftRequest request, Message message, String orderId, String txnType) {
		ShcOrder result = null;
		
		Body body = message.getBody();
		StagingDetails stagingData = (StagingDetails) body.get(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH);
		if (stagingData == null) {
			logger.warn("StagingDetails not found for " + orderId);
			return null;
		}
		List<ShcOrder> stagingOrdersList = stagingData.getStageServiceOrder();
		for (ShcOrder shcOrder : stagingOrdersList) {
			String objOrderId = MarketESBUtil.constructOrderIdUtil(shcOrder.getOrderNo(), shcOrder.getUnitNo());
			
			Set<ShcOrderTransaction> shcOrderTransactionSet = shcOrder.getShcOrderTransactions();
			String transactionType = "";
			if(shcOrderTransactionSet!=null){
				transactionType = shcOrderTransactionSet.toArray(new ShcOrderTransaction[0])[0].getTransactionType();
			}
			
			if (objOrderId.equals(orderId) && (transactionType.equals(txnType))) {
				result = shcOrder;
				break;
			}
			
			
		}
		return result;
	}

	private void translateDrafts(String orderID, CreateDraftRequest request, List<SkuPrice> skus, Message message) throws Exception {
		String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
		try {			
			xlationService.translateDraft(request, skus, client);
			xlationService.createSOUpsells(request);
		} catch (Exception e) {
			String inputFilefeedName = (String) message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
			StringBuilder errorMsg = new StringBuilder("Exception returned by Translation service");
			ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), inputFilefeedName, errorMsg, e);
		}
	}

	private ShcOrder processOrderForStaging( CreateDraftRequest request, List<SkuPrice> skus, Message message, String orderId, String txnType) {
		//This is done for staging data
			
			int buyerId = 0;
			try {
				buyerId = xlationService.getauthorizeBuyerId(request.getUserId());
			} catch(Exception ex){
				 logger.error("Error in authorizing buyer!",ex);
			}
			request.setBuyerId(buyerId);
			
			ShcOrder order = getOrderByDraft(request, message, orderId, txnType);
			order = mapSpendLimitForStaging(order,request,skus);
			
			stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
			
			stagingService.stageDataAfterTranslation(order,skus,buyerId);    
			
			return order;
			
	}
	
	
	
	/**
	 * Method to set required attributes in list of SkuPrice objects that Translator requires 
	 * @param serviceOrder The unmarshalled service order (from XML payload feed)
	 * @return
	 */
	private List<SkuPrice> getSKUprice(ServiceOrder serviceOrder) {
		List<SkuPrice> skuList = new ArrayList<SkuPrice>();
		SkuPrice sku = null;		
		JobCodes jobCodesObj = serviceOrder.getJobCodes();
		List<JobCode> jobCodes = jobCodesObj.getJobCodeList();
		if(jobCodes != null) {
			for (JobCode jobCode : jobCodes) {
				sku = new SkuPrice();
				sku.setLeadTime(jobCode.getJobCodeInfo().getLeadTimeDays());
				sku.setMargin(new Double(jobCode.getJobCodeInfo().getMarginRate()));
				sku.setSku(jobCode.getJobCodeInfo().getJobCode());
				sku.setSpecialtyCode(jobCode.getJobCodeInfo().getSpecCode());
				sku.setJobCodeType(jobCode.getType());
				sku.setCoverage(jobCode.getCoverage());
				sku.setActualSellingPrice(Double.valueOf(jobCode.getAmount()));
				skuList.add(sku);
			}
		}
		return skuList; 
	}
	
	/**
     * Method to map the spend limit in staging 
     * @param List<StageServiceOrder> 
     * @param CreateDraftRequest request
     * @return List<StageServiceOrder> 
     */  
	private ShcOrder mapSpendLimitForStaging(ShcOrder stagingOrder,CreateDraftRequest request, List<SkuPrice> skus)  {
        
        skuService = (IBuyerSkuService) SpringUtil.factory.getBean("BuyerSkuService"); 
        int buyerID= request.getBuyerId(); 
        try {
                  
          String storeCode = stagingOrder.getStoreNo();
          List <ShcOrderSku> shcOrderSkus = new ArrayList<ShcOrderSku>(stagingOrder.getShcOrderSkus());
          for(SkuPrice sku : skus){
                sku = skuService.priceBuyerSku(sku, request.getServiceLocation().getZip(), storeCode, buyerID);                               
                for(ShcOrderSku shcOrderSku : shcOrderSkus){
                      if (shcOrderSku.getSku() != null && sku.getSku() != null
                                  && shcOrderSku.getSku().equals(sku.getSku())) {
                            shcOrderSku.setInitialBidPrice(sku.getSellingPrice());
                      }
                }           
          }
            
 
        } catch(Exception e){
           logger.error("Swallowing exception.  Just in case anyone cares, here is the message: " + e.getMessage());   
        }
        return stagingOrder; 
	}


	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public OMSCreateDraftRequestTranslatorAction(ConfigTree config) { super.configTree = config; }

	/**
	 * Default Constructor for JUnit test cases
	 */
	public OMSCreateDraftRequestTranslatorAction() {}
	
}
