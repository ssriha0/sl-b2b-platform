package com.servicelive.orderfulfillment.lookup;

import java.util.List;
import java.util.Map;

import com.servicelive.domain.common.BuyerFeatureSetEnum;

public class BuyerFeatureLookup implements IQuickLookup, IRemoteServiceDependentLookup {
    private Map<BuyerFeatureSetEnum, List<Long>> featureToBuyerListMap;
    BuyerFeatureLookupInitializer initializer;

    boolean initialized;

    public void initialize() {
        initializer.initialize(this);
    }

    public void setFeatureToBuyerListMap(Map<BuyerFeatureSetEnum, List<Long>> featureToBuyerListMap) {
        this.featureToBuyerListMap = featureToBuyerListMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum feature, Long buyerId) {
        List<Long> buyersWithActiveFeature = featureToBuyerListMap.get(feature);
        return buyersWithActiveFeature != null && buyersWithActiveFeature.contains(buyerId);
    }

    public void setInitializer(BuyerFeatureLookupInitializer initializer) {
        this.initializer = initializer;
    }
}
