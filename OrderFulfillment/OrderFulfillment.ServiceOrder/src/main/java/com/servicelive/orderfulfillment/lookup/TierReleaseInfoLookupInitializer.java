package com.servicelive.orderfulfillment.lookup;

import com.servicelive.marketplatform.common.vo.TierReleaseInfoVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class TierReleaseInfoLookupInitializer {
    IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO;
    protected final Logger logger = Logger.getLogger(getClass());

    public void initialize(TierReleaseInfoLookup tierReleaseInfoLookup) {
    	logger.info("Inside TierReleaseInfoLookupInitializer ");
    	long start = System.currentTimeMillis();
        List<TierReleaseInfoVO> tierReleaseInfoList = marketPlatformRoutingRulesBO.retrieveAllAvailableTierReleaseInfo();

        Map<Integer, SortedMap<Integer, TierReleaseInfoVO>> spnAndTierToTierReleaseInfoMap = new HashMap<Integer, SortedMap<Integer, TierReleaseInfoVO>>();
        Map<Integer, String> spnAndNetworkName = new HashMap<Integer, String>();
        if (tierReleaseInfoList != null) {
            for (TierReleaseInfoVO tierReleaseInfoVO : tierReleaseInfoList) {
                SortedMap<Integer, TierReleaseInfoVO> tierToTierReleaseInfoMap;
                if (spnAndTierToTierReleaseInfoMap.containsKey(tierReleaseInfoVO.getSpnId())) {
                    tierToTierReleaseInfoMap = spnAndTierToTierReleaseInfoMap.get(tierReleaseInfoVO.getSpnId());
                } else {
                    tierToTierReleaseInfoMap = new TreeMap<Integer, TierReleaseInfoVO>();
                    spnAndTierToTierReleaseInfoMap.put(tierReleaseInfoVO.getSpnId(), tierToTierReleaseInfoMap);
                    spnAndNetworkName.put(tierReleaseInfoVO.getSpnId(), tierReleaseInfoVO.getNetworkName());
                }
                tierToTierReleaseInfoMap.put(tierReleaseInfoVO.getCurrentTier(), tierReleaseInfoVO);
            }
        }

        tierReleaseInfoLookup.setSpnAndTierToTierReleaseInfoMap(spnAndTierToTierReleaseInfoMap);
        tierReleaseInfoLookup.setSpnAndNetworkName(spnAndNetworkName);
        tierReleaseInfoLookup.setInitialized(true);
        long end = System.currentTimeMillis();
        logger.info("Inside TierReleaseInfoLookupInitializer..>>load Time Taken>>"+(end-start));

    }

    public void setMarketPlatformRoutingRulesBO(IMarketPlatformRoutingRulesBO marketPlatformRoutingRulesBO) {
        this.marketPlatformRoutingRulesBO = marketPlatformRoutingRulesBO;
    }
}
