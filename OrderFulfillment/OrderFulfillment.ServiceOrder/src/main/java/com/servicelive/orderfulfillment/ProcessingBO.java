package com.servicelive.orderfulfillment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.servicelive.common.util.MoneyUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.servicelive.client.SimpleRestClient;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.SkuTaxCalculation;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.type.OrderStatusOnUpdate;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementService;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementServiceResolver;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.validation.IValidationUtil;
import com.sun.jersey.core.header.MediaTypes;

public class ProcessingBO {

	private ServiceOrderBO serviceOrderBO;
	private ServiceOrderGroupBO serviceOrderGroupBO;
    private QuickLookupCollection quickLookupCollection;
    private OrderEnhancementServiceResolver orderEnhancementServiceResolver;
    private Logger logger = Logger.getLogger(getClass());
    private IValidationUtil validationUtil;
    IBuyerPricingScheme commonPricingScheme;
    private LeadBO leadBO;

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String executeCreateWithGroups(Identification idfn, ServiceOrder so, Map<String, Object> pvars){
       
       logger.info("Inside ProcessingBO.executeCreateWithGroups method");
        long startTime = System.currentTimeMillis();
        OrderEnhancementService orderEnhancementService = orderEnhancementServiceResolver.resolveOrderEnhancementService(so.getBuyerId());
        if (orderEnhancementService != null) {
            if(orderEnhancementService.enhanceServiceOrder(so))
                setProcessVariablesForEnhancedOrder(so, pvars);
            logger.info(String.format("Finished with order enhancement - Inside ProcessingBO.executeCreateWithGroups method. Time taken is %1$d ms", System.currentTimeMillis() - startTime));
        }
        else{
    		//applying market adjustment rate for API buyers without enhancers
    		boolean isMARfeatureOn = commonPricingScheme.isMARfeatureOn(so.getBuyerId());
    		if(isMARfeatureOn && so.isCreatedViaAPI()){
    			commonPricingScheme.calculatePriceWithMAR(so);
    		}
    		
        }
        /*This method will persist an entry in so_price_change_history incase
		  *the service order is created via api for Facilities Bid Buyer*/
         if(so.isCreatedViaAPI() && so.getBuyerId().equals(OFConstants.FACILITIES_BUYER)){
        	commonPricingScheme.setSOPriceChangeHistory(so);
        	boolean isMARfeatureOn = commonPricingScheme.isMARfeatureOn(so.getBuyerId());
        	//To handle MAR feture if it is enabled for bid buyer and order creation via api
        	if(isMARfeatureOn){
    			commonPricingScheme.calculatePriceWithMAR(so);
    		}
         }
         
        // tax changes
 		if (null != so.getPrice()) {
 			if (quickLookupCollection.getBuyerFeatureLookup()
 					.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ALLOW_SKU_TAX, so.getBuyerId())) {
 				BigDecimal taxRateForLabor = getTaxRateForLabor(so);
 				BigDecimal taxRateForParts = getTaxRateForParts(so);
 				logger.info("calling calculateTaxesForLaborAndParts...");
 				calculateTaxesForLaborAndParts(so, taxRateForLabor, taxRateForParts);
 			} else {
 				logger.info("For buyers except Relay and TT");
 				so.getPrice().setTaxPercentLaborSL(BigDecimal.ZERO);
 				so.getPrice().setTaxPercentPartsSL(BigDecimal.ZERO);
 				so.getPrice().setDiscountPercentLaborSL(BigDecimal.ZERO);
 				so.getPrice().setDiscountPercentPartsSL(BigDecimal.ZERO);
 			}
 		} else {
 			logger.warn("so price node is null while creating order");
 		}
         
		String soId = serviceOrderBO.createServiceOrder(idfn, so, pvars);
        if (so.getCondAutoRouteRuleId() != null) {
            serviceOrderBO.setCarOrderIndicatorForProcess(soId);
        }
        
