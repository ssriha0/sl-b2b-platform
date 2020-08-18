package com.servicelive.orderfulfillment.orderprep.buyer;

import org.apache.log4j.Logger;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.orderprep.pricing.creator.IBuyerPricingSchemeCreator;
import com.servicelive.orderfulfillment.common.OFConstants;

public class OrderBuyerInitializer implements IOrderBuyerInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformBuyerBO marketPlatformBuyerBO;
    IBuyerPricingSchemeCreator buyerPricingSchemeCreator;
    BuyerOrderTemplateLoader buyerOrderTemplateLoader;
    BuyerSkuLoader buyerSkuLoader;
    IBuyerReferenceTypeLoader buyerReferenceTypeLoader;
    RemoteServiceDependentOrderBuyerInitializer remoteServiceDependentOrderBuyerInitializer;

    public void initialize(IOrderBuyer orderBuyer) {
        logger.info("\n\nInitializing order buyer " + orderBuyer.getBuyerId() + "\n\n");
        orderBuyer.setBuyerSkuMap(buyerSkuLoader.getBuyerSkuMap(orderBuyer.getBuyerId()));
        if (buyerPricingSchemeCreator != null) {
            orderBuyer.setBuyerPricingScheme(buyerPricingSchemeCreator.createPricingScheme(orderBuyer.getBuyerId(), orderBuyer.getBuyerSkuMap()));
        }
        	if (buyerPricingSchemeCreator != null) {
        		orderBuyer.setBuyerPricingScheme(buyerPricingSchemeCreator.createPricingScheme(orderBuyer.getBuyerId(), orderBuyer.getBuyerSkuMap()));
        	}
       	initializeDependentProperties(orderBuyer);
     }

    private void initializeDependentProperties(IOrderBuyer orderBuyer){
        try {
            remoteServiceDependentOrderBuyerInitializer.setOrderBuyer(orderBuyer);
            remoteServiceDependentOrderBuyerInitializer.setBuyerOrderTemplateLoader(buyerOrderTemplateLoader);
            remoteServiceDependentOrderBuyerInitializer.setBuyerReferenceTypeLoader(buyerReferenceTypeLoader);
            remoteServiceDependentOrderBuyerInitializer.setMarketPlatformBuyerBO(marketPlatformBuyerBO);
        } catch (Exception ex) {
            logger.error("OrderBuyer object not fully initialized for buyer " + orderBuyer.getBuyerId(), ex);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }

    public void setBuyerPricingSchemeCreator(IBuyerPricingSchemeCreator buyerPricingSchemeCreator) {
        this.buyerPricingSchemeCreator = buyerPricingSchemeCreator;
    }

    public void setBuyerOrderTemplateLoader(BuyerOrderTemplateLoader buyerOrderTemplateLoader) {
        this.buyerOrderTemplateLoader = buyerOrderTemplateLoader;
    }

    public void setBuyerSkuLoader(BuyerSkuLoader buyerSkuLoader) {
        this.buyerSkuLoader = buyerSkuLoader;
    }

    public void setBuyerReferenceTypeLoader(IBuyerReferenceTypeLoader buyerReferenceTypeLoader) {
        this.buyerReferenceTypeLoader = buyerReferenceTypeLoader;
    }

    public void setRemoteServiceDependentOrderBuyerInitializer(RemoteServiceDependentOrderBuyerInitializer remoteServiceDependentOrderBuyerInitializer) {
        this.remoteServiceDependentOrderBuyerInitializer = remoteServiceDependentOrderBuyerInitializer;
    }
}
