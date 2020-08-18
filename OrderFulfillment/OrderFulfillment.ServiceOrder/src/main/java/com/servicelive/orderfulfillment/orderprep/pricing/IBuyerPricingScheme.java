package com.servicelive.orderfulfillment.orderprep.pricing;

import java.math.BigDecimal;

import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuPricingItem;

public interface IBuyerPricingScheme {
    void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem);
    
	boolean isMARfeatureOn(Long buyerId);
	
	void calculatePriceWithMAR(ServiceOrder serviceOrder);

	void createNoteForSkuWithMAR(BigDecimal marketAdjRate, ServiceOrder serviceOrder, StringBuilder note);
	
	void priceSkuItem(BuyerSkuPricingItem buyerSkuPricingItem, StringBuilder note, String taskName);
	
	BigDecimal getMarketAdjustmentRate(Long buyerId, String serviceLocationZip);
	
    void setSOPriceChangeHistory(ServiceOrder serviceOrder);
    
    BigDecimal getServiceOfferingPrice(Long buyerId,String sku,String zip,String firmId);
	
}