    	logger.info("R16_0 Servoice Order Id: "+soId);

        if(null != pvars && null !=pvars.get(ProcessVariableUtil.PROVIDER_LIST_SIZE)){
        	logger.info("Servoice Order Provider List Size: " + pvars.get(ProcessVariableUtil.PROVIDER_LIST_SIZE));
        }
        else if(null == pvars){
        	logger.info("R16_0 null process variables");
        }

		serviceOrderGroupBO.createServiceOrderGroup(idfn, so);
        logger.info(String.format("Finished with ProcessingBO.executeCreateWithGroups method. Time taken is %1$d ms", System.currentTimeMillis() - startTime));
		return soId;
	}
	
	private void calculateTaxesForLaborAndParts(ServiceOrder so, BigDecimal taxRateForLabor,
			BigDecimal taxRateForParts) {

		so.getPrice().setTaxPercentLaborSL(taxRateForLabor);

		logger.info("Labor base price of SO:----- " + so.getSpendLimitLabor() + " and labor tax: " + taxRateForLabor);
		if (taxRateForLabor.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal roundedLaborPrice = MoneyUtil.getRoundedMoneyBigDecimal(so.getSpendLimitLabor().multiply(taxRateForLabor.add(BigDecimal.ONE)).doubleValue());
			logger.info("roundedLaborPrice:----- " + roundedLaborPrice);

			so.setSpendLimitLabor(roundedLaborPrice);
			so.getPrice().setOrigSpendLimitLabor(so.getSpendLimitLabor());
			so.getPrice().setDiscountedSpendLimitLabor(so.getSpendLimitLabor());
			so.setInitialPrice(so.getSpendLimitLabor());
		}

		so.getPrice().setTaxPercentPartsSL(taxRateForParts);

		logger.info("Parts base price of SO:----- " + so.getSpendLimitParts() + " and parts tax: " + taxRateForParts);
		if (taxRateForParts.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal roundedPartsPrice =  MoneyUtil.getRoundedMoneyBigDecimal(so.getSpendLimitParts().multiply(taxRateForParts.add(BigDecimal.ONE)).doubleValue());
			logger.info("roundedPartsPrice:----- " + roundedPartsPrice); 

			so.setSpendLimitParts(roundedPartsPrice);
			so.getPrice().setOrigSpendLimitParts(so.getSpendLimitParts());
			so.getPrice().setDiscountedSpendLimitParts(so.getSpendLimitParts());
		}

		logger.info("*** new labor spend limit " + so.getSpendLimitLabor());
		logger.info("*** new parts spend limit " + so.getSpendLimitParts());

		so.getPrice().setDiscountPercentLaborSL(null == so.getPrice().getDiscountPercentLaborSL() ? BigDecimal.ZERO
				: so.getPrice().getDiscountPercentLaborSL());
		so.getPrice().setDiscountPercentPartsSL(null == so.getPrice().getDiscountPercentPartsSL() ? BigDecimal.ZERO
				: so.getPrice().getDiscountPercentPartsSL());
	}
	
	private BigDecimal getTaxRateForLabor(ServiceOrder so) {
		logger.info("Inside getTaxRateForSO method");
		BigDecimal taxRate = new BigDecimal(0);

		String taxServiceUrl = getTaxServiceUrl(OrderfulfillmentConstants.TAX_SERVICE_KEY);
		Long buyerId = so.getBuyerId();

		logger.info("Calculating the tax for Buyer" + buyerId);
		try {
			if (null == so.getTasks().get(0).getExternalSku()) {
				logger.info("Did not find sku");
				return taxRate;
			}
			String sku = so.getTasks().get(0).getExternalSku();
			String zip = so.getServiceLocation().getZip();
			String state = so.getServiceLocation().getState();
			SkuTaxCalculation taxUtil = new SkuTaxCalculation();

			logger.info("calling tax service...");

			return taxUtil.getTaxRateForSku(sku, zip, state, taxServiceUrl);
		} catch (Exception e) {
			logger.error("Error occurred while calculating the tax : " + e.getStackTrace());
		}

		logger.info("returning default tax 0 for buyer " + buyerId);
		return taxRate;
	}

	private BigDecimal getTaxRateForParts(ServiceOrder so) {
		logger.info("Inside getTaxRateForParts method");
		BigDecimal taxRate = new BigDecimal(0);

		String taxServiceUrl = getTaxServiceUrl(OrderfulfillmentConstants.TAX_SERVICE_KEY);
		Long buyerId = so.getBuyerId();

		logger.info("Calculating tax for Buyer" + buyerId);
		try {
			String sku = "partsSku";
			String zip = so.getServiceLocation().getZip();
			String state = so.getServiceLocation().getState();
			SkuTaxCalculation taxUtil = new SkuTaxCalculation();

			logger.info("calling tax service...");

			return taxUtil.getTaxRateForSku(sku, zip, state, taxServiceUrl);
		} catch (Exception e) {
			logger.error("Error occurred while calculating the tax : " + e.getStackTrace());
		}

		logger.info("returning default tax 0 for buyer " + buyerId);
		return taxRate;
	}
	
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String executeCreateLead(Identification idfn, LeadHdr lead, Map<String, Object> pvars){       
		logger.info("Inside ProcessingBO.executeCreateWithGroups method");
        long startTime = System.currentTimeMillis();        
		String leadId = leadBO.createLeadObject(idfn, lead, pvars);		
        logger.info(String.format("Finished with ProcessingBO.executeCreateLead method. Time taken is %1$d ms", System.currentTimeMillis() - startTime));        
		return leadId;
	}

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String updateClosedOrder(Identification idfn, ServiceOrder so, Map<String, Object> pvars) {
        if (buyerHasFeatureUpdateClosedOrder(so.getBuyerId()) && buyerHasFeatureNewOrderOnUpdateClosedOrder(so.getBuyerId())) {
            OrderEnhancementService orderEnhancementService = orderEnhancementServiceResolver.resolveOrderEnhancementService(so.getBuyerId());
            if (orderEnhancementService != null) {
                orderEnhancementService.enhanceServiceOrder(so);
                setProcessVariablesForEnhancedOrder(so, pvars);
            }
            so.setSoId(null);
            return serviceOrderBO.createServiceOrder(idfn, so, pvars);
        }
        return so.getSoId();
    }

    private boolean buyerHasFeatureUpdateClosedOrder(Long buyerId) {
        return quickLookupCollection.getBuyerFeatureLookup().isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.UPDATE_CLOSE_ORDER, buyerId);
    }
    
    private String getTaxServiceUrl(String applicatioKey) {
        return quickLookupCollection.getApplicationPropertyLookup().getPropertyValue(applicatioKey);
    }

    public List<ServiceOrder> getPendingServiceOrders() {
    	return serviceOrderBO.getPendingServiceOrders();
    }

    private boolean buyerHasFeatureNewOrderOnUpdateClosedOrder(Long buyerId) {
        return quickLookupCollection.getBuyerFeatureLookup().isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.NEW_ORDER_ON_CLOSE_ORDER_UPDATE, buyerId);
    }

	private void setProcessVariablesForEnhancedOrder(ServiceOrder so, Map<String, Object> pvars) {
        pvars.put(ProcessVariableUtil.PVKEY_USER_NAME, so.getCreatorUserName());
        if (so.getPrimaryTask() == null) {
        	validationUtil.addErrors(so.getValidationHolder(), ProblemType.MissingTaskName);
        } else {
        pvars.put(ProcessVariableUtil.MAIN_JOB_CODE, so.getPrimaryTask().getExternalSku());
        }
        if (so.getCondAutoRouteRuleId() != null) {
            pvars.put(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_ID, so.getCondAutoRouteRuleId());
            pvars.put(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_NAME, so.getCondAutoRouteRuleName());
        }
        setValidationWarningsIfEnhancementErrors(so, pvars);
        if(so.getBuyerState()!=null){
        	pvars.put(ProcessVariableUtil.PVKEY_BUYER_STATE_CODE, so.getBuyerState());
        }
        //Mandatory CustomRef check
        logger.debug("Checking Mandatory CustomRef for api creation flow");
        if (so.isCreatedViaAPI()){
	        if(so.isMandatoryCustomRefPresent()){
	        	logger.debug("Setting serviceOrderHasMandatoryCustomReference as true");
	        	pvars.put(ProcessVariableUtil.SERVICE_ORDER_HAS_MANDATORY_CUSTOM_REFERENCE,"true");
	        	
	        }else{
	        	logger.debug("Setting serviceOrderHasMandatoryCustomReference as false");
	        	pvars.put(ProcessVariableUtil.SERVICE_ORDER_HAS_MANDATORY_CUSTOM_REFERENCE,"false");
        }
        }else{
        	logger.info("Setting serviceOrderHasMandatoryCustomReference as true");
        	pvars.put(ProcessVariableUtil.SERVICE_ORDER_HAS_MANDATORY_CUSTOM_REFERENCE,"true");
        }

        if(so.isNewSoInjection()){
        	pvars.put(ProcessVariableUtil.CREATE_WITHOUT_TASKS, so.isCreateWithOutTasks());
        	pvars.put(ProcessVariableUtil.JOBCODE_MISMATCH, so.isJobCodeMismatch());
        }
        logger.info("ROUTING PRIORITY IN PROCESSINGBO"+so.getRoutingPriorityInd());
        logger.info("PERF_CRITERIA_LEVEL IN PROCESSINGBO"+so.getPerfCriteriaLevel());
        pvars.put(ProcessVariableUtil.ROUTING_PRIORITY_IND, so.getRoutingPriorityInd());
        pvars.put(ProcessVariableUtil.PERF_CRITERIA_LEVEL, so.getPerfCriteriaLevel());
        
    }

    private void setValidationWarningsIfEnhancementErrors(ServiceOrder so, Map<String, Object> pvars) {
    	logger.info("inside setValidationWarningsIfEnhancementErrors");
        if (so.getValidationHolder().getWarnings().size() > 0) {
        	logger.info("SERVICE_ORDER_HAS_VALIDATION_WARNINGS:::"+so.getValidationHolder().getWarnings()+"sizee::"+so.getValidationHolder().getWarnings().size());
            pvars.put(ProcessVariableUtil.SERVICE_ORDER_HAS_VALIDATION_WARNINGS, so.getValidationHolder().getWarnings().size() > 0);
        }
    }

    @Transactional
    public void executeProcessSignalWithGroups(String soId, SignalType signalType,
			Identification callerId, SOElement soElement, Map<String,Serializable> miscParams, boolean createNoteIfClosed){
    	
        if(createNoteIfClosed){
            if(serviceOrderBO.isProcessFinished(soId)){
                //order is already finished nothing to update
                //create a note that we are not updating anything
                Map<String, Object> params = new HashMap<String, Object>();
                for(Map.Entry<String, Serializable> entry : miscParams.entrySet()){
                    params.put(entry.getKey(),entry.getValue());
                }
                serviceOrderBO.addSONote(soId, "SOUpdateButNoChange", params);
                return ;
            }
        }
        if(signalType == SignalType.AUTO_POST_ORDER){
        	miscParams.put(ProcessVariableUtil.SAVE_AND_AUTOPOST, "true");
        	miscParams.put(ProcessVariableUtil.ISUPDATE,null);
        }
        else {        	
        	miscParams.put(ProcessVariableUtil.SAVE_AND_AUTOPOST, null);
        }
        
        if(signalType == SignalType.EDIT_ORDER)
        {
        	ServiceOrder so=(ServiceOrder) soElement;
	if(so.isCreatedViaAPI())
      	{
        OrderEnhancementService orderEnhancementService = orderEnhancementServiceResolver.resolveOrderEnhancementService(so.getBuyerId());
        if (orderEnhancementService != null) {
        	
            if(orderEnhancementService.enhanceServiceOrder(so))
            {
            	
            	 Map<String,Object> pvars = new HashMap<String,Object>();
            	
               setProcessVariablesForEnhancedOrder(so, pvars);
               
           
               for(Map.Entry<String, Object> entry : pvars.entrySet()){
               	miscParams.put(entry.getKey(), (Serializable) entry.getValue());
               }
               
         logger.info("Finished with order enhancement for Edit API Call.");
          soElement=(SOElement) so;
        }
        }
        else{
    		//applying market adjustment rate
    		boolean isMARfeatureOn = commonPricingScheme.isMARfeatureOn(so.getBuyerId());
    		if(isMARfeatureOn){
    			commonPricingScheme.calculatePriceWithMAR(so);
    		}
        }
        
       }
        }
        serviceOrderBO.processOrderSignal(soId, signalType, callerId, soElement, miscParams);
        serviceOrderGroupBO.processOrderSignal(soId, signalType, callerId, miscParams);
    }
    public void updatePOSCancellationIndicator(String soId , ServiceOrder serviceOrder) {
    	serviceOrderBO.updatePOSCancellationIndicator(soId, serviceOrder);
    }
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void executeProcessSignalWithGroups(String soId, SignalType signalType,
			Identification callerId, SOElement soElement, Map<String,Serializable> miscParams){
        logger.info(String.format("Inside ProcessingBO.executeProcessSignalWithGroups method for service order %1$s and signal %2$s.", soId, signalType.name()));
        long startTime = System.currentTimeMillis();
        executeProcessSignalWithGroups(soId, signalType, callerId, soElement, miscParams, false);
        logger.info(String.format("Finished with ProcessingBO.executeProcessSignalWithGroups method for service order %1$s and signal %2$s. Time taken is %3$d ms", soId, signalType.name(), System.currentTimeMillis() - startTime));
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void executeProcessLeadSignal(String leadId, SignalType signalType,
			Identification callerId, LeadElement leadElement, Map<String,Serializable> miscParams){
        logger.info(String.format("Inside ProcessingBO.executeProcessLeadSignal method for lead %1$s and signal %2$s.", leadId, signalType.name()));
        long startTime = System.currentTimeMillis();
        leadBO.processLeadSignal(leadId, signalType, callerId, leadElement, miscParams);
        logger.info(String.format("Finished with ProcessingBO.executeProcessLeadSignal method for lead %1$s and signal %2$s. Time taken is %3$d ms", leadId, signalType.name(), System.currentTimeMillis() - startTime));
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void executeProcessGroupSignal(String groupId, SignalType signalType, Identification identification, SOElement soElement, Map<String,Serializable> miscParams){
        logger.info(String.format("Inside ProcessingBO.executeProcessGroupSignal method for group order %1$s and signal %2$s.", groupId, signalType.name()));
        long startTime = System.currentTimeMillis();
		serviceOrderGroupBO.processGroupSignal(groupId, signalType, identification, soElement, miscParams);
        logger.info(String.format("Finished with ProcessingBO.executeProcessGroupSignal method for group order %1$s and signal %2$s. Time taken is %3$d ms", groupId, signalType.name(), System.currentTimeMillis() - startTime));
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public OrderStatusOnUpdate processUpdateBatch(ServiceOrder newSo, Identification callerId){
        logger.info("Inside ProcessingBO.processUpdateBatch method for service order " + newSo.getSoId());
        long startTime = System.currentTimeMillis();
        newSo.setUpdate(true);
        OrderEnhancementService orderEnhancementService = orderEnhancementServiceResolver.resolveOrderEnhancementService(newSo.getBuyerId());
        Map<String,Object> pvars = new HashMap<String,Object>();
        ServiceOrder oldSo = serviceOrderBO.getServiceOrder(newSo.getSoId());
        if (orderEnhancementService != null) {
        	
        	if(newSo.getBuyerId().intValue()==1000
                  && null != newSo.getExternalStatus()
                    && newSo.getExternalStatus().equals("AT")){
        		 //make the schedule same as original since we are going to call edit and it should not edit the schedule
                newSo.setSchedule(oldSo.getSchedule());
        	
        	}
        	
        	
            if(orderEnhancementService.enhanceServiceOrder(newSo))
                setProcessVariablesForEnhancedOrder(newSo, pvars);
        }
        Map<String, Serializable> miscParams = new HashMap<String, Serializable>();
        for(Map.Entry<String, Object> entry : pvars.entrySet()){
        	miscParams.put(entry.getKey(), (Serializable) entry.getValue());
        }
        
        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
		if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(
				BuyerFeatureSetEnum.SCOPE_CHANGE, newSo.getBuyerId())) {
			newSo.setScopeChange(true);
		}


        List<SOFieldsChangedType> changes = oldSo.compareTo(newSo);
        //new 
        if(changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)||changes.contains(SOFieldsChangedType.TASKS_ADDED)||changes.contains(SOFieldsChangedType.TASKS_DELETED)||changes.contains(SOFieldsChangedType.SERVICE_LOCATION_ZIP_CHANGED)||changes.contains(SOFieldsChangedType.SCHEDULE)||changes.contains(SOFieldsChangedType.TIER_CHANGE)){
        	miscParams.put(ProcessVariableUtil.REPOST,"true");
        }
        else{
        	miscParams.put(ProcessVariableUtil.REPOST,"false");
        }
        miscParams.put(ProcessVariableUtil.ISUPDATE,"true");
        miscParams.put(ProcessVariableUtil.BUYER_ID,"1000");
        miscParams.put(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION,null);	

        //Check if the order type is Sears RI and the external status is AT remove the schedule changed
        verifyChangesAsPerSOType(changes, newSo, oldSo);

        if (changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)){
        	//add so logging that primary category changed
            //this happens regardless of the state service order is in
        	serviceOrderBO.addSOLogging(newSo.getSoId(), "batchUpdateLogging", callerId);
        }
        //moving it down here after comparison because so logging for primary category change happens regardless
        if(!serviceOrderBO.isOrderUpdatable(newSo.getSoId())){
        	//order is already finished nothing to update
        	//create a note that we are not updating anything
    		serviceOrderBO.addSONote(newSo.getSoId(), "SOUpdateButNoChange", null);
        	return OrderStatusOnUpdate.NOT_UPDATABLE;
        }

    	//check if the order is in completed state
        //and tasks changes for Sears RI Call scope changed
		if(changes.contains(SOFieldsChangedType.PRIMARY_SKILL_CATEGORY)
				|| changes.contains(SOFieldsChangedType.TASKS_ADDED) 
				|| changes.contains(SOFieldsChangedType.TASKS_DELETED)){
			logger.info("before Scope Change Signal");
			if(serviceOrderBO.isTransitionAvailable(newSo.getSoId(), SignalType.UPDATE_BATCH_ORDER_SCOPE_CHANGED)){
				
				// execute this signal only for Active status with substatus
				// Pending Claim and Completed Status.
				if (null!=oldSo.getWfStateId() && (oldSo.getWfStateId().intValue() == OrderfulfillmentConstants.COMPLETED_STATUS
						|| (oldSo.getWfStateId().intValue() == OrderfulfillmentConstants.ACTIVE_STATUS
								&& null != oldSo.getWfSubStatusId() && oldSo
								.getWfSubStatusId().intValue() == OrderfulfillmentConstants.PENDING_CLAIM_SUBSTATUS))) {
					logger.info("Executing scope change signal for completed status or active state with pending claim status"); 
				miscParams.put(ProcessVariableUtil.SERVICE_ORDER_CHANGES, (Serializable) changes);
				//not calling group BO since the order should not be grouped anymore
				serviceOrderBO.processOrderSignal(newSo.getSoId(), SignalType.UPDATE_BATCH_ORDER_SCOPE_CHANGED, callerId, newSo, miscParams);
				return OrderStatusOnUpdate.UPDATABLE;
				} 
			}
		}
		
        //if the schedule is changed than check if the transition to reschedule request is available
        if (changes.contains(SOFieldsChangedType.SCHEDULE)){
        	if (serviceOrderBO.isTransitionAvailable(newSo.getSoId(), SignalType.BUYER_REQUEST_RESCHEDULE)){
        		//call buyer requested reschedule
        		//not calling group BO since the order should not be grouped anymore
        		serviceOrderBO.processOrderSignal(newSo.getSoId(), SignalType.BUYER_REQUEST_RESCHEDULE, callerId, newSo.getSchedule(), new HashMap<String, Serializable>());
        		//make the schedule same as original since we are going to call edit and it should not edit the schedule
        		newSo.setSchedule(oldSo.getSchedule()); 
        	}
        } 
        
	        //call service order and service group BO
	        miscParams.put(ProcessVariableUtil.SERVICE_ORDER_CHANGES, (Serializable) changes);
	        
	        // do not execute the UPDATE BATCH ORDER signal for Pending Claim substatus 
	        if (null!=oldSo.getWfStateId() &&  !(oldSo.getWfStateId().intValue() == OrderfulfillmentConstants.ACTIVE_STATUS
							&& null != oldSo.getWfSubStatusId() && oldSo
							.getWfSubStatusId().intValue() == OrderfulfillmentConstants.PENDING_CLAIM_SUBSTATUS)) {
			serviceOrderBO.processOrderSignal(newSo.getSoId(), SignalType.UPDATE_BATCH_ORDER, callerId, newSo, miscParams);
	        }
			serviceOrderGroupBO.processOrderSignal(newSo.getSoId(), SignalType.UPDATE_BATCH_ORDER, callerId, miscParams);

        logger.info(String.format("Finished with ProcessingBO.processUpdateBatch method for service order %1$s. Time taken is %2$d ms", newSo.getSoId(), System.currentTimeMillis() - startTime));
        return OrderStatusOnUpdate.UPDATABLE;
	}

    private void verifyChangesAsPerSOType(List<SOFieldsChangedType> changes, ServiceOrder newSo, ServiceOrder oldSo) {
        ServiceOrderProcess sop = serviceOrderBO.getServiceOrderProcess(newSo.getSoId());
        if(sop.getSoType().equals("SEARSRI")
                && changes.contains(SOFieldsChangedType.SCHEDULE)
                && null != newSo.getExternalStatus()
                && newSo.getExternalStatus().equals("AT")){
            changes.remove(SOFieldsChangedType.SCHEDULE);
            //make the schedule same as original since we are going to call edit and it should not edit the schedule
            newSo.setSchedule(oldSo.getSchedule());

        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void manualUngroupOrders(String soGroupId, Identification callerId){
        //Mark so_process_map that this group is not searchable
        serviceOrderGroupBO.markGroupNotSearchable(soGroupId);

        //get the SOGroup Record
        SOGroup group = serviceOrderGroupBO.getGroup(soGroupId);

        int i = 1;
        int groupSize = group.getServiceOrders().size();
        logger.info("Size of the group is " + groupSize);
        for(ServiceOrder so : group.getServiceOrders()){
            //if the last one than do not call
            if (i == 1){
                logger.info("Calling the remove all order signal for so " + so.getSoId());
                serviceOrderGroupBO.processOrderSignal(so.getSoId(), SignalType.REMOVE_ALL_ORDERS_FROM_GROUP, callerId, null);
            } else {
                //create its own group workflow
                logger.info("Creating new group workflow for so " + so.getSoId());
                serviceOrderGroupBO.createServiceOrderGroupWithoutSearch(callerId, so);
            }
            i++;
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String manualGroupOrders(String groupId, SOElement soElement, Identification callerId){
        SOElementCollection collection = (SOElementCollection) soElement;
        List<String> soIds = new ArrayList<String>();
        int i = 1;
        String firstSoId = "";
        for(SOElement so : collection.getElements()){
            String soId = ((ServiceOrder)so).getSoId();
            if(i == 1 && StringUtils.isBlank(groupId)){
                firstSoId = soId;
            } else {
                //call close the group process for all orders except the first one
                serviceOrderGroupBO.processOrderSignal(soId, SignalType.END_FOR_GROUP_ORDERS, callerId, null);
                soIds.add(soId);
            }
            i++;
        }

        //call add all orders to the first order group process
        return serviceOrderGroupBO.addOrdersToGroup(groupId, firstSoId, soIds, callerId); 
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void processAddBuyerNote(String soId, List<SONote> notes, Identification callerId) {
        if (!callerId.isBuyer()) return;
        for (SONote note: notes) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("content", note.getNote());
            serviceOrderBO.addSONote(soId, "GeneralBuyerNote", dataMap);
        }
    }

// Commenting code SL-20167 : updating purchase amount for canceled tasks
//	public void updatePurchaseAmount(String sku, BigDecimal purchaseAmount,String soId) {
//		serviceOrderBO.updatePurchaseAmount(sku, purchaseAmount,soId);
//		
//	}
	
	public void setServiceOrderBO(ServiceOrderBO serviceOrderBO){
		this.serviceOrderBO = serviceOrderBO;
	}
	
	public void setServiceOrderGroupBO(ServiceOrderGroupBO serviceOrderGroupBO){
		this.serviceOrderGroupBO = serviceOrderGroupBO;
	}

    public void setOrderEnhancementServiceResolver(OrderEnhancementServiceResolver orderEnhancementServiceResolver) {
        this.orderEnhancementServiceResolver = orderEnhancementServiceResolver;
    }

	public ServiceOrder getServiceOrder(String soId) {
		return serviceOrderBO.getServiceOrder(soId);
	}

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }

    public OrderFulfillmentResponse isSignalAvailable(String soId, SignalType signalType) {
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
		response.setSoId(soId);
		response.setSignalAvailable(serviceOrderBO.isTransitionAvailable(soId, signalType));
		return response;
	}

	public void setValidationUtil(IValidationUtil validationUtil) {
		this.validationUtil = validationUtil;
	}

	public IValidationUtil getValidationUtil() {
		return validationUtil;
	}
	
	public void updateSONotes(String soId,ServiceOrder serviceOrder) {
    	serviceOrderBO.updateSONotes(soId,serviceOrder);
    }
	
	public void updateSOLogging(String soId,ServiceOrder serviceOrder){
		serviceOrderBO.updateSOLogging(soId,serviceOrder);
	}

	public Double getCancelAmount(int buyerId) {
		Double cancelFee = serviceOrderBO.getCancelAmount(buyerId);
		return cancelFee;
	}

	public IBuyerPricingScheme getCommonPricingScheme() {
		return commonPricingScheme;
	}

	public void setCommonPricingScheme(IBuyerPricingScheme commonPricingScheme) {
		this.commonPricingScheme = commonPricingScheme;
	}

	public LeadBO getLeadBO() {
		return leadBO;
	}

	public void setLeadBO(LeadBO leadBO) {
		this.leadBO = leadBO;
	}



}
