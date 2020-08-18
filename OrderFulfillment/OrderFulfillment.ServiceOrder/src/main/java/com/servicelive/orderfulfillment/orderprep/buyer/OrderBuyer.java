package com.servicelive.orderfulfillment.orderprep.buyer;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.orderfulfillment.orderprep.buyersku.BuyerSkuMap;
import com.servicelive.orderfulfillment.orderprep.pricing.IBuyerPricingScheme;

public class OrderBuyer implements IOrderBuyer {
    protected final Logger logger = Logger.getLogger(getClass());

    Long buyerId;
    Buyer buyerInfo;
    IBuyerPricingScheme buyerPricingScheme;

    BuyerTemplateMap orderBuyerTemplateMap;
    Map<String, Integer> buyerRefTypeMap;
    BuyerSkuMap buyerSkuMap;
    IOrderBuyerInitializer orderBuyerInitializer;
    boolean initialized = false;

    public void initialize() {
    	if(orderBuyerInitializer != null){
    		orderBuyerInitializer.initialize(this);
    	} 		
    	else{
    		initialized = true; //Since initializer is not present and/or it is not needed we will assume that the buyer object is initialized
    	}    		
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Buyer getBuyerInfo() {
        return buyerInfo;
    }

    public void setBuyerInfo(Buyer buyerInfo) {
        this.buyerInfo = buyerInfo;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public IBuyerPricingScheme getBuyerPricingScheme() {
        return buyerPricingScheme;
    }

    public BuyerSkuMap getBuyerSkuMap() {
        return buyerSkuMap;
    }

    public BuyerTemplateMap getTemplateMap() {
        return orderBuyerTemplateMap;
    }

    public Integer getBuyerReferenceTypeId(String referenceTypeName) {
        return buyerRefTypeMap.get(referenceTypeName.toUpperCase());
    }

    public void setBuyerPricingScheme(IBuyerPricingScheme buyerPricingScheme) {
        this.buyerPricingScheme = buyerPricingScheme;
    }

    public void setBuyerSkuMap(BuyerSkuMap buyerSkuMap) {
        this.buyerSkuMap = buyerSkuMap;
    }

    public void setOrderBuyerTemplateMap(BuyerTemplateMap orderBuyerTemplateMap) {
        this.orderBuyerTemplateMap = orderBuyerTemplateMap;
    }

    public void setBuyerRefTypeMap(Map<String, Integer> buyerRefTypeMap) {
        this.buyerRefTypeMap = buyerRefTypeMap;
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setOrderBuyerInitializer(IOrderBuyerInitializer orderBuyerInitializer) {
        this.orderBuyerInitializer = orderBuyerInitializer;
    }
}
