package com.servicelive.esb.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.business.IHSRStagingService;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.HSRMerchandise;
import com.servicelive.esb.dto.HSRProtectionAgreement;
import com.servicelive.esb.dto.HSRRepairLocation;
import com.servicelive.esb.dto.HSRServiceOrder;
import com.servicelive.esb.dto.HSRServiceOrders;
import com.servicelive.esb.dto.HSRSoCustomer;
import com.servicelive.esb.dto.HSRSoDatesAndTimes;
import com.servicelive.esb.service.ExceptionHandler;
import com.servicelive.staging.domain.LoggableBaseDomain;
import com.servicelive.staging.domain.hsr.HSRStageOrder;
import com.servicelive.staging.domain.hsr.HSRStageOrderClientOutflowUpdate;
import com.servicelive.staging.domain.hsr.HSRStageOrderCustomer;
import com.servicelive.staging.domain.hsr.HSRStageOrderMerchandise;
import com.servicelive.staging.domain.hsr.HSRStageOrderProtectionAgreement;
import com.servicelive.staging.domain.hsr.HSRStageOrderRepairLocation;
import com.servicelive.staging.domain.hsr.HSRStageOrderSchedule;
import com.servicelive.staging.domain.hsr.HSRStageOrderTransaction;
import com.servicelive.staging.domain.hsr.HSRStageOrderUpdate;
import com.servicelive.staging.domain.hsr.HSRStageOrderUpdatePart;
import com.servicelive.staging.domain.hsr.HSRStageOrders;
import com.servicelive.util.MarketESBUtil;

public class HSRStagingAction extends AbstractEsbSpringAction{
	
	Logger logger = Logger.getLogger(HSRStagingAction.class);
	/*
	 * This Service is used  for perssisting the information 
	 */
	private IHSRStagingService stagingService = null;

	public Message stageData(Message message){
		logger.info("Entering into "+ this.getClass().getCanonicalName());
		Body body = message.getBody();
		HSRServiceOrders serviceOrders = (HSRServiceOrders) body.get(MarketESBConstant.UNMARSHALLED_OBJ_GRAPH);
		String inputFileName =  serviceOrders.getInputFileName();
		try{
			List<HSRStageOrder>  ordersToStage = getPopulatedStagingDetails(serviceOrders);
			stagingService = (IHSRStagingService) SpringUtil.factory.getBean(MarketESBConstant.HSR_STAGING_SERVICE);
			//String userName = (String) resourceBundle.getObject(MarketESBConstant.Client.OMS + "_USER_ID");
			HSRStageOrders stageOrdersList = new HSRStageOrders();
			stageOrdersList.setInputFilenameHoldingThisOrders(serviceOrders.getInputFileName());
			stageOrdersList.setStageOrders(ordersToStage);
			stagingService.persistStageOrder(stageOrdersList);
		} catch(Exception e) {
			String errorMsg = "Exception caught in STAGING file: " + inputFileName;
			logger.error( errorMsg );
			ExceptionHandler.handle((String) message.getBody().get(MarketESBConstant.CLIENT_KEY), 
					new String((byte[]) message.getBody().get()), 
					inputFileName,
					errorMsg, 
					null,
					e);
		} 
		return message;
	}
	
	private List<HSRStageOrder> getPopulatedStagingDetails(HSRServiceOrders serviceOrders ) throws Exception{
		List<HSRStageOrder> stageOrderList = new ArrayList<HSRStageOrder>();
		for (HSRServiceOrder order : serviceOrders.getServiceOrders()) {

			// If the Order came in Update File
			if (order.getIsCameInUpdateFile()) {
				try {
						populateUpdateStageOrders(serviceOrders, stageOrderList,order);
				} catch (Exception e) {
					// TODO some logging for
					e.printStackTrace();
				}
			}
			// ELSE consider them are new Orders
			else {
				populateNewStageOrders(serviceOrders, stageOrderList, order);
			}

		}
		return stageOrderList;
	}

