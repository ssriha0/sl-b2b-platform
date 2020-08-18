package com.servicelive.orderfulfillment.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.buyer.BuyerHoldTime;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;

public class BuyerHoldTimeLookupInitializer {
    protected final Logger logger = Logger.getLogger(getClass());

    IMarketPlatformBuyerBO marketPlatformBuyerBO;

    public void initialize(BuyerHoldTimeLookup lookup){
    	logger.debug("initializing buyer hold times");
    	List<BuyerHoldTime> buyerHoldTimes = marketPlatformBuyerBO.retrieveAllBuyerHoldTime();
    	lookup.setHoldTimes(createHoldTimeMap(buyerHoldTimes));
    	lookup.setMaxDayDiff(createMaxDayDiff(buyerHoldTimes));
    	lookup.setInitialized(true);
    	logger.debug("done initializing buyer hold time");
    }

	private Map<Long, Integer> createMaxDayDiff(List<BuyerHoldTime> buyerHoldTimes) {
		Map<Long, Integer> holdTimeMax = new HashMap<Long, Integer>();
		for(BuyerHoldTime holdTime : buyerHoldTimes){
			if(holdTimeMax.containsKey(holdTime.getBuyerId())){
				if(holdTimeMax.get(holdTime.getBuyerId()).intValue() < holdTime.getDayDiff().intValue())
					holdTimeMax.put(holdTime.getBuyerId(), holdTime.getDayDiff());
			} else {
				holdTimeMax.put(holdTime.getBuyerId(), holdTime.getDayDiff());
			}
		}
		logger.debug("******hold time max ======================= " + holdTimeMax);
		return holdTimeMax;
	}

	private Map<Long, Map<Integer, BuyerHoldTime>> createHoldTimeMap(List<BuyerHoldTime> buyerHoldTimes) {
		Map<Long, Map<Integer, BuyerHoldTime>> holdTimeMap = new HashMap<Long, Map<Integer,BuyerHoldTime>>();
		for(BuyerHoldTime holdTime : buyerHoldTimes){
			Long buyerId = holdTime.getBuyerId();
			Integer dayDiff = holdTime.getDayDiff();
			if(!holdTimeMap.containsKey(buyerId)) holdTimeMap.put(buyerId, new HashMap<Integer, BuyerHoldTime>());
			holdTimeMap.get(buyerId).put(dayDiff, holdTime);
		}
		logger.debug("******hold time map ======================== " + holdTimeMap);
		return holdTimeMap;
	}

	public void setMarketPlatformBuyerBO(
			IMarketPlatformBuyerBO marketPlatformBuyerBO) {
		this.marketPlatformBuyerBO = marketPlatformBuyerBO;
	}
}
