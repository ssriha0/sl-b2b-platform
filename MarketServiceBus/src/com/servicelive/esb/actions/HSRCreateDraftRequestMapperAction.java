package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.translator.business.IBuyerService;
import com.newco.marketplace.translator.business.impl.BuyerService;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusResponse;
import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.HSRServiceOrder;
import com.servicelive.esb.dto.HSRServiceOrders;
import com.servicelive.esb.mapper.Mapper;
import com.servicelive.esb.mapper.MapperFactory;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.SLOrderService;

public class HSRCreateDraftRequestMapperAction extends AbstractEsbSpringAction {
	
	Logger logger = Logger.getLogger(HSRCreateDraftRequestProcessorAction.class);
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	private IBuyerService buyerService = null;
	private BuyerCredentials buyerCreds=null;
	private SLOrderService slOrderService = null;
	
	public Message mapData(Message message) throws Exception{
				
		logger.info(new StringBuffer("**** Invoking HSRCreateDraftRequestMapperAction ---->")
		.append(message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH)));
	    List<HSRServiceOrder> serviceOrderList = null;
	      List<CreateDraftRequest> createDraftReqList = new ArrayList<CreateDraftRequest>();
	
	    //Read the Unmarshalled Data  from message body
	    HSRServiceOrders serviceOrders = (HSRServiceOrders) message.getBody()
		.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
	   
		    String client = (String) message.getBody().get(MarketESBConstant.CLIENT_KEY);
		
		    Mapper dataMapper = MapperFactory.getInstance(client, MapperFactory.SERVICE_ORDER);
		    logger.debug("********dataMapper="+dataMapper);
		    