	/**
	 *  This method is main method which getting called when Update FILE is being processed.
	 *  The Update can be having two sceanario, 1) UPDATE AFTER NEW   2) UDPATE PRIOR NEW 
	 *  both sceanrio has been handled in this method.
	 * @param serviceOrders
	 * @param stageOrderList
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private void  populateUpdateStageOrders (HSRServiceOrders serviceOrders, List<HSRStageOrder> stageOrderList,HSRServiceOrder order) throws Exception {
			//Step1 : check if the Order is already available. There might be situation where UPDATE comes prior to NEW
			// When Update come prior to NEW we would just create the the DRAFT WITH SUBSTATUS Missing INformation)
			 HSRStageOrder stageOrder = stagingService.getHSRStageOrderForOrderNOAndUnitNO( order.getServiceOrderNumber().trim(),order.getServiceUnitNumber().trim());
			//Step2 : Fetch the Already available ( or Use the alread available from the step
			 if(stageOrder != null) {
				 applyUpdatesToExistingStageOrder(stageOrder,order);
				 populateStageOrderTransaction(stageOrder,order,serviceOrders.getInputFileName(),MarketESBConstant.HSR_TX_MODE_UPDATE);
			 }else {
				 stageOrder = getPopulateUpdatePriorNewStageOrders(serviceOrders,order);
				 flagESBOrderWithUpdatePriorNew(order,Boolean.TRUE);
			 }
			 stageOrderList.add(stageOrder);
	}

	/**
	 * This is to flag the ESVB order object to facilitat the UPDATE PRIOR to NEW Sceanrio during the translation.
	 * @param order
	 */
	private void flagESBOrderWithUpdatePriorNew(HSRServiceOrder order,Boolean value) {
		order.setIsUpdateCamePriorToNew(value);
	}

	/**
	 * This method DEALS ONLY the NEW ORDER.
	 * @param serviceOrders
	 * @param stageOrderList
	 * @param order
	 */
	private void populateNewStageOrders(final HSRServiceOrders serviceOrders,
			List<HSRStageOrder> stageOrderList, HSRServiceOrder order) {
		HSRStageOrder stageOrder = new HSRStageOrder();
		pouplateStageOrder(stageOrder,order);
		populateStageOrderTransaction(stageOrder,order,serviceOrders.getInputFileName(),MarketESBConstant.TX_MODE_NEW);
		popluateNewStageOrderCustomerInfo(stageOrder,order);
		popluateNewStageOrderMerchandiseInfo(stageOrder,order);
		popluateNewStageOrderClientOutFlowInfo(stageOrder);
		popluateNewStageOrderSchedulingInfo(stageOrder,order);
		popluateNewStageOrderProtectionAgreementInfo(stageOrder,order);
		popluateNewStageOrderRepairLocationInfo(stageOrder,order);
		stageOrderList.add(stageOrder);
	}
	
	/**
	 * 
	 * @param serviceOrders
	 * @param stageOrderList
	 * @param order
	 */
	private HSRStageOrder getPopulateUpdatePriorNewStageOrders(final HSRServiceOrders serviceOrders, HSRServiceOrder order) {
		HSRStageOrder stageOrder = new HSRStageOrder();
		pouplateStageOrder(stageOrder,order);
		 populateStageOrderTransaction(stageOrder,order,serviceOrders.getInputFileName(),MarketESBConstant.HSR_TX_MODE_UPDATE_PRIOR_NEW);
		 return stageOrder;
	}
	
	
	/** 
	 * This method update the INFORMATION already exisitn on the hsr_order table.
	 * At this moment the update file is only coming with STATUS_CODE so we are updating only8 the status code.s
	 * @param existingstageOrder
	 * @param order
	 */
	private void applyUpdatesToExistingStageOrder(HSRStageOrder existingstageOrder, HSRServiceOrder order){
		existingstageOrder.setOrderStatusCode(order.getServiceOrderStatusCode());
		populateStageOrderUpdates( existingstageOrder,  order);
	}
	
