package com.servicelive.orderfulfillment.decision;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.vo.InHomeNPSConstants;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.common.NameValuePair;
import com.servicelive.marketplatform.common.vo.CondRoutingRuleVO;
import com.servicelive.marketplatform.common.vo.ItemsForCondAutoRouteRepriceVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.SkuTaxCalculation;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.AutoRoutingBehavior;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;

public class AssociateRuleWithSo  extends AbstractServiceOrderDecision{
	IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO;
	QuickLookupCollection quickLookupCollection;
	
	private static final long serialVersionUID = 9146711749919429739L;
	/**
	 * @return the marketPlatformRoutingRulesBO
	 */
	public IMarketPlatformRoutingRulesBO getMarketPlatformRoutingRulesBO() {
		return marketPlatformRoutingRulesBO;
	}
	/**
	 * @param marketPlatformRoutingRulesBO the marketPlatformRoutingRulesBO to set
	 */
	public void setMarketPlatformRoutingRulesBO(
			IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO) {
		this.marketPlatformRoutingRulesBO = marketPlatformRoutingRulesBO;
	}
	
	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}
	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

	private static final Logger logger = Logger.getLogger(AssociateRuleWithSo.class);
	public String decide(OpenExecution execution) 
	{
		ServiceOrder serviceOrder = getServiceOrder(execution);
		logger.info("Checking if rule already assocated with the SO to avoid rechecking of rule for SO");
		Object saveAutoPostInd = execution.getVariable(ProcessVariableUtil.SAVE_AND_AUTOPOST);
		logger.info("Taking saveAutoPostInd to re evalutae rule if is save and auto post");
		if((saveAutoPostInd!= null && saveAutoPostInd.toString().equals("true")) || (null!=serviceOrder && null==serviceOrder.getCondAutoRouteRuleId()))
		{
		logger.info("Save and Auto Post Ind Value"+saveAutoPostInd);
		CondRoutingRuleVO condRoutingRuleVO = new CondRoutingRuleVO();
        condRoutingRuleVO.setBuyerId(Integer.valueOf(serviceOrder.getBuyerId().intValue()));
        condRoutingRuleVO.setMainJobCode(serviceOrder.getPrimaryTask().getExternalSku());
        condRoutingRuleVO.setServiceLocationZip(serviceOrder.getServiceLocation().getZip());
        logger.info("Conditional-route : Zip Code+:Job Code"+serviceOrder.getServiceLocation().getZip()+":"+serviceOrder.getPrimaryTask().getExternalSku());
        	condRoutingRuleVO.setSoId(serviceOrder.getSoId());
            condRoutingRuleVO.setUpdate(serviceOrder.isUpdate());	
            logger.info("Checking update option of so:"+serviceOrder.isUpdate());
            logger.info("Conditional-route :SO ID"+serviceOrder.getSoId());  

        List<NameValuePair> custRefNameValues = new ArrayList<NameValuePair>();
        for (SOCustomReference custRef : serviceOrder.getCustomReferences()) {
            custRefNameValues.add(new NameValuePair(custRef.getBuyerRefTypeName(), custRef.getBuyerRefValue()));
            logger.info("Custom Reference name and Values:"+custRef.getBuyerRefTypeName()+ custRef.getBuyerRefValue());
        }
        condRoutingRuleVO.setCustomRefNameValuePairs(custRefNameValues);
        CondRoutingRuleVO returnedCondtlRoutingRule = marketPlatformRoutingRulesBO.getConditionalRoutingRuleId(condRoutingRuleVO);
        logCondAutoRouteParameters(returnedCondtlRoutingRule, condRoutingRuleVO);
        logger.info("Conditional-route in AssociateRUleWithSO class: rule ID.");  
        if (null!=returnedCondtlRoutingRule ) 
        {
        	//to handle order in draft-missing info getting auto routed on save and auto post
        	execution.setVariable(ProcessVariableUtil.PVKEY_AUTO_ROUTING_BEHAVIOR,AutoRoutingBehavior.Conditional);
        	 logger.info("Inside YES statement with rule id "+returnedCondtlRoutingRule.getRuleId());
            serviceOrder.setCondAutoRouteRuleId(returnedCondtlRoutingRule.getRuleId());
            serviceOrder.setCondAutoRouteRuleName(returnedCondtlRoutingRule.getRuleName());
            //Updating so_routing_rule_assoc table on finding a suitable rule id for so
            serviceOrderDao.updateRuleIdForSo(serviceOrder.getSoId(), returnedCondtlRoutingRule.getRuleId());
            ItemsForCondAutoRouteRepriceVO itemsForCondAutoRouteReprice = createItemsForCondAutoRouteReprice(serviceOrder);
                ItemsForCondAutoRouteRepriceVO repricedItems = marketPlatformRoutingRulesBO.repriceItems(itemsForCondAutoRouteReprice);
                if((repricedItems != null) && (repricedItems.getSkuPricePairList() != null)&& (repricedItems.getSkuPricePairList().size()>0)){
                	repriceOrderUsingSkuPriceList(serviceOrder, repricedItems.getSkuPricePairList());	
                	//removing the notes for MAR if repricing is applied
                	removeMarketAdjustmentNotes(serviceOrder);
                	//SL 20672 : price history
                	logger.info("Setting price change history for soId:"+serviceOrder.getSoId());
                	if(1000 != serviceOrder.getBuyerId().intValue()){
                		logger.info("Setting price change history for soId:"+serviceOrder.getSoId());
                		//SL 18534 Price displayed in the Price History is incorrect for CAR routed orders of HSR buyer
                		setSOPriceChangeHistory(serviceOrder);
                	}
            }
                logger.info("Return YES statement going to the associate rule to so");
                return "YES";
		
        }
        else
        {
        	//priority 4 issue changes
        	//If no matching rule is found for Inhome order, 
        	//return "NoMatchingRule"
        	if(null != serviceOrder.getBuyerId() && InHomeNPSConstants.HSRBUYER.intValue() == serviceOrder.getBuyerId().intValue()){
        		logger.info("Return NoMatchingRule if no rule is found for Inhome SO");
        		return InHomeNPSConstants.NO_MATCHING_RULE;
        	}
        	logger.info("Return NO if there is rule found for the SO");
        	return "NO";
        }
		}
		else
		{
			logger.info("If there is rule already  with SO then just returning YES.");
            return "YES";
		}
}
	
        private void repriceOrderUsingSkuPriceList(ServiceOrder serviceOrder, List<NameValuePair> skuPriceList) {
            
            BigDecimal totalPrice = new BigDecimal("0.00");
            for (SOTask soTask : serviceOrder.getActiveTasks()) {
            	BigDecimal taskPrice = soTask.getPrice();
            	if (taskPrice == null)
            		continue;
            	        
            	updatePriceForFirstTaskWithSku(soTask, skuPriceList);
            	
            	taskPrice = soTask.getPrice();
            	if (taskPrice != null) {
            		totalPrice = totalPrice.add(taskPrice);
            		 //Over-ride the task price history set in OrderPriceEnhancer
            		logger.info("Conditional-route : Task History:"+soTask.getTaskId());
            		List<SOTaskHistory> historyList = new ArrayList<SOTaskHistory>();
                    SOTaskHistory sOTaskHistory =new SOTaskHistory();
        			
        			sOTaskHistory.setPrice(soTask.getFinalPrice());
        			sOTaskHistory.setSku(soTask.getExternalSku());
                	Date startingDate = new Date();
                	sOTaskHistory.setModifiedDate(startingDate);
                	sOTaskHistory.setModifiedByName("SYSTEM");
                	sOTaskHistory.setModifiedBy("SYSTEM");
                	sOTaskHistory.setCreatedDate(startingDate);
                	sOTaskHistory.setTask(soTask);
                	sOTaskHistory.setTaskSeqNum(soTask.getTaskSeqNum());
                	historyList.add(sOTaskHistory);
                	soTask.setTaskHistory(historyList);
            	}
            	
            }
           // PricingUtil.setTotalRetailPrice(serviceOrder, totalPrice);
            BigDecimal totalRetailPrice = totalPrice;
            // adding tax calculation for the CAR rule
            if (quickLookupCollection.getBuyerFeatureLookup().isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.ALLOW_SKU_TAX,
            		serviceOrder.getBuyerId())) {
            	BigDecimal taxRateForLabor = getTaxRateForLabor(serviceOrder);  
            	totalRetailPrice = totalRetailPrice.multiply(taxRateForLabor.add(BigDecimal.ONE));
            	if (null != serviceOrder.getPrice()) {
            		serviceOrder.getPrice().setOrigSpendLimitLabor(totalRetailPrice);
            		serviceOrder.getPrice().setDiscountedSpendLimitLabor(totalRetailPrice);
            	}

            } else {
                totalRetailPrice = totalRetailPrice.setScale(2, RoundingMode.HALF_DOWN);
            }

            logger.info("Service order spend Limit labor for "+ serviceOrder.getSoId()+"from Rule before setting"+ serviceOrder.getSpendLimitLabor());
            serviceOrder.setSpendLimitLabor(totalRetailPrice);
            logger.info("Service order spend Limit labor for"+ serviceOrder.getSoId()+" from Rule after setting"+ serviceOrder.getSpendLimitLabor());
            serviceOrder.setInitialPrice(totalRetailPrice);
            if(null == serviceOrder.getSpendLimitParts()){
            	serviceOrder.setSpendLimitParts(new BigDecimal(0.0));
            }       
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
        
        private String getTaxServiceUrl(String applicatioKey) {
            return quickLookupCollection.getApplicationPropertyLookup().getPropertyValue(applicatioKey);
        }

        
        private void updatePriceForFirstTaskWithSku(SOTask task, List<NameValuePair> skuPriceList){
          	 for (NameValuePair skuPricePair : skuPriceList) {
                   String sku = skuPricePair.getName();
                   BigDecimal newPrice = new BigDecimal(skuPricePair.getValue());
                  if (task.getExternalSku() != null) {
                   if (sku.equalsIgnoreCase(task.getExternalSku())) {
                       task.setPrice(newPrice);
                       //task.setRetailPrice(newPrice);
           			task.setFinalPrice(newPrice);
                       break;
                   }
                  }
               }         
          }
        
        private void logCondAutoRouteParameters(CondRoutingRuleVO returnedCondtlRoutingRule, CondRoutingRuleVO sentCondtlRoutingRuleVO) {
            if (!logger.isDebugEnabled()) return;
            logger.info("Inside logCondAutoRouteParameter:");
            String ruleName = null;
            Integer ruleId = null;
            if (returnedCondtlRoutingRule != null) {
                ruleName = returnedCondtlRoutingRule.getRuleName();
                ruleId = returnedCondtlRoutingRule.getRuleId();
                logger.info("Rule Name and Rule Id value"+ruleName+":"+ruleId);
            }

            StringBuilder sb = new StringBuilder("\nConditional auto route rule ");
            sb.append(ruleName).append("(").append(ruleId).append(") resulted from the following parameters:");
            sb.append("\nbuyerId     : ").append(sentCondtlRoutingRuleVO.getBuyerId());
            sb.append("\nmainJobCode : ").append(sentCondtlRoutingRuleVO.getMainJobCode());
            sb.append("\nserviceZip  : ").append(sentCondtlRoutingRuleVO.getServiceLocationZip());
            if (sentCondtlRoutingRuleVO.getCustomRefNameValuePairs() != null) {
                sb.append("\nCustom Refs :");
                for (NameValuePair valuePair : sentCondtlRoutingRuleVO.getCustomRefNameValuePairs()) {
                    sb.append("\n  ").append(valuePair.getName()).append("=").append(valuePair.getValue());
                }
            }
            logger.info("Value of sb:"+sb.toString());
            logger.debug(sb.toString());
        }
        
      //remove the notes added when MAR is applied
        private void removeMarketAdjustmentNotes(ServiceOrder serviceOrder){
        	String subject = "Service Order Price Update";
        	SONote removeNote = null;
        	List <SONote> soNotes = serviceOrder.getNotes();
        	for(SONote note : serviceOrder.getNotes()){
        		if(note.isMARnote() && subject.equals(note.getSubject())){
        			removeNote = note;
        		}
        	}
        	if(null != removeNote){
        		soNotes.remove(removeNote);
        	}
        	serviceOrder.setNotes(soNotes);
        }
        
        private ItemsForCondAutoRouteRepriceVO createItemsForCondAutoRouteReprice(ServiceOrder serviceOrder) {
            ItemsForCondAutoRouteRepriceVO itemsForCondAutoRouteReprice = new ItemsForCondAutoRouteRepriceVO();
            itemsForCondAutoRouteReprice.setSpecialtyCode(serviceOrder.getPrimaryTask().getSpecialtyCode());
            itemsForCondAutoRouteReprice.setCondAutoRouteRuleId(serviceOrder.getCondAutoRouteRuleId());
            itemsForCondAutoRouteReprice.setSkus(extractDistinctSkusListFromTasks(serviceOrder.getTasks()));
            return itemsForCondAutoRouteReprice;
        }
        
        private List<String> extractDistinctSkusListFromTasks(List<SOTask> tasks) {
            Set<String> skuSet = new HashSet<String>(tasks.size());
            logger.info("tasks.size value"+tasks.size());
            if(tasks.size() > 0) {
                for (SOTask task : tasks) {
                    if (task.getExternalSku() != null) {
                        skuSet.add(task.getExternalSku());
                    }
                }
            }
            List<String> distinctSkuList = new ArrayList<String>();
            distinctSkuList.addAll(skuSet);
            return distinctSkuList;
        }
        
        //SL 18543 Start Added the price change update method
        /**
         * SL-18007 method to update  so price change history record during SO creation through car
         * @param: ServiceOrder serviceOrder
         */
        private void setSOPriceChangeHistory(ServiceOrder serviceOrder){
        	logger.info("Inside So Price Change History in Car Enhancer");
        	
        	List<SOPriceChangeHistory> soPriceChangeHistoryList = new ArrayList<SOPriceChangeHistory>();
        	BigDecimal soLabourPrice = PricingUtil.ZERO;
        	BigDecimal soPartsPrice = PricingUtil.ZERO;
        	BigDecimal addOnPrice = PricingUtil.ZERO;
        	BigDecimal partsInvoicePrice = PricingUtil.ZERO;
        	double totalPermitPrice = 0.00;
        	BigDecimal permitPrice = PricingUtil.ZERO;
        	Integer permitTask = 1;
        	if (null != serviceOrder){
        		soPriceChangeHistoryList = serviceOrder.getSoPriceChangeHistory();
        		soLabourPrice = serviceOrder.getSpendLimitLabor();
        		soPartsPrice = serviceOrder.getSpendLimitParts();
        		for (SOTask task : serviceOrder.getActiveTasks()) {
    				if(permitTask.equals(task.getTaskType())){
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
        		
        	logger.info("Exiting So Price Change History in Car Enhancer");
 }
        //SL 18543 End  Added the price change update method to update labor column of 
}
