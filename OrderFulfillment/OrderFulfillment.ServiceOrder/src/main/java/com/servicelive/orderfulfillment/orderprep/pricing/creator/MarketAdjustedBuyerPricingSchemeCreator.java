package com.servicelive.orderfulfillment.orderprep.pricing.creator;

import com.servicelive.orderfulfillment.dao.IOrderBuyerDao;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;
import com.servicelive.orderfulfillment.orderprep.pricing.MarketAdjustedBuyerPricingScheme;

public class MarketAdjustedBuyerPricingSchemeCreator implements IBuyerPricingSchemeCreator {
    IOrderBuyerDao orderBuyerDao;
    String defaultStoreNo;

    public IBuyerPricingScheme createPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap) {
        MarketAdjustedBuyerPricingScheme marketAdjustedBuyerPricingScheme = new MarketAdjustedBuyerPricingScheme(buyerId, buyerSkuMap);
        if (defaultStoreNo != null) {
            marketAdjustedBuyerPricingScheme.setDefaultStoreNo(defaultStoreNo);
        }
        marketAdjustedBuyerPricingScheme.setOrderBuyerDao(orderBuyerDao);
        return marketAdjustedBuyerPricingScheme;
    }
    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderBuyerDao(IOrderBuyerDao orderBuyerDao) {
        this.orderBuyerDao = orderBuyerDao;
    }

    public void setDefaultStoreNo(String defaultStoreNo) {
        this.defaultStoreNo = defaultStoreNo;
    }
}