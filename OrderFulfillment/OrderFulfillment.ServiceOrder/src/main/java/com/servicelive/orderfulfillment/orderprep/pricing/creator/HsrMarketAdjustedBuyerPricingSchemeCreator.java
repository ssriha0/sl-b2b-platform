package com.servicelive.orderfulfillment.orderprep.pricing.creator;

import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.pricing.HsrMarketAdjustedBuyerPricingScheme;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;

public class HsrMarketAdjustedBuyerPricingSchemeCreator implements IBuyerPricingSchemeCreator {
	
	IOrderBuyerDao orderBuyerDao;

	public IBuyerPricingScheme createPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
        HsrMarketAdjustedBuyerPricingScheme hsrMarketAdjustedBuyerPricingScheme = new HsrMarketAdjustedBuyerPricingScheme(buyerId, buyerSkuMap);
        hsrMarketAdjustedBuyerPricingScheme.setOrderBuyerDao(orderBuyerDao);
        return hsrMarketAdjustedBuyerPricingScheme;
    }
	
	public IOrderBuyerDao getOrderBuyerDao() {
		return orderBuyerDao;
	}

	public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
		this.orderBuyerDao = orderBuyerDao;
	}
}