		    if(serviceOrders!= null ){
		    	serviceOrderList = serviceOrders.getServiceOrders();
		    	logger.debug("********serviceOrderList="+serviceOrderList);
				
				if(serviceOrderList == null) {
					String inputFilefeedName = (String)message.getBody().get(MarketESBConstant.FILE_FEED_NAME);
					ExceptionHandler.handle(client, new String((byte[]) message.getBody().get()), inputFilefeedName,
						getClass().getName() + " reports: Unrecoverable error occurred. Please process the file again", 
						message.getBody().get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH));
					throw new Exception("----Unmarshalled ServiceOrder object(s) not found!----");
				}
		   		Iterator<HSRServiceOrder> hsrServiceOrderIterator = serviceOrderList.iterator();
		   		while(hsrServiceOrderIterator.hasNext()){
		   			HSRServiceOrder serviceOrder = hsrServiceOrderIterator.next();
		   		    //The injected orders SHOULD NOT BE PROCESSED if the status of the SO in ServiceLive is Cancelled/Closed/Voided/Deleted.
					GetSOStatusResponse getSOStatusResponse = getSOStatus(serviceOrder, client);
					String soStatus=getSOStatusResponse.getSLServiceOrderStatus();
					if(StringUtils.isEmpty(soStatus)){
						soStatus=StringUtils.EMPTY;
					}
					if(!(soStatus.equals(MarketESBConstant.CANCELLED_STATUS) ||soStatus.equals(MarketESBConstant.CLOSED_STATUS) 
							|| soStatus.equals(MarketESBConstant.VOIDED_STATUS)|| soStatus.equals(MarketESBConstant.DELETED_STATUS) 
							||	soStatus.equals(MarketESBConstant.COMPLETED_STATUS) )){
						try{
							CreateDraftRequest draftReq = (CreateDraftRequest)dataMapper.mapData(serviceOrder);
							createDraftReqList.add(draftReq);
						}catch( Exception e ){
							StringBuilder orderString = 
								new StringBuilder( serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber() );
							StringBuilder msg = 
								new StringBuilder( "Unrecoverable error, removing from list: " )
								.append( orderString );
							logger.error( msg, e );
							// REMOVE DIRTY SO FROM LIST
							hsrServiceOrderIterator.remove();
						}
					}else{
						logger.info(new StringBuffer("Webservice call showed SO in ").append(soStatus).append(" status - not processing the update"));
						//WS call to add SO note
						addSONote(serviceOrder, client);
						//Remove the SO from the list
						hsrServiceOrderIterator.remove();
					}	
		   			
		   		}
		   	}
		    	
			
			logger.debug("********Mapping successfully completed for "+serviceOrderList.size()+" objects.");
			/* check if NEW or UPDATE file and populate  ordersList into respective message obj*/
			Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
			String inputFileName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
			if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.NEW_FILE_PREFIX) )){
				message.getBody().add(MarketESBConstant.MAPPED_OBJ_GRAPH_NEW, createDraftReqList);
			}else if(StringUtils.isNotBlank(inputFileName) &&  (inputFileName.startsWith(HSRFieldNameConstants.UPDATE_FILE_PREFIX) )){
				message.getBody().add(MarketESBConstant.MAPPED_OBJ_GRAPH_UPDATE, createDraftReqList);			
			}
		
			return message;

	}
	
	
	/**
	 * This method call the getSOStatus webservice method to get the soStatus.
	 * @param serviceOrder
	 * @return GetSOStatusResponse
	 * @throws Exception
	 */
	private GetSOStatusResponse getSOStatus(HSRServiceOrder serviceOrder, String client) throws Exception { 
		slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
		String serviceLiveWebServiceEndPointURL = "NOT A WEBSERVICE CALL";//Constants.MARKET_WS_URL;
		logger.info(new StringBuffer("=========== Invoking ServiceLive WebService URL: ")
				.append(serviceLiveWebServiceEndPointURL));

		GetSOStatusRequest getSOStatusRequest = new GetSOStatusRequest();
		String unitNoOrderNo = serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();			
		if(buyerCreds == null){
			//fetch credential if not already obtained
			String userName = "ServiceFlexibility";// (String) resourceBundle.getObject(client + "_USER_ID");		
			buyerService = (BuyerService) SpringUtil.factory.getBean("BuyerService");
			buyerCreds = buyerService.getBuyerCredentials(userName);
		}
		getSOStatusRequest.setUserId(buyerCreds.getUsername());
		getSOStatusRequest.setPassword(buyerCreds.getPassword());
		getSOStatusRequest.setBuyerId(buyerCreds.getBuyerID());
		getSOStatusRequest.setPasswordFlag(MarketESBConstant.INTERNAL);
		getSOStatusRequest.setUniqueCustomReferenceValue(unitNoOrderNo);		 
		GetSOStatusResponse getSOStatusResponse = slOrderService.getSOStatus(getSOStatusRequest);
		return getSOStatusResponse;
	}
	
	/**
	 * The method call the web service to add an SO Note
	 * @param serviceOrder
	 * @throws Exception
	 */
	private void addSONote(HSRServiceOrder serviceOrder,String client) throws Exception { 
		slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
		String serviceLiveWebServiceEndPointURL = Constants.MARKET_WS_URL;
		logger.info("\n\n=========== Invoking ServiceLive WebService URL: " + serviceLiveWebServiceEndPointURL);
		slOrderService.setServiceOrderEndPointUrl(serviceLiveWebServiceEndPointURL);
		if(buyerCreds == null){
			//fetch credential if not already obtained
			String userName = (String) resourceBundle.getObject(client + "_USER_ID");		
			buyerService = (BuyerService) SpringUtil.factory.getBean("BuyerService");
			buyerCreds = buyerService.getBuyerCredentials(userName);
		}
		String unitNoOrderNo=serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();
		ClientServiceOrderNoteRequest request = new ClientServiceOrderNoteRequest();
		request.setUserId(buyerCreds.getUsername());
		request.setPassword(buyerCreds.getPassword());
		request.setBuyerId(buyerCreds.getBuyerID());
		request.setPasswordFlag(MarketESBConstant.INTERNAL);
		request.setOrderIDString(unitNoOrderNo);
		request.setNote(MarketESBConstant.NO_UPDATE_NOTE_MESSAGE_HSR);
		request.setSubject(MarketESBConstant.NO_UPDATE_NOTE_SUBJECT_HSR);
		request.setClientId(MarketESBConstant.Client.HSR);
		request.setRoleId(OrderConstants.BUYER_ROLEID);
		request.setCreatedBy(OrderConstants.NEWCO_DISPLAY_SYSTEM);
		request.setNoteType(OrderConstants.SO_NOTE_PUBLIC_ACCESS);
		slOrderService.addClientNote(request);		
	}
	

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public HSRCreateDraftRequestMapperAction(ConfigTree config) {
		super.configTree = config;
			
	}
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public HSRCreateDraftRequestMapperAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
		
	}

	
}
