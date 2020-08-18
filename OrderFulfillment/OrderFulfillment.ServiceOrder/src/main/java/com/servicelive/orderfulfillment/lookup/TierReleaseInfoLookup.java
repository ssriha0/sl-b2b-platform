package com.servicelive.orderfulfillment.lookup;

import com.servicelive.marketplatform.common.vo.TierReleaseInfoVO;
import com.servicelive.orderfulfillment.common.ServiceOrderException;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

public class TierReleaseInfoLookup implements IQuickLookup, IRemoteServiceDependentLookup {
    Map<Integer, SortedMap<Integer, TierReleaseInfoVO>> spnAndTierToTierReleaseInfoMap;
    Map<Integer, String> spnAndNetworkName;

    TierReleaseInfoLookupInitializer initializer;

    boolean initialized;

    public void initialize() {
      //  initializer.initialize(this);
    }

    public void setSpnAndTierToTierReleaseInfoMap(Map<Integer, SortedMap<Integer, TierReleaseInfoVO>> spnAndTierToTierReleaseInfoMap) {
        this.spnAndTierToTierReleaseInfoMap = spnAndTierToTierReleaseInfoMap;
    }

    public void setSpnAndNetworkName(Map<Integer, String> spnAndNetworkName) {
        this.spnAndNetworkName = spnAndNetworkName;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public TierReleaseInfoVO getTierReleaseInfo(Integer spnId, Integer currentTier) {
        if (!isInitialized()) throw new ServiceOrderException("TierReleaseInfoLookup not yet initialized.");
        Map<Integer, TierReleaseInfoVO> tierToTierReleaseInfoMap = spnAndTierToTierReleaseInfoMap.get(spnId);
        if (tierToTierReleaseInfoMap == null) return null;
        return tierToTierReleaseInfoMap.get(currentTier);
    }

    public TierReleaseInfoVO getStartingTier(Integer spnId) {
        if (!isInitialized()) throw new ServiceOrderException("TierReleaseInfoLookup not yet initialized.");
        SortedMap<Integer, TierReleaseInfoVO> tierToTierReleaseInfoMap = spnAndTierToTierReleaseInfoMap.get(spnId);
        if (tierToTierReleaseInfoMap == null) return null;
        Integer firstTierId = null;
        try {
            firstTierId = tierToTierReleaseInfoMap.firstKey();
        } catch (NoSuchElementException e) {
            return null;
        }
        return tierToTierReleaseInfoMap.get(firstTierId);
    }

    public void setInitializer(TierReleaseInfoLookupInitializer initializer) {
        this.initializer = initializer;
    }

    public String getSPNetName(Integer spnId) {
        return spnAndNetworkName.get(spnId);  
    }
}
