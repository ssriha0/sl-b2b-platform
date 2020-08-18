
package com.servicelive.esb.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.translator.business.IApplicationPropertiesService;
import com.newco.marketplace.translator.business.IBuyerService;
import com.newco.marketplace.translator.business.IStagingService;
import com.newco.marketplace.translator.business.ITranslationService;
import com.newco.marketplace.translator.business.impl.BuyerService;
import com.newco.marketplace.translator.dto.BuyerCredentials;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.translator.util.SpringUtil;
import com.newco.marketplace.webservices.dao.ShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.ShcOrderTransaction;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusResponse;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.Address;
import com.servicelive.esb.dto.JobCode;
import com.servicelive.esb.dto.JobCodes;
import com.servicelive.esb.dto.Logistics;
import com.servicelive.esb.dto.LogisticsMerchandise;
import com.servicelive.esb.dto.LogisticsOrder;
import com.servicelive.esb.dto.Merchandise;
import com.servicelive.esb.dto.Messages;
import com.servicelive.esb.dto.RepairLocation;
import com.servicelive.esb.dto.SalesCheck;
import com.servicelive.esb.dto.SalesCheckItem;
import com.servicelive.esb.dto.SalesCheckItems;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.dto.ServiceOrderCustomer;
import com.servicelive.esb.dto.ServiceOrders;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.esb.service.SLOrderService;
import com.servicelive.util.ShcOrderSkuHashSet;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;

/**
 * An ESB Action responsible for reading XML Payload from the input file feed to unmarshal it into 
 * a java object graph and set it into the message body for other actions to use.
 * @author pbhinga
 *
 */

public class ServiceOrdersUnMarshallerAction extends AbstractEsbSpringAction {

	private Logger logger = Logger.getLogger(ServiceOrdersUnMarshallerAction.class);
	private IStagingService stagingService = null;
	private ITranslationService translationService = null;
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	private IBuyerService buyerService = null;
	private BuyerCredentials buyerCreds=null;
	private SLOrderService slOrderService = null;
	private Integer buyer_id=1000;
	
	public static final Class<?>[] classes = new Class[] { Address.class, JobCode.class, JobCodes.class, 
		Logistics.class, LogisticsOrder.class,
		LogisticsMerchandise.class,
		Merchandise.class, Messages.class,
		RepairLocation.class,
		ServiceOrders.class, ServiceOrder.class,
		ServiceOrderCustomer.class, SalesCheck.class,
		SalesCheckItem.class, SalesCheckItems.class };
	
		
		/**
	 * Method to process the message that contains the XML Payload. This method is deprecated. 
	 * Use unmarshalCustomPayload instead.
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public Message unmarshalPayload(Message message) {		
		logger.info("Inside ServiceOrdersUnMarshallerAction.unmarshalPayload() method>>>>>>>>>>>");
		// Please do not remove following lines because they are required for debugging locally
		//Constants.MARKET_WS_URL = "http://localhost/marketws/services/ServiceOrderSEI";
		//Constants.STAGING_WS_URL = "http://localhost/omsstaging/services/StageOrderSEI";
		Constants.MARKET_WS_URL = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getServiceLiveEndpoint();
		Constants.STAGING_WS_URL = ((IApplicationPropertiesService)SpringUtil.factory.getBean("ApplicationPropertiesService")).getStagingWebServiceEndPoint();
		
		
		Body body = message.getBody();
		Object xmlFeed = body.get();
		
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFilefeedName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		logger.info("*********** Processing the file feed : \""+inputFilefeedName+"\"");
		
		XStream xstream = new XStream();
		
		xstream.processAnnotations(classes);
		
		logger.info("Before unmarshalling the xml payload...");
		
		ByteArrayInputStream byteArrayInputStream = null;
		ServiceOrders serviceOrders = null;
		try {
			//Capture the input file feed name for future use (in event of an error)
			body.add(MarketESBConstant.FILE_FEED_NAME, inputFilefeedName);
			
			//Add the client key to the message to drive specific translation and mapping rules
			body.add(MarketESBConstant.CLIENT_KEY, MarketESBConstant.Client.OMS);
			
			//Unmarshal the input xml payload from the file feed
			byteArrayInputStream = new ByteArrayInputStream((byte[])xmlFeed);
			serviceOrders = (ServiceOrders) xstream.fromXML(byteArrayInputStream);
			logger.debug("Unmarshalled the bin payload into ServiceOrders obj="+serviceOrders);
			
			//Set the unmarshalled object onto message body with a key
			body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, serviceOrders);
			logger.info("Unmarshalled the java pojo's from the xml payload and set into message body...");
		} catch(ConversionException e) {
			String errorMsg = "Exception caught UnMarshalling file: " + inputFilefeedName;
			logger.error( errorMsg );
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					new String((byte[]) message.getBody().get()), 
					inputFilefeedName,
					errorMsg, 
					new String((byte[]) xmlFeed),
					e);
		} finally {
			try {
				if (byteArrayInputStream != null) {
					byteArrayInputStream.close();
				}
			} catch (IOException ioEx) {
				logger.fatal("Could not close the byte array input stream", ioEx);
			}
		}
		
		// Persist Orders in Staging Area
		if (StringUtils.isNotBlank(inputFilefeedName) &&
					serviceOrders != null && serviceOrders.getServiceOrders() != null && !serviceOrders.getServiceOrders().isEmpty()) {
			StagingDetails stagingDetails = persistStagingData(inputFilefeedName, serviceOrders);
			body.add(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH, stagingDetails);
		}
		
		return message;
	} 
	

	/**
	 * Method to process the message that contains the XML Payload
	 * @param message
	 * @return
	 * @throws Exception
	 */
	
