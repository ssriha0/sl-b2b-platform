package com.servicelive.orderfulfillment.orderprep.processor.att;

import java.math.BigDecimal;

import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderPriceEnhancer;

public class AttFixedOrderPriceEnhancer extends AbstractOrderPriceEnhancer {
	

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
    			for (SOTask task : serviceOrder.getTasks()) {
    	            BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
    	                                            orderEnhancementContext.getOrderBuyer());
    				if (taskPrice!=null){
    					if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
    		            	task.setPrice(taskPrice);
    		            	task.setFinalPrice(taskPrice);
    		            }
    					totalRetailPrice = totalRetailPrice.add(taskPrice);
    				}
    	        }
    			PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
    		}else if(taskPresent){
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
