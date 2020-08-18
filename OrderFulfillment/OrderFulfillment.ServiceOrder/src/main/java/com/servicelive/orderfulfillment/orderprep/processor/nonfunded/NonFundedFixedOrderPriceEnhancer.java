package com.servicelive.orderfulfillment.orderprep.processor.nonfunded;

import java.math.BigDecimal;



import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderPriceEnhancer;
import com.servicelive.orderfulfillment.domain.SOPrice;

public class NonFundedFixedOrderPriceEnhancer extends AbstractOrderPriceEnhancer {

	// SL-19728 ,added code change for non funded Buyer
	@Override
	protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder,
			OrderEnhancementContext orderEnhancementContext) {
		
	
		BigDecimal totalRetailPrice = PricingUtil.ZERO;
		IOrderBuyer orderBuyer = orderEnhancementContext.getOrderBuyer();
		Long buyerId = orderBuyer.getBuyerId();
		
		// Check if tasks are present 
    	boolean taskPresent = false;
    	taskPresent = serviceOrder.isTasksPresent();
    	
    	// setting non funded Indicator as true.
    	serviceOrder.getSOWorkflowControls().setNonFundedInd(true);
    	
    	//initializing the price to zero 
    		if(!taskPresent){
	    		for (SOTask task : serviceOrder.getTasks()) {
	                BigDecimal taskPrice = PricingUtil.ZERO;
	                
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
    		}
    		
    		initializePriceInfoToZero(serviceOrder);
    		
    	      
	}

	private BigDecimal getRetailPrice(String externalSku, IOrderBuyer orderBuyer) {
		return PricingUtil.ZERO;
	}
	
	// initializing the price to zero
	 private void initializePriceInfoToZero(ServiceOrder serviceOrder) {
	        serviceOrder.setSpendLimitLabor(PricingUtil.ZERO);
	        serviceOrder.setInitialPrice(PricingUtil.ZERO);
	        serviceOrder.setSpendLimitParts(PricingUtil.ZERO);

	        SOPrice soPrice = new SOPrice();
	        soPrice.setOrigSpendLimitLabor(PricingUtil.ZERO);
	        soPrice.setDiscountedSpendLimitLabor(PricingUtil.ZERO);
	        soPrice.setInitPostedSpendLimitLabor(PricingUtil.ZERO);
	        soPrice.setOrigSpendLimitParts(PricingUtil.ZERO);
	        soPrice.setDiscountedSpendLimitParts(PricingUtil.ZERO);
	        soPrice.setInitPostedSpendLimitParts(PricingUtil.ZERO);
	        soPrice.setFinalServiceFee(PricingUtil.ZERO);
	        serviceOrder.setPrice(soPrice);
	    }

}
