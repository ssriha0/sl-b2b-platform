package com.servicelive.orderfulfillment.orderprep.buyer;

import org.apache.log4j.Logger;

import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.common.RemoteServiceStartupDependentInitializer;

public class RemoteServiceDependentOrderBuyerInitializer implements RemoteServiceStartupDependentInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformBuyerBO marketPlatformBuyerBO;
    IBuyerReferenceTypeLoader buyerReferenceTypeLoader;
    BuyerOrderTemplateLoader buyerOrderTemplateLoader;
    IOrderBuyer orderBuyer;

    public void doRemoteServiceDependentInitialization() {
    	if(null!=orderBuyer){
    		if(null==orderBuyer.getBuyerId()){
    			logger.info("Order buyer id is null");
    		}
    		logger.info("Before setBuyerInfo initialize with buyer id: "+orderBuyer.getBuyerId());
            orderBuyer.setBuyerInfo(marketPlatformBuyerBO.retrieveBuyer(orderBuyer.getBuyerId()));
            logger.info("Before setOrderBuyerTemplateMap initialize with buyer id: "+orderBuyer.getBuyerId());
            orderBuyer.setOrderBuyerTemplateMap(buyerOrderTemplateLoader.getBuyerTemplateMap(orderBuyer.getBuyerId()));
            logger.info("Before setBuyerRefTypeMap initialize with buyer id: "+orderBuyer.getBuyerId());
            orderBuyer.setBuyerRefTypeMap(buyerReferenceTypeLoader.getBuyerReferenceNameToIdMap(orderBuyer.getBuyerId()));
            orderBuyer.setInitialized(true);
            logger.info("Before initialize for buyer id: "+orderBuyer.getBuyerId());
    	}else{
    		logger.info("Order buyer object is null");
    	}
    	
    }

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }

    public void setBuyerReferenceTypeLoader(IBuyerReferenceTypeLoader buyerReferenceTypeLoader) {
        this.buyerReferenceTypeLoader = buyerReferenceTypeLoader;
    }

    public void setBuyerOrderTemplateLoader(BuyerOrderTemplateLoader buyerOrderTemplateLoader) {
        this.buyerOrderTemplateLoader = buyerOrderTemplateLoader;
    }

    public void setOrderBuyer(IOrderBuyer orderBuyer) {
        this.orderBuyer = orderBuyer;
    }
}