	private void populateStageOrderUpdates(HSRStageOrder existingstageOrder, HSRServiceOrder orderCameIn){
		HSRStageOrderUpdate update = new HSRStageOrderUpdate();
		update.setFiller(orderCameIn.getFiller());
		update.setModifyingUnitId(orderCameIn.getModifyingUnitId());
		update.setEmpIdNumber(orderCameIn.getEmployeeIDNumber());
		if(orderCameIn.getDatesAndTimes() != null) {
			update.setUpdatedModifiedDate(orderCameIn.getDatesAndTimes().getModifiedDate());
			update.setUpdatedModifiedTime(orderCameIn.getDatesAndTimes().getModifiedTime());
		}
		handleDates(Boolean.FALSE,update);
		
		HSRStageOrderUpdatePart updateParts = new HSRStageOrderUpdatePart();
		//TODO Map the parts as soon as the method available on the ESB POJO
		//but for now just ading blank ROW
		//You may need to iteratr over 6 times or so based on what came in. in PILOT phase they told us to IGNORE for now
		updateParts.setUpdatedDivisionNO("");
		updateParts.setPartNumber("ESB TO PARTS NOTMAPPED YET");
		handleDates(Boolean.FALSE,updateParts);
		update.getParts().add(updateParts);
		existingstageOrder.getUpdates().add(update);
	}
	/**
	 * 
	 * @param stageOrder
	 * @param order
	 */
	private void pouplateStageOrder(HSRStageOrder stageOrder,HSRServiceOrder order ){
		/* This would copy all the Blank String to the Staggin Tables, I know its gonna consume space. I talked to Suganya and she said they need to STORE BALNK rather than NULL.
		 * She Thinks that blank value represents information for reporting purpose
		 */
		MarketESBUtil.mapByReflection(MarketESBUtil.MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT,order,stageOrder);
		//TODO fix here for Update currently every thing is treated as NEW
		handleDates(Boolean.FALSE,stageOrder);
	}
	/**
	 * This method maps the Csutomer information
	 * @param stageOrder
	 * @param order
	 */
	private void popluateNewStageOrderCustomerInfo(HSRStageOrder stageOrder,HSRServiceOrder order ){
		HSRStageOrderCustomer customer = new HSRStageOrderCustomer();
		HSRSoCustomer esbCustomer = order.getCustomer();
		if(esbCustomer != null ) {
			MarketESBUtil.mapByReflection(MarketESBUtil.MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT,esbCustomer,customer);
			handleDates(Boolean.FALSE,customer);
			//Why do I do this ?? .. THINK 
			stageOrder.setCustomer(customer);
			customer.setHsrOrderId(stageOrder);
		}
		
	}
	private void popluateNewStageOrderMerchandiseInfo(HSRStageOrder stageOrder,HSRServiceOrder order ){
		HSRStageOrderMerchandise merchandise = new HSRStageOrderMerchandise();
		HSRMerchandise esbmerchandise = order.getMerchandise();
		if(esbmerchandise != null ) {
			MarketESBUtil.mapByReflection(MarketESBUtil.MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT,esbmerchandise,merchandise);
			handleDates(Boolean.FALSE,merchandise);
			if(order.getDatesAndTimes()!= null) {
				merchandise.setPurchaseDate( order.getDatesAndTimes().getPurchaseDate());
				merchandise.setOriginalDeliveryDate(order.getDatesAndTimes().getOriginalDeliveryDate());
			}
			stageOrder.setMerchandise(merchandise); 
			merchandise.setHsrOrderId(stageOrder);
		}
		
	}
	
	private void popluateNewStageOrderSchedulingInfo(HSRStageOrder stageOrder,HSRServiceOrder order ){
		HSRStageOrderSchedule schedule = new HSRStageOrderSchedule();
		HSRSoDatesAndTimes esbschudle = order.getDatesAndTimes();
		if(esbschudle != null ) {
			MarketESBUtil.mapByReflection(MarketESBUtil.MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT,esbschudle,schedule);
			handleDates(Boolean.FALSE,schedule);
			
			stageOrder.setSchedule(schedule);
			schedule.setHsrOrderId(stageOrder);
		}
		
	}
	
