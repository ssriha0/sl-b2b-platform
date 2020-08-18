package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;


public abstract class AbstractOrderPriceEnhancer extends AbstractOrderEnhancer {
    public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        initializePriceInfoToZero(serviceOrder);
        calculateAndSetPriceInfo(serviceOrder, orderEnhancementContext);
        //SL-18007 method to save so price change history during so creation
        setSOPriceChangeHistory(serviceOrder);
    }

    private void initializePriceInfoToZero(ServiceOrder serviceOrder) {
        if(serviceOrder.getSpendLimitLabor()== null)serviceOrder.setSpendLimitLabor(PricingUtil.ZERO);
        if(serviceOrder.getInitialPrice()== null)serviceOrder.setInitialPrice(PricingUtil.ZERO);
        if(serviceOrder.getSpendLimitParts()== null)serviceOrder.setSpendLimitParts(PricingUtil.ZERO);

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

    protected abstract void calculateAndSetPriceInfo(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext);
    
    /**
     * SL-18007 method to save so price change history during SO creation
     * @param: ServiceOrder serviceOrder
     */
    private void setSOPriceChangeHistory(ServiceOrder serviceOrder){
    	
    	List<SOPriceChangeHistory> soPriceChangeHistoryList = new ArrayList<SOPriceChangeHistory>();
    	SOPriceChangeHistory soPriceChangeHistory  = new SOPriceChangeHistory();
    	BigDecimal soLabourPrice = PricingUtil.ZERO;
    	BigDecimal soPartsPrice = PricingUtil.ZERO;
    	BigDecimal permitTaskPrice = PricingUtil.ZERO;
    	BigDecimal addOnPrice = PricingUtil.ZERO;
    	BigDecimal partsInvoicePrice = PricingUtil.ZERO;
    	Date createdDate = new Date();
    	
    	if (null != serviceOrder){
    		soLabourPrice = serviceOrder.getSpendLimitLabor();
    		soPartsPrice = serviceOrder.getSpendLimitParts();
    		soPriceChangeHistory.setSoMaterialsPrice(soPartsPrice);
    		// set the price of Permit tasks from so_tasks    		
    		List<SOTask> soTasks = serviceOrder.getTasks();
    		if (null != soTasks){
    			for(SOTask task: soTasks){
    				if (1==task.getTaskType()){
    					permitTaskPrice = permitTaskPrice.add(task.getPrice());
    				}
    			}
    		}
    		
    		List<SOAddon> soAddons = serviceOrder.getAddons();
    		if (null != soAddons){
    			for (SOAddon soAddon: soAddons){
    				if(("99888").equals(soAddon.getSku()))
    				{	
    					logger.info("<<<<<addon*Quantity---"+soAddon.getAddonPrice());
    					permitTaskPrice = permitTaskPrice.add(soAddon.getAddonPrice());
    				}
    			}
    		}    		
    		logger.info("<<<<<<permitTaskPrice---"+permitTaskPrice);
    		
    		soPriceChangeHistory.setSoPermitPrice(permitTaskPrice);
    		soPriceChangeHistory.setSoLabourPrice(soLabourPrice.subtract(permitTaskPrice));
    		// set the price of addons from so_addons 
    		addOnPrice = serviceOrder.getPrice().getTotalAddonPriceGL();
    		logger.info("<<<<<addOnPrice---"+addOnPrice);
    		

//    		List<SOAddon> soAddons = serviceOrder.getAddons();
//    		if (null != soAddons){
//    			for (SOAddon soAddon: soAddons){
//    				addOnPrice = addOnPrice.add(soAddon.getRetailPrice().multiply(new BigDecimal(soAddon.getQuantity())));
//    			}
//    		}
    		if(addOnPrice == null)
    		{
        		soPriceChangeHistory.setSoAddonPrice(new BigDecimal(0.00));

    		}	
    		else
    		{	logger.info(">>>>>>addOnPrice--"+addOnPrice);
    		    soPriceChangeHistory.setSoAddonPrice(addOnPrice);
    		}
    		//setting parts invoice price to zero as this is applicable only for buyer 3000 and only during completion of an SO
    		soPriceChangeHistory.setSoPartsInvoicePrice(partsInvoicePrice);
    		// Total SO Price = labour + parts price
    		
    		soPriceChangeHistory.setSoTotalPrice(soLabourPrice == null?PricingUtil.ZERO : soLabourPrice
    				.add(soPartsPrice == null? PricingUtil.ZERO : soPartsPrice )
    				.add(addOnPrice == null? PricingUtil.ZERO : addOnPrice)
    				.add(partsInvoicePrice == null ? PricingUtil.ZERO : partsInvoicePrice));
    		soPriceChangeHistory.setAction(OFConstants.ORDER_CREATION);
    		soPriceChangeHistory.setReasonComment(null);
    		soPriceChangeHistory.setCreatedDate(createdDate);
    		soPriceChangeHistory.setModifiedDate(createdDate);
    		soPriceChangeHistory.setModifiedBy(PricingUtil.SYSTEM);
    		soPriceChangeHistory.setModifiedByName(PricingUtil.SYSTEM);
    		
    		soPriceChangeHistoryList.add(soPriceChangeHistory);
    		
    		serviceOrder.setSoPriceChangeHistory(soPriceChangeHistoryList);
    	}
    	
    }
}