	public Message unmarshalCustomPayload(Message message) {
		logger.info(new StringBuffer("**** Invoking ServiceOrdersUnMarshallerAction ---->")
			.append(message.getBody()));	
		
		Body body = message.getBody();
		Object xmlFeed = body.get();		
		//Capture the input file feed name
		Object fileFeedPropertyValue = message.getProperties().getProperty(MarketESBConstant.ORIGINAL_FILE_FEED_NAME);
		String inputFilefeedName = String.valueOf(fileFeedPropertyValue == null ? "" : fileFeedPropertyValue);
		//logger.info("*********** Processing the file feed : \""+inputFilefeedName+"\"");
		
		ServiceOrders serviceOrders = null;
	
		try {
			//Capture the input file feed name for future use (in event of an error)
			body.add(MarketESBConstant.FILE_FEED_NAME, inputFilefeedName);			
			//Add the client key to the message to drive specific translation and mapping rules
			body.add(MarketESBConstant.CLIENT_KEY, MarketESBConstant.Client.OMS);
			
			if (xmlFeed instanceof ServiceOrders) {
				serviceOrders = (ServiceOrders)xmlFeed; 
			}
			
			if (serviceOrders == null || serviceOrders.getServiceOrders() == null)
			{
				throw new ConversionException("Error in reading from message ");
			}
			if (!serviceOrders.getServiceOrders().isEmpty()) {
				mapDefaultValues(serviceOrders);
				// Persist Orders in Staging Area
				StagingDetails stagingDetails = persistStagingData(inputFilefeedName, serviceOrders);
				body.add(MarketESBConstant.UNMARSHALLED_STAGE_OBJ_GRAPH, stagingDetails);
				removeClosedOrders(serviceOrders.getServiceOrders());
				//Set the unmarshalled object onto message body with a key
				body.add(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH, serviceOrders);		
			}
				
		} catch(ConversionException e) {
			String errorMsg = "Exception caught UnMarshalling file: " + inputFilefeedName;
			logger.error( errorMsg );
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					new String((byte[]) message.getBody().get()), 
					inputFilefeedName,
					errorMsg, 
					new String((byte[]) xmlFeed),
					e);
		}
		if (serviceOrders.getServiceOrders().isEmpty()) {
			return null;
		}		
		return message;
	}
	
	/**
	 * This method is used to map default values for firstName, 
	 * Brand & Model when they are null/blank.
	 * @param serviceOrders
	 */
	private void mapDefaultValues(ServiceOrders serviceOrders) {
		List<ServiceOrder> serviceOrderList = serviceOrders.getServiceOrders();
		for(ServiceOrder serviceOrder : serviceOrderList){
			if(null!=serviceOrder.getCustomer() && StringUtils.isBlank(serviceOrder.getCustomer().getFirstName())){
				serviceOrder.getCustomer().setFirstName(MPConstants.UNKNOWN);
			}
			if(null != serviceOrder.getMerchandise()){
				if(StringUtils.isBlank(serviceOrder.getMerchandise().getBrand())){
					serviceOrder.getMerchandise().setBrand(MPConstants.UNKNOWN);
				}			
				if(StringUtils.isBlank(serviceOrder.getMerchandise().getModel())){
					serviceOrder.getMerchandise().setModel(MPConstants.UNKNOWN);
				}
			}
		}
	}

	private StagingDetails persistStagingData(String inputFilefeedName, ServiceOrders serviceOrders) {
		StagingDetails stagingDetails = null;
		try {
			//Populate staging details 
			stagingDetails = populateStagingData(serviceOrders,inputFilefeedName);
			stagingService = (IStagingService) SpringUtil.factory.getBean(MarketESBConstant.SL_STAGING_SERVICE);
			
			// SL-21931
			String userName =new String();
			logger.info("Buyer Id Service Order "+ serviceOrders.getBuyerId());
			if(null == serviceOrders.getBuyerId())
			{
				userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(buyer_id);
			}
			else
			{
				userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(Integer.parseInt(serviceOrders.getBuyerId()));
			}
			translationService = (ITranslationService) SpringUtil.factory.getBean("TranslationService");
			int buyerId = translationService.getauthorizeBuyerId(userName);
			
			stagingService.stageDataAfterUnMarshalling(stagingDetails, buyerId);
			populateStageShcOrderId(stagingDetails);
		} catch(Exception ex) {
			// Log and ignore error
			stagingDetails = null;
			logger.error("Unexpected exception while persisting staging data in UnMarshaller Action", ex);
		}
		return stagingDetails;
	}
	
	/**
	 * Method to populate the staging data from unmarshalled object
	 * @param ServiceOrders
	 * @param inputFilefeedName
	 * @return StagingDetails
	 */
	private StagingDetails populateStagingData(ServiceOrders serviceOrders, String inputFilefeedName ) {
		
		List<ShcOrder> shcOrders = new ArrayList<ShcOrder>();
		List<ServiceOrder> serviceOrderList = serviceOrders.getServiceOrders();
		// SL-21931
		String userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(Integer.parseInt(serviceOrders.getBuyerId()));
		
		for(ServiceOrder serviceOrder : serviceOrderList){
			//mapping to SHCOrder		
			ShcOrder shcOrder = populateShcOrder(userName, serviceOrder);
			//mapping to SHCOrderSku
			Set<ShcOrderSku> orderSkus = populateShcOrderSkus(userName,
					serviceOrder);
			//mapping to SHCMerchandise	
			Set<ShcMerchandise> shcMerchandises = populateShcMerchandises(
					userName, serviceOrder);
			//mapping to SHCTransaction
			Set<ShcOrderTransaction> shcOrderTransactions = populateShcOrderTransactions(
					inputFilefeedName, userName, serviceOrder);
			
			shcOrder.setShcOrderSkus(orderSkus);
			shcOrder.setShcMerchandises(shcMerchandises);
			shcOrder.setShcOrderTransactions(shcOrderTransactions);
			
			shcOrders.add(shcOrder);
		}
		
		StagingDetails stagingDetails = new StagingDetails() ;
		stagingDetails.setStageServiceOrder(shcOrders);
		
		return stagingDetails; 
	}
	
	private void populateStageShcOrderId(StagingDetails stagingDetails) {
		List<ShcOrder> shcOrderList = stagingDetails.getStageServiceOrder();
		ShcOrder[] shcOrderArray = null;
		if(shcOrderList!=null){
			shcOrderArray = shcOrderList.toArray(new ShcOrder[0]);
		}
		 
		ShcOrder savedOrder = null; 
		for (ShcOrder shcOrder : shcOrderArray) {
			if (shcOrder != null) {
				
				String orderNo = shcOrder.getOrderNo();
				String unitNo = shcOrder.getUnitNo();
				savedOrder = stagingService.getShcOrder(orderNo, unitNo);
				
				Integer shcOrderId = savedOrder.getShcOrderId();
				shcOrder.setShcOrderId(shcOrderId);				
			}
		}	
		
	}

	/**
	 * @param userName
	 * @param serviceOrder
	 * @return
	 */
	private ShcOrder populateShcOrder(String userName, ServiceOrder serviceOrder) {
		ShcOrder shcOrder = new ShcOrder();
		shcOrder.setOrderNo(serviceOrder.getServiceOrderNumber());
		shcOrder.setUnitNo(serviceOrder.getServiceUnitNumber());
		shcOrder.setModifiedBy(userName);
		String storeNum =  serviceOrder.getSalesCheck().getNumber();
		if (storeNum != null && storeNum.length() > 5) {
			storeNum = storeNum.substring(0, 5);
		}
		shcOrder.setStoreNo(storeNum);
		shcOrder.setOmsProcessId(serviceOrder.getProcessId());
		shcOrder.setSalesCheckNum(serviceOrder.getSalesCheck().getNumber());
		if (StringUtils.isNotBlank(serviceOrder.getSalesCheck().getTime())) {
			shcOrder.setSalesCheckDate(serviceOrder.getSalesCheck().getDate() + " " + serviceOrder.getSalesCheck().getTime());
		}
		else {
			shcOrder.setSalesCheckDate(serviceOrder.getSalesCheck().getDate() + " 00:00:00");
		}
		shcOrder.setPromisedDate(serviceOrder.getPromisedDate());
		shcOrder.setNpsStatus(serviceOrder.getServiceOrderStatusCode());
		return shcOrder;
	}

	/**
	 * @param inputFilefeedName
	 * @param userName
	 * @param serviceOrder
	 * @return
	 */
	private Set<ShcOrderTransaction> populateShcOrderTransactions(
			String inputFilefeedName, String userName, ServiceOrder serviceOrder) {
		Set<ShcOrderTransaction> shcOrderTransactions = new HashSet<ShcOrderTransaction>();
		ShcOrderTransaction shcOrderTransaction = new ShcOrderTransaction();
		shcOrderTransaction.setTransactionType(serviceOrder.getTransactionType());
		shcOrderTransaction.setModifiedBy(userName);
		XStream xstream = new XStream();
		xstream.processAnnotations(classes);
		shcOrderTransaction.setXmlFragment(xstream.toXML(serviceOrder));
		shcOrderTransaction.setInputFileName(inputFilefeedName);
		shcOrderTransactions.add(shcOrderTransaction);
		return shcOrderTransactions;
	}

	/**
	 * @param userName
	 * @param serviceOrder
	 * @return
	 */
	private Set<ShcMerchandise> populateShcMerchandises(String userName,
			ServiceOrder serviceOrder) {
		Set<ShcMerchandise> shcMerchandises = new HashSet<ShcMerchandise>();
		ShcMerchandise shcMerchandise = new ShcMerchandise();	
		shcMerchandise.setItemNo(serviceOrder.getMerchandise().getItemNumber());
		shcMerchandise.setCode(serviceOrder.getMerchandise().getCode());
		shcMerchandise.setDescription(serviceOrder.getMerchandise().getDescription());
		shcMerchandise.setSpecialty(serviceOrder.getMerchandise().getSpecialty());
		shcMerchandise.setBrand(serviceOrder.getMerchandise().getBrand());
		shcMerchandise.setModel(serviceOrder.getMerchandise().getModel());
		shcMerchandise.setSerialNumber(serviceOrder.getMerchandise().getSerialNumber());
		shcMerchandise.setDivisionCode(serviceOrder.getMerchandise().getDivision());
		shcMerchandise.setModifiedBy(userName);
		shcMerchandises.add(shcMerchandise);
		
		return shcMerchandises;
	}

	/**
	 * @param userName
	 * @param serviceOrder
	 * @return
	 */
	private Set<ShcOrderSku> populateShcOrderSkus(String userName,
			ServiceOrder serviceOrder) {
		Set<ShcOrderSku> orderSkus = new ShcOrderSkuHashSet<ShcOrderSku>();
		List<JobCode> jobcodes = serviceOrder.getJobCodes().getJobCodeList();
		HashMap<String,Integer> jobCodeMap= new HashMap<String,Integer>();
		Integer jobcodeCount = 0;
		if(!jobcodes.isEmpty()){
			for(JobCode jobcode : jobcodes) {		
				if(!Constants.CALL_COLLECT_COVERAGE_TYPE.equals(jobcode.getCoverage())){
					ShcOrderSku shcOrderSku = new ShcOrderSku();
					if(jobCodeMap.containsKey(jobcode.getNumber())){
						jobcodeCount = jobCodeMap.get(jobcode.getNumber());
						jobcodeCount++;
						jobCodeMap.remove(jobcode.getNumber());
						jobCodeMap.put(jobcode.getNumber(), jobcodeCount);
					}else{
						jobCodeMap.put(jobcode.getNumber(), MarketESBConstant.DEFAULT_SKU_QTY);
					}
					shcOrderSku.setSku(jobcode.getNumber());
					shcOrderSku.setChargeCode(jobcode.getChargeCode());
					if(MarketESBConstant.PERMIT_SKU.equals(jobcode.getNumber())) {
						shcOrderSku.setPermitSkuInd(MarketESBConstant.PERMIT_SKU_IND_ONE);
					} else {
						shcOrderSku.setPermitSkuInd(MarketESBConstant.PERMIT_SKU_IND_ZERO);
					}
					shcOrderSku.setCoverage(jobcode.getCoverage());
					shcOrderSku.setType(jobcode.getType());
					if(StringUtils.isNotEmpty(jobcode.getAmount())){
						shcOrderSku.setSellingPrice(new Double(jobcode.getAmount())); 
					}
					shcOrderSku.setModifiedBy(userName);
					shcOrderSku.setQty(MarketESBConstant.DEFAULT_SKU_QTY);
					shcOrderSku.setDescription(jobcode.getDescription());
					shcOrderSku.setStatus(Constants.DEFAULT_SKU_STATUS);
					shcOrderSku.setAddOnInd(new Integer(0).shortValue());
					orderSkus.add(shcOrderSku);
				}
			}
			Integer qty = 0;
			for(ShcOrderSku shcOrderSku : orderSkus){
				if(jobCodeMap.containsKey(shcOrderSku.getSku())){
					qty = jobCodeMap.get(shcOrderSku.getSku());
					shcOrderSku.setQty(qty);
				}
			}
		
		}
		return orderSkus;
	}
	/**
	 * This method call the getSOStatus webservice method to get the soStatus.
	 * @param serviceOrder
	 * @return GetSOStatusResponse
	 * @throws Exception
	 */
	private GetSOStatusResponse getSOStatus(ServiceOrder serviceOrder) { 
		try {
			slOrderService = (SLOrderService) getBeanFactory().getBean(MarketESBConstant.SL_ORDER_SERVICE);
		} catch (Exception e) {
			logger.error(new StringBuffer(
				"Fatal error getting SLOrderService Bean, returning NULL"),e);
			return null;
		}
		//Invoke the Market Webservice to check status
		GetSOStatusRequest soStatusRequest = new GetSOStatusRequest();
		String orderString = serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();			
		if(buyerCreds == null){
			//fetch credential if not already obtained
			// SL-21931
			String userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(buyer_id);
			
			buyerService = (BuyerService) SpringUtil.factory.getBean("BuyerService");
			buyerCreds = buyerService.getBuyerCredentials(userName);
		}
		soStatusRequest.setUserId(buyerCreds.getUsername());
		soStatusRequest.setPassword(buyerCreds.getPassword());
		soStatusRequest.setBuyerId(buyerCreds.getBuyerID());
		soStatusRequest.setPasswordFlag(MarketESBConstant.INTERNAL);
		soStatusRequest.setUniqueCustomReferenceValue(orderString);		 
		GetSOStatusResponse soStatusResponse = null;
		try {
			soStatusResponse = slOrderService.getSOStatus(soStatusRequest);
		} catch (Exception e) {
			logger.error(new StringBuffer("Setting SO status to EMPTY:")
				.append(soStatusResponse.getSLServiceOrderId()),e);
			soStatusResponse.setSLServiceOrderStatus(StringUtils.EMPTY);
		}
		return soStatusResponse;
	}
	private void addSONote(ServiceOrder serviceOrder) throws Exception 
	{ 
		if(buyerCreds == null){
			//fetch credential if not already obtained
			// SL-21931
			String userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(buyer_id);
			
			buyerService = (BuyerService) SpringUtil.factory.getBean("BuyerService");
			buyerCreds = buyerService.getBuyerCredentials(userName);
		}
		String orderString = serviceOrder.getServiceUnitNumber() + serviceOrder.getServiceOrderNumber();
		ClientServiceOrderNoteRequest request = new ClientServiceOrderNoteRequest();
		request.setUserId(buyerCreds.getUsername());
		request.setPassword(buyerCreds.getPassword());
		request.setBuyerId(buyerCreds.getBuyerID());
		request.setPasswordFlag(MarketESBConstant.INTERNAL);
		request.setOrderIDString(orderString);
		request.setNote(MarketESBConstant.NO_UPDATE_NOTE_MESSAGE);
		request.setSubject(MarketESBConstant.NO_UPDATE_NOTE_SUBJECT);
		request.setClientId(MarketESBConstant.Client.OMS);
		request.setRoleId(OrderConstants.BUYER_ROLEID);
		request.setCreatedBy(OrderConstants.NEWCO_DISPLAY_SYSTEM);
		request.setNoteType(OrderConstants.SO_NOTE_PUBLIC_ACCESS);
		slOrderService.addClientNote(request);		
	}
	private void removeClosedOrders(List<ServiceOrder> serviceOrderList)
	{
		Iterator<ServiceOrder> serviceOrderListIterator = serviceOrderList.iterator();
		while(serviceOrderListIterator.hasNext())
		{
			ServiceOrder serviceOrder = (ServiceOrder) serviceOrderListIterator.next();
			
//			Don't process of ServiceLive status is 
//			Cancelled/Closed/Voided/Deleted.
			GetSOStatusResponse soStatusResponse = getSOStatus(serviceOrder);
			if(soStatusResponse == null)
				continue;
			String soStatus = soStatusResponse.getSLServiceOrderStatus();
			if(StringUtils.isEmpty(soStatus))
			{
				soStatus = StringUtils.EMPTY;
			}
			if(	soStatus.equals(MarketESBConstant.CANCELLED_STATUS) 
				||soStatus.equals(MarketESBConstant.CLOSED_STATUS) 
				|| soStatus.equals(MarketESBConstant.VOIDED_STATUS)
				|| soStatus.equals(MarketESBConstant.DELETED_STATUS))
			{
				try {
					addSONote(serviceOrder);
				} catch (Exception e) {
					logger.error( "Error adding client note", e);
				}
				logger.info(new StringBuffer("(NOTTA)Webservice call showed SO in ")
					.append(soStatus).append(" status - not processing the update"));
				serviceOrderListIterator.remove();
			}
		}
	}
	
	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public ServiceOrdersUnMarshallerAction(ConfigTree config) { super.configTree = config; }
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public ServiceOrdersUnMarshallerAction() {logger.warn("***** This constructor is for Test Cases only *****"); }

}

