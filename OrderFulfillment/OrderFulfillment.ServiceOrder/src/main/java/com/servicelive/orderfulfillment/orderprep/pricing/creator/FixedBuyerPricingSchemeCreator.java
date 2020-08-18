package com.servicelive.orderfulfillment.orderprep.pricing.creator;

import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.pricing.FixedBuyerPricingScheme;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;

public class FixedBuyerPricingSchemeCreator implements IBuyerPricingSchemeCreator {
	
	IOrderBuyerDao orderBuyerDao;

	public IBuyerPricingScheme createPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
        FixedBuyerPricingScheme fixedBuyerPricingScheme = new FixedBuyerPricingScheme(buyerId, buyerSkuMap);
        fixedBuyerPricingScheme.setOrderBuyerDao(orderBuyerDao);
        return fixedBuyerPricingScheme;
    }

    public IOrderBuyerDao getOrderBuyerDao() {
		return orderBuyerDao;
	}

	public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
		this.orderBuyerDao = orderBuyerDao;
	}
}