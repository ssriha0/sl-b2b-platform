package com.servicelive.orderfulfillment.orderprep.pricing.creator;

import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;

public interface IBuyerPricingSchemeCreator {
    IBuyerPricingScheme createPricingScheme(Long buyerId, BuyerSkuMap buyerSkuMap);
}
