package com.servicelive.orderfulfillment.orderprep.processor.nest;

import java.math.BigDecimal;

import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderPriceEnhancer;

public class NestOrderPriceEnhancer extends AbstractOrderPriceEnhancer {

	@Override
	protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder,
			OrderEnhancementContext orderEnhancementContext) {
		
		logger.info("Into NestOrderPriceEnhancer.calculateAndSetPriceInfo method");
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
    	} else if (!taskPresent) {
			logger.info("NestOrderPriceEnhancer : task not present for the order : "
					+ serviceOrder.getSoId());
			
			if (null == serviceOrder.getTasks()
					|| 0 >= serviceOrder.getTasks().size()) {
				logger.info("Did not find tasks");
				return;
			}
			
			for (SOTask task : serviceOrder.getTasks()) {
	            BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
	                                            orderEnhancementContext.getOrderBuyer());
				if (taskPrice!=null){
					if(SOPriceType.TASK_LEVEL.name().equals(serviceOrder.getPriceType())){
		            	task.setPrice(taskPrice);
		            	task.setFinalPrice(taskPrice);
		            }
					totalRetailPrice = totalRetailPrice.add(taskPrice);
				} else {
					task.setPrice(PricingUtil.ZERO);
					task.setFinalPrice(PricingUtil.ZERO);
				}
	        }
			PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
		} else {
			logger.info("NestOrderPriceEnhancer : task present for the order : "
					+ serviceOrder.getSoId());
			for (SOTask task : serviceOrder.getTasks()) {
				BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
						orderEnhancementContext.getOrderBuyer());
				if (taskPrice != null) {
					if (SOPriceType.TASK_LEVEL.name().equals(
							serviceOrder.getPriceType())) {
						task.setPrice(taskPrice);
						task.setFinalPrice(taskPrice);
					}
					totalRetailPrice = totalRetailPrice.add(taskPrice);
				} else {
					task.setPrice(PricingUtil.ZERO);
					task.setFinalPrice(PricingUtil.ZERO);
				}
			}
			PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
		}
	}

	private BigDecimal getRetailPrice(String externalSku, IOrderBuyer orderBuyer) {
		BuyerSkuPricingItem buyerSkuPricingItem = new BuyerSkuPricingItem(
				externalSku);
		orderBuyer.getBuyerPricingScheme().priceSkuItem(buyerSkuPricingItem);
		return buyerSkuPricingItem.getRetailPrice();
	}

}
