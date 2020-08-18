package com.servicelive.orderfulfillment.command;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.mobile.constants.MPConstants;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.SOLoggingCmdHelper;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;



public class PriceReductionCmd extends SOCommand{

	protected QuickLookupCollection quickLookupCollection;
	protected SOLoggingCmdHelper soLoggingCmdHelper;
	
	
	public void execute(Map<String, Object> processVariables) {
		
		try{
			logger.info("Entered PriceReductionCmd :");
			ServiceOrder so = getServiceOrder(processVariables);
				
			if(null!=so){
				
				BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
				if (!buyerFeatureLookup.isInitialized()) {
					throw new ServiceOrderException("Unable to lookup buyer feature for labour price reduction. BuyerFeatureLookup not initialized.");
				}
				Long buyerId = so.getBuyerId();
				logger.info("PriceReductionCmd Buyer Id :"+buyerId);
					
				//1.Check whether the buyer has the feature LABOUR_PRICE_REDUCTION from buyer_feature_set table
				if (null!=buyerId && buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.LABOR_PRICE_REDUCTION, buyerId)) {
					
					String validWfStates = serviceOrderDao.getApplicationConstantValue(OrderfulfillmentConstants.POSSIBLE_WF_STATES_OF_PRIMARY_ORDER);
					if(StringUtils.isNotBlank(validWfStates)){
						
						logger.info("validWfStates :"+validWfStates);
						logger.info("PriceReductionCmd Buyer Id :"+buyerId);
						String soId = getPrimaryServiceOrder(so,processVariables,validWfStates.trim());
						logger.info("PriceReductionCmd soId Id :"+soId);
						
						if(StringUtils.isNotBlank(soId)){
									
							//4.Fetch the price reduction factor from application_constants table .
							String priceReductionValue = serviceOrderDao.getApplicationConstantValue(OrderfulfillmentConstants.PRICE_REDUCTION_FACTOR);
							logger.info("PriceReductionCmd priceReductionValue :"+priceReductionValue);
										
							if(StringUtils.isNotBlank(priceReductionValue)){	
												
								reduceLaborPrice(so,priceReductionValue,soId);
								setSOPriceChangeHistory(so);
												
								//6.Insert into so_logging table for price reduction
								HashMap<String,Object> soLogDataMap = new HashMap<String,Object>();
								soLogDataMap.put("PRICE_REDUCTION_LOG_COMMENT", OrderfulfillmentConstants.PRICE_REDUCTION_LOGGING);
								soLoggingCmdHelper.logServiceOrderActivity(so, "priceReductionLogging", soLogDataMap);					
							}
						}	
					}
				}		
				logger.info("Leaving PriceReductionCmd :");
			}
		}catch(Exception e){
			logger.error("getServiceOrder - no order available for So ID: " + e);
		}
	}


	/**
	 * Description :2.Check if current SO has only a Single firm associated with it
	 * @param so
	 * @return Integer
	 */
	private Integer isSingleFirmOrder(ServiceOrder so,Map<String, Object> processVariables){
		logger.info("PriceReductionCmd-  Inside isSingleFirmOrder() method :");
		Integer ruleId =(Integer)processVariables.get(ProcessVariableUtil.CONDITIONAL_AUTO_ROUTING_RULE_ID);
		logger.info("PriceReductionCmd-  Inside isSingleFirmOrder() method :ruleId"+ruleId);
		Integer vendorId = null;
		if(null!=so && null!= so.getRoutedResources() && !so.getRoutedResources().isEmpty()){
			vendorId = so.getRoutedResources().get(0).getVendorId();
			for(RoutedProvider routedProvider: so.getRoutedResources()){
				logger.info("PriceReductionCmd-  Inside isSingleFirmOrder() method :routedProvider.getVendorId()"+routedProvider.getVendorId());
				if(null!=vendorId && vendorId.intValue()!=routedProvider.getVendorId().intValue()){
					vendorId = null;
					break;
				}
			}
		}else if(null!=ruleId){
			logger.info("PriceReductionCmd-  Inside isSingleFirmOrder() method :ruleId"+ruleId);
			vendorId=serviceOrderDao.getRoutingRuleVendorId(ruleId);
		}
		logger.info("PriceReductionCmd-  Inside isSingleFirmOrder() method :vendorId"+vendorId);
		return vendorId;
	}
	
	/**
	 * Description :3.Find service orders satisfying below conditions :
					i.CAR routed
					ii.Same buyer id
					iii.Same Service Location as that of the current SO
					iv.Same Service Date as that of the current SO
					v.Same firm as that of the current SO
					vi.SO In Posted/Accepted/Active/Problem wf state
	 * @param so
	 * @return String
	 */
	
	
	 private String getPrimaryServiceOrder(ServiceOrder secondaryOrder,Map<String, Object> processVariables,String validWfStates){
		 String primarySoId = null;
		 logger.info("PriceReductionCmd-  Inside getListOfSOForPrimarySO() method :");
		 Integer primaryVendorId = null;
		 Integer secondarySingleFirmId = isSingleFirmOrder(secondaryOrder,processVariables);
		 if(null != secondarySingleFirmId){
			 List<ServiceOrder> soList = serviceOrderDao.getServiceOrdersForPrimarySo(secondaryOrder,validWfStates);
			 if(null!=soList && !soList.isEmpty()){
				 for(ServiceOrder serviceOrder: soList){
					if(null!=serviceOrder){
						
						if(null!=serviceOrder.getAcceptedProviderId()) // checking if the order is in wf-states 150,155,170
						{
							if(serviceOrder.getAcceptedProviderId().equals(Long.parseLong(secondarySingleFirmId.toString()))){
								primarySoId=serviceOrder.getSoId();
							 	break;
							}
						}
						else { //checking if the order is in wf-state 110
							primaryVendorId = serviceOrderDao.getVendorIdForRoutedSo(serviceOrder.getSoId(),secondarySingleFirmId);
							if(null!=primaryVendorId){
								primarySoId=serviceOrder.getSoId();
								break;
							}
						}
					}
				 }
			}
		}
		return primarySoId;
	 }
	 
	//5.Apply price reduction to the spend limit labor for second SO.
	//Set labor_price_red_factor for the current SO in 'so_workflow_controls' table.
	//Set the so_id satisfying above conditions	as the 'primary_so' in 'so_workflow_controls' table.
	//Set reduced price in so_hdr table.
	//Set reduced price across tasks in so_tasks table.
    private void reduceLaborPrice(ServiceOrder serviceOrder, String priceReductionValue,String primarySO) {
    	logger.info("PriceReductionCmd reduceLaborPrice :");
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal hundredValue = new BigDecimal("100");
        BigDecimal priceReduction = new BigDecimal(priceReductionValue);
        
        for (SOTask soTask : serviceOrder.getActiveTasks()) {
        	BigDecimal taskPrice = soTask.getPrice();
        	if (taskPrice != null) {
        		
        		taskPrice = (taskPrice.multiply(priceReduction)).divide(hundredValue);
        		taskPrice = taskPrice.setScale(2, RoundingMode.HALF_DOWN);
        		totalPrice = totalPrice.add(taskPrice);
        		soTask.setPrice(taskPrice);
        		soTask.setFinalPrice(taskPrice);
        	}
        	
        }
        
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_DOWN);
        logger.info("Service order spend Limit labor for "+ serviceOrder.getSoId()+"from Rule before setting"+ serviceOrder.getSpendLimitLabor());
        serviceOrder.setSpendLimitLabor(totalPrice);
        logger.info("Service order spend Limit labor for"+ serviceOrder.getSoId()+" from Rule after setting"+ serviceOrder.getSpendLimitLabor());
        serviceOrder.setInitialPrice(totalPrice);       
        
        if(null != serviceOrder.getSOWorkflowControls())
        {
        	SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls();
        	soWorkflowControls.setPrimarySO(primarySO);
        	soWorkflowControls.setLaborPriceRedFactor(priceReductionValue);
        }
   
    }
    
    //5.Set reduced price in so_price_change_history table.
    private void setSOPriceChangeHistory(ServiceOrder serviceOrder){

    	logger.info("Inside So Price Change History in Car Enhancer in PriceReductionCmd");
    	List<SOPriceChangeHistory> soPriceChangeHistoryList = new ArrayList<SOPriceChangeHistory>();
    	BigDecimal soLabourPrice = PricingUtil.ZERO;
    	BigDecimal soPartsPrice = PricingUtil.ZERO;
    	BigDecimal addOnPrice = PricingUtil.ZERO;
    	BigDecimal partsInvoicePrice = PricingUtil.ZERO;
    	double totalPermitPrice = 0.00;
    	BigDecimal permitPrice = PricingUtil.ZERO;
    	int permitTask = 1;
    	if (null != serviceOrder){
    		soPriceChangeHistoryList = serviceOrder.getSoPriceChangeHistory();
    		soLabourPrice = serviceOrder.getSpendLimitLabor();
    		soPartsPrice = serviceOrder.getSpendLimitParts();
    		for (SOTask task : serviceOrder.getActiveTasks()) {
				if(null != task.getTaskType() && permitTask == task.getTaskType().intValue()){
					if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
					}
				
				}	    	
	    	}
    		permitPrice =  new BigDecimal(totalPermitPrice);
    		if(null != soPriceChangeHistoryList){
    			int size = soPriceChangeHistoryList.size();
    			SOPriceChangeHistory soPriceChangeHistory = soPriceChangeHistoryList.get(size-1);
        		soPriceChangeHistory.setSoLabourPrice(soLabourPrice.subtract(permitPrice));
        		soPriceChangeHistory.setSoMaterialsPrice(soPartsPrice);
        		addOnPrice = soPriceChangeHistory.getSoAddonPrice();
        		partsInvoicePrice = soPriceChangeHistory.getSoPartsInvoicePrice();
        		
        		soPriceChangeHistory.setSoTotalPrice(soLabourPrice == null?PricingUtil.ZERO : soLabourPrice
        				.add(soPartsPrice == null? PricingUtil.ZERO : soPartsPrice )
        				.add(addOnPrice == null? PricingUtil.ZERO : addOnPrice)
        				.add(partsInvoicePrice == null ? PricingUtil.ZERO : partsInvoicePrice));
        		//replace the latest entry with new values  
        		soPriceChangeHistoryList.remove(size-1);
        		soPriceChangeHistoryList.add(soPriceChangeHistory);
        		serviceOrder.setSoPriceChangeHistory(soPriceChangeHistoryList);
        	}
    	}
    		
}
	
   

	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}

	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	public SOLoggingCmdHelper getSoLoggingCmdHelper() {
		return soLoggingCmdHelper;
	}

	public void setSoLoggingCmdHelper(SOLoggingCmdHelper soLoggingCmdHelper) {
		this.soLoggingCmdHelper = soLoggingCmdHelper;
	}
	
	
}