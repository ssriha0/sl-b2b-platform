package com.servicelive.orderfulfillment.lookup;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.buyer.BuyerHoldTime;

public class BuyerHoldTimeLookup implements IQuickLookup, IRemoteServiceDependentLookup {
    protected final Logger logger = Logger.getLogger(getClass());

	private static final Integer DEFAULT = 30;
	Map<Long, Map<Integer, BuyerHoldTime>> holdTimes;
	Map<Long, Integer> maxDayDiff;
	BuyerHoldTimeLookupInitializer initializer;

    boolean initialized;

	public void initialize() {
		initializer.initialize(this);
	}

	public Integer getHoldTime(Long buyerId, Integer daysDiff){
		if (!initialized) initialize();
		Integer maxDiff = maxDayDiff.get(buyerId);
		if(maxDiff == null) {
			logger.debug("Did not find the max for the buyer " + buyerId);
			return DEFAULT; //Buyer does not have hold time settings in the database
		}
		if(daysDiff.intValue() > maxDiff.intValue()) daysDiff = maxDiff;
		BuyerHoldTime holdTime = holdTimes.get(buyerId).get(daysDiff);
		if(holdTime == null){
			logger.debug("Did not find the hold time for the buyer " + buyerId + " days diff " + daysDiff);
			return DEFAULT; //Buyer does not have the hold time settings for the days difference we are looking for
		}
		return holdTime.getHoldTime();
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public void setHoldTimes(Map<Long, Map<Integer, BuyerHoldTime>> holdTimes) {
		this.holdTimes = holdTimes;
	}

	public void setMaxDayDiff(Map<Long, Integer> maxDayDiff) {
		this.maxDayDiff = maxDayDiff;
	}

	public void setInitializer(BuyerHoldTimeLookupInitializer initializer) {
		this.initializer = initializer;
	}

}
