package com.servicelive.orderfulfillment.orderprep.processor.common;

import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;

public class CommonOrderPriceEnhancer extends AbstractOrderPriceEnhancer {
    @Override
    protected void calculateAndSetPriceInfo(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
    	//create SOPricing from the service order spend limits
        SOPrice soPrice = (serviceOrder.getPrice()==null)?  new SOPrice() : serviceOrder.getPrice();
        soPrice.setOrigSpendLimitLabor(serviceOrder.getSpendLimitLabor());
        soPrice.setDiscountedSpendLimitLabor(serviceOrder.getSpendLimitLabor());
        soPrice.setInitPostedSpendLimitLabor(serviceOrder.getSpendLimitLabor());
        soPrice.setOrigSpendLimitParts(serviceOrder.getSpendLimitParts());
        soPrice.setDiscountedSpendLimitParts(serviceOrder.getSpendLimitParts());
        soPrice.setInitPostedSpendLimitParts(serviceOrder.getSpendLimitParts());
        if(serviceOrder.getPrice()==null)
        	serviceOrder.setPrice(soPrice);
    }
}
