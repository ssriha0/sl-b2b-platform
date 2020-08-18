package com.servicelive.orderfulfillment.orderprep.processor.hsr;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.so.type.BidPriceSchema;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderPriceEnhancer;

import java.math.BigDecimal;

public class HsrOrderPriceEnhancer extends AbstractOrderPriceEnhancer {
	//Added to validate Repeat Repair Order is Recall Or Not
	protected IServiceOrderDao serviceOrderDao;
    @Override
    protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        BigDecimal totalRetailPrice = PricingUtil.ZERO;
        
        IOrderBuyer orderBuyer = orderEnhancementContext.getOrderBuyer();
		Long buyerId = orderBuyer.getBuyerId();
		
		// Check if tasks are present 
    	boolean taskPresent = false;
    	boolean isRecallOrder = false;
    	taskPresent = serviceOrder.isTasksPresent();
        
        //check whether MAR is on    	
		boolean isMARfeatureOn = orderBuyer.getBuyerPricingScheme().isMARfeatureOn(buyerId);
		
    	//if feature set is on & created via API
    	if(isMARfeatureOn && serviceOrder.isCreatedViaAPI() &&!serviceOrder.isCustomRefPresent()){
    		//call the method to calculate price with MAR
    		orderBuyer.getBuyerPricingScheme().calculatePriceWithMAR(serviceOrder);	
    	}//Non w2 priority 2 : OverRide the price from request to ZERO for repeat Repair warranty Order
    	else if(serviceOrder.isCreatedViaAPI() && serviceOrder.isCustomRefPresent()){
    		//Setting Buyer Id in so for validation
    		serviceOrder.setBuyerId(buyerId);
    		isRecallOrder = serviceOrderDao.validateRecallOrder(serviceOrder);
    		if(isRecallOrder){
	    		if(!taskPresent){
	    			  //Set price of task to ZERO for warranty Order
	                  for (SOTask task : serviceOrder.getTasks()) {
	            		    task.setPrice(PricingUtil.ZERO);
	                        task.setFinalPrice(PricingUtil.ZERO);
	            		}
	                serviceOrder.setSpendLimitParts(PricingUtil.ZERO);  
	        		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice);
	    		}//Setting Price of tasks and So as zero for order created using tasks
	    		else{
	    			BigDecimal labourSpendLimit = PricingUtil.ZERO;
	    			BigDecimal partsSpenLimit = PricingUtil.ZERO;
	    			//set task price to zero instead of null.
	    			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpenLimit);
	    		}
    		}else{
    			//Normal Setting of Price
    			if(!taskPresent){
        			BuyerSkuMap buyerSkuMap = orderEnhancementContext.getOrderBuyer().getBuyerSkuMap();
        	        BuyerOrderSku primaryBuyerOrderSku = buyerSkuMap.getBuyerSku(serviceOrder.getPrimaryTask().getExternalSku());
        			if (primaryBuyerOrderSku != null) {
                        BidPriceSchema primaryBidPriceSchema = primaryBuyerOrderSku.getBidPriceSchema();

                        
                        for (SOTask task : serviceOrder.getTasks()) {
                            BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
                                                            primaryBidPriceSchema,
                                                            orderEnhancementContext.getOrderBuyer());
                            if (taskPrice!=null) {
                	            task.setPrice(taskPrice);
                	            task.setFinalPrice(taskPrice);
                            	totalRetailPrice = totalRetailPrice.add(taskPrice);
                            }else{
                				task.setPrice(PricingUtil.ZERO);
                            	task.setFinalPrice(PricingUtil.ZERO);
                			}
                        }
                    }
            		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice); 
        		}
        		else{
        			// Set the price coming from the request.
        			BigDecimal labourSpendLimit = PricingUtil.ZERO;
        			BigDecimal partsSpenLimit = PricingUtil.ZERO;
        			labourSpendLimit = serviceOrder.getSpendLimitLabor();
        			partsSpenLimit = serviceOrder.getSpendLimitParts();
        			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpenLimit);
        		}
    			
    		}
    		
    	}else{
    		if(!taskPresent){
    			BuyerSkuMap buyerSkuMap = orderEnhancementContext.getOrderBuyer().getBuyerSkuMap();
    	        BuyerOrderSku primaryBuyerOrderSku = buyerSkuMap.getBuyerSku(serviceOrder.getPrimaryTask().getExternalSku());
    			if (primaryBuyerOrderSku != null) {
                    BidPriceSchema primaryBidPriceSchema = primaryBuyerOrderSku.getBidPriceSchema();

                    
                    for (SOTask task : serviceOrder.getTasks()) {
                        BigDecimal taskPrice = getRetailPrice(task.getExternalSku(),
                                                        primaryBidPriceSchema,
                                                        orderEnhancementContext.getOrderBuyer());
                        if (taskPrice!=null) {
            	            task.setPrice(taskPrice);
            	            task.setFinalPrice(taskPrice);
                        	totalRetailPrice = totalRetailPrice.add(taskPrice);
                        }else{
            				task.setPrice(PricingUtil.ZERO);
                        	task.setFinalPrice(PricingUtil.ZERO);
            			}
                    }
                }
        		PricingUtil.setTotalRetailPrice(serviceOrder, totalRetailPrice); 
    		}
    		else{
    			// Set the price coming from the request.
    			BigDecimal labourSpendLimit = PricingUtil.ZERO;
    			BigDecimal partsSpenLimit = PricingUtil.ZERO;
    			labourSpendLimit = serviceOrder.getSpendLimitLabor();
    			partsSpenLimit = serviceOrder.getSpendLimitParts();
    			PricingUtil.setTotalRetailPriceForTasks(serviceOrder, labourSpendLimit, partsSpenLimit);
    		}
    		
    	}               
    }

    private BigDecimal getRetailPrice(String sku, BidPriceSchema bidPriceSchema, IOrderBuyer orderBuyer) {
        BuyerSkuPricingItem buyerSkuPricingItem = new BuyerSkuPricingItem(sku, bidPriceSchema);
        orderBuyer.getBuyerPricingScheme().priceSkuItem(buyerSkuPricingItem);
        return buyerSkuPricingItem.getRetailPrice();
    }

	public IServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
  
}
