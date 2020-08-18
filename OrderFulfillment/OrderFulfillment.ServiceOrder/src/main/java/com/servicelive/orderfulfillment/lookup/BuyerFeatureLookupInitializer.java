package com.servicelive.orderfulfillment.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.buyer.SimpleBuyerFeature;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;

public class BuyerFeatureLookupInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformBuyerBO marketPlatformBuyerBO;

    public void initialize(BuyerFeatureLookup buyerFeatureLookup) {
        List<SimpleBuyerFeature> simpleBuyerFeatures = marketPlatformBuyerBO.retrieveAllSimpleBuyerFeatures();
        buyerFeatureLookup.setFeatureToBuyerListMap(createFeatureToBuyerListMap(simpleBuyerFeatures));
        buyerFeatureLookup.setInitialized(true);
    }

    private Map<BuyerFeatureSetEnum, List<Long>> createFeatureToBuyerListMap(List<SimpleBuyerFeature> simpleBuyerFeatures) {
        Map<BuyerFeatureSetEnum, List<Long>> featureToBuyerListMap = new HashMap<BuyerFeatureSetEnum, List<Long>>();
        if (simpleBuyerFeatures!=null) {
            for (SimpleBuyerFeature simpleBuyerFeature : simpleBuyerFeatures) {
            	if(simpleBuyerFeature.getBuyerId()!=null && simpleBuyerFeature.getBuyerId()==3000){
            		logger.info("BuyerId HSR:"+simpleBuyerFeature.getBuyerId());
            		logger.info("BuyerId HSR Feature:"+simpleBuyerFeature.getFeature());
            		logger.info("BuyerId HSR Feature Value:"+simpleBuyerFeature.isActive());
            	}else{
            		logger.info("BuyerId non HSR:"+simpleBuyerFeature.getBuyerId());
            	}
            	
                BuyerFeatureSetEnum feature;
                try {
                    feature = BuyerFeatureSetEnum.valueOf(simpleBuyerFeature.getFeature());
                } catch (IllegalArgumentException e) {
                    logger.error("Unable to initialize buyer list for feature " + simpleBuyerFeature.getFeature(), e);
                    continue;
                }
                if (simpleBuyerFeature.isActive()) {
                    List<Long> buyersWithActiveFeature;
                    if (featureToBuyerListMap.containsKey(feature)) {
                        buyersWithActiveFeature = featureToBuyerListMap.get(feature);
                    } else {
                        buyersWithActiveFeature = new ArrayList<Long>();
                        featureToBuyerListMap.put(feature, buyersWithActiveFeature);
                    }
                    buyersWithActiveFeature.add(simpleBuyerFeature.getBuyerId().longValue());
                }
            }
        }
        return featureToBuyerListMap;
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////

    public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
        this.marketPlatformBuyerBO = marketPlatformBuyerBO;
    }
}
