package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.Map;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;

public interface IOrderBuyer {
    Long getBuyerId();
    Buyer getBuyerInfo();
    void initialize();
    boolean isInitialized();
    void setInitialized(boolean initialized);
    IBuyerPricingScheme getBuyerPricingScheme();
    BuyerSkuMap getBuyerSkuMap();
    BuyerTemplateMap getTemplateMap();
    Integer getBuyerReferenceTypeId(String referenceTypeName);

    void setBuyerInfo(Buyer buyerInfo);
    void setBuyerPricingScheme(IBuyerPricingScheme buyerPricingScheme);
    void setBuyerSkuMap(BuyerSkuMap buyerSkuMap);
    void setOrderBuyerTemplateMap(BuyerTemplateMap orderBuyerTemplateMap);
    void setBuyerRefTypeMap(Map<String, Integer> buyerRefTypeMap);
}
