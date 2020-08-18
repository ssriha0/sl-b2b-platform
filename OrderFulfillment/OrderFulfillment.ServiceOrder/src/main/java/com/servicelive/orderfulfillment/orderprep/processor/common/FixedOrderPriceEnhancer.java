package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;


public class FixedOrderPriceEnhancer extends AbstractOrderPriceEnhancer {

	@Override
	protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder,
			OrderEnhancementContext orderEnhancementContext) {
		BigDecimal totalRetailPrice = PricingUtil.ZERO;
		IOrderBuyer orderBuyer = orderEnhancementContext.getOrderBuyer();
		Long buyerId = orderBuyer.getBuyerId();
		
		// Check if tasks are present 
    	boolean taskPresent = false;
    	taskPresent = serviceOrder.isTasksPresent();
    	
		//check whether MAR is on    	
		boolean isMARfeatureOn = orderBuyer.getBuyerPricingScheme().isMARfeatureOn(buyerId);

    	//if feature set is on & created via API
    	if(isMARfeatureOn && serviceOrder.isCreatedViaAPI()){
    		//call the method to calculate price with MAR
    		orderBuyer.getBuyerPricingScheme().calculatePriceWithMAR(serviceOrder);	
    	}
    	else{
    		if(!taskPresent){
    			//SL-21284: Overriding SKU price with the price coming in API Request for standard services for Relay Services buyer 
    			if (((OFConstants.RELAY_SERVICES_BUYER.intValue() == serviceOrder.getBuyerId().intValue() || OFConstants.TECHTALK_BUYER.intValue() == serviceOrder.getBuyerId().intValue()) && 
    					null!=serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE) && OFConstants.STANDARD.equalsIgnoreCase(serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE))
    					&& StringUtils.isNotBlank(serviceOrder.getCustomRefValue(OFConstants.SELECTED_FIRM_ID)))){
    				logger.info("FixedOrderPriceEnhancer SELECTED_FIRM_ID:"+serviceOrder.getCustomRefValue(OFConstants.SELECTED_FIRM_ID));
    				for (SOTask task : serviceOrder.getTasks()) {
    	               
    					 BigDecimal taskPrice = orderBuyer.getBuyerPricingScheme().getServiceOfferingPrice(buyerId,task.getExternalSku(),serviceOrder.getServiceLocation().getZip(),serviceOrder.getCustomRefValue(OFConstants.SELECTED_FIRM_ID));
    					 if (taskPrice!=null) {
    	    				if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
    	    	            	task.setPrice(taskPrice);
    	    	            	task.setFinalPrice(taskPrice);
    	    	            }
    	    				totalRetailPrice = totalRetailPrice.add(taskPrice);
    	    			}
    	    			else{
    	    				task.setPrice(PricingUtil.ZERO);
    	                	task.setFinalPrice(PricingUtil.ZERO);
    	    			}
    	            }
    				logger.info("FixedOrderPriceEnhancer taskPrice:"+totalRetailPrice);
    	    		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);	
    			}
    			else if (((OFConstants.RELAY_SERVICES_BUYER.intValue() == serviceOrder.getBuyerId().intValue() || OFConstants.TECHTALK_BUYER.intValue() == serviceOrder.getBuyerId().intValue() )&& 
    					null!=serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE) && OFConstants.STANDARD.equalsIgnoreCase(serviceOrder.getCustomRefValue(OFConstants.ORDER_TYPE))  )){
    				//SL-21284: Overriding SKU price with the price coming in API Request for standard services for Relay Services buyer 
    				// Set the price coming from the request.
        			logger.info(" Relay Service labourSpendLimit"+serviceOrder.getSpendLimitLabor());
        			logger.info(" Relay Service partsSpendLimit"+serviceOrder.getSpendLimitParts());
    				BigDecimal labourSpendLimit = PricingUtil.ZERO;
        			BigDecimal partsSpendLimit = PricingUtil.ZERO;
        			labourSpendLimit = serviceOrder.getSpendLimitLabor();
        			partsSpendLimit = serviceOrder.getSpendLimitParts();
        			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpendLimit);
    			}
    			else{
    				for (SOTask task : serviceOrder.getTasks()) {
    	                BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
    	                                                orderEnhancementContext.getOrderBuyer());
    	    			if (taskPrice!=null) {
    	    				if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
    	    	            	task.setPrice(taskPrice);
    	    	            	task.setFinalPrice(taskPrice);
    	    	            }
    	    				totalRetailPrice = totalRetailPrice.add(taskPrice);
    	    			}
    	    			else{
    	    				task.setPrice(PricingUtil.ZERO);
    	                	task.setFinalPrice(PricingUtil.ZERO);
    	    			}
    	            }
    	    		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
    			}
	    		
    		}else{
    			// Set the price coming from the request.
    			BigDecimal labourSpendLimit = PricingUtil.ZERO;
    			BigDecimal partsSpenLimit = PricingUtil.ZERO;
    			labourSpendLimit = serviceOrder.getSpendLimitLabor();
    			partsSpenLimit = serviceOrder.getSpendLimitParts();
    			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpenLimit);
    		}
    	}       
	}

	private BigDecimal getRetailPrice(String externalSku, IOrderBuyer orderBuyer) {
        BuyerSkuPricingItem buyerSkuPricingItem = new BuyerSkuPricingItem(externalSku);
        orderBuyer.getBuyerPricingScheme().priceSkuItem(buyerSkuPricingItem);
        return buyerSkuPricingItem.getRetailPrice();
	}

}