	private void popluateNewStageOrderProtectionAgreementInfo(HSRStageOrder stageOrder,HSRServiceOrder order ){
		HSRStageOrderProtectionAgreement agreement = new HSRStageOrderProtectionAgreement();
		HSRProtectionAgreement esbagreement = order.getProtectionAgreement();
		if(esbagreement != null ) {
			agreement.setPaType(esbagreement.getAgreementType());
			agreement.setPaNumber(esbagreement.getAgreementNumber());
			agreement.setPaLtestExpDate(esbagreement.getAgreementExpDate());
			agreement.setPaPlanType(esbagreement.getAgreementPlanType());
			handleDates(Boolean.FALSE,agreement);
			
			stageOrder.setProtectionAgrement(agreement); 
			agreement.setHsrOrderId(stageOrder);
		}
		
	}
	
	private void popluateNewStageOrderRepairLocationInfo(HSRStageOrder stageOrder,HSRServiceOrder order ){
		HSRStageOrderRepairLocation locn = new HSRStageOrderRepairLocation();
		HSRRepairLocation esbLocn = order.getRepairLocation();
		if(esbLocn != null ) {
			locn.setServiceLocnNotes(esbLocn.getContactName());
			locn.setServiceLocnTypeInd(esbLocn.getBusinessAddressInd());
			locn.setServiceLocnCode(esbLocn.getServiceLocationCode());
						
			if(esbLocn.getAddress() != null) {
				locn.setCity(esbLocn.getAddress().getCity());
				locn.setRepairStreet1(esbLocn.getAddress().getStreetAddress1());
				locn.setRepairStreet2(esbLocn.getAddress().getStreetAddress2());
				locn.setStateCode(esbLocn.getAddress().getState());
				locn.setPostalCode(esbLocn.getAddress().getZipCode());
				
			}
			
			
			handleDates(Boolean.FALSE,locn);
			
			stageOrder.setRepairLocation(locn); 
			locn.setHsrOrderId(stageOrder);
		}
		
	}
	private void popluateNewStageOrderClientOutFlowInfo(HSRStageOrder stageOrder) {
		HSRStageOrderClientOutflowUpdate clientOutFlowUpdate = new HSRStageOrderClientOutflowUpdate();
		handleDates(Boolean.FALSE,clientOutFlowUpdate);
		stageOrder.setClientOutflowUpdate(clientOutFlowUpdate);
		clientOutFlowUpdate.setHsrOrderId(stageOrder);
	}

	/**
	 * 
	 * @param isUpdate
	 * @param domain
	 */
	private void handleDates(Boolean isUpdate ,LoggableBaseDomain domain) {
		if(!isUpdate){
			domain.setCreatedDate(new Date());
		}
		domain.setModifiedDate(new Date());
		domain.setModifiedBy("SYSTEM");
	}
	/**
	 * This method will pouplate TRASNACTION table for the HST Update.
	 * There will ( should nOT ) be any sceanrio when you need to update the record in this table, you always create new
	 * @param stageOrder
	 * @param order
	 * @param inputFileName
	 * @param transactionStatus
	 */
	private void populateStageOrderTransaction(HSRStageOrder stageOrder,HSRServiceOrder order,String inputFileName,String transactionStatus){
		HSRStageOrderTransaction stageOrderTran = new HSRStageOrderTransaction();
		stageOrderTran.setInputFileName(inputFileName);
		stageOrderTran.setInputFragment(order.getInputFragment());
		stageOrderTran.setTransactionType(transactionStatus);
		handleDates(Boolean.FALSE,stageOrderTran);
		stageOrder.getTransactions().add(stageOrderTran);
	}

	/**
	 * The Constructor which is called by ESB runtime to configure actions
	 * @param config
	 */
	public HSRStagingAction(ConfigTree config) {
		super.configTree = config;
		
	}
	
	/**
	 * Default Constructor for JUnit test cases
	 */
	public HSRStagingAction() {
		logger.warn("***** This constructor is for Test Cases only *****");
		
	}

	/**
	 * @return the stagingService
	 */
	public IHSRStagingService getStagingService() {
		return stagingService;
	}

	/**
	 * @param stagingService the stagingService to set
	 */
	public void setStagingService(IHSRStagingService stagingService) {
		this.stagingService = stagingService;
	}
	
	
}
