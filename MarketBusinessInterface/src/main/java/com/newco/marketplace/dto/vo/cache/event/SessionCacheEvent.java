package com.newco.marketplace.dto.vo.cache.event;

import java.util.HashMap;


public class SessionCacheEvent extends CacheEvent {

	public SessionCacheEvent(HashMap<String, Object> params) {
		super(params);
		clearBuyerDetails = true;
		clearAllProvidersDashboardAmount=true;
		clearAllProvidersDetails=true;
		clearAllProvidersSummary=true;
		clearBuyerDashboardAmounts=true;
		clearBuyerDetails=true;
		clearBuyerSummary=true;
		clearProvidersDashboardAmount=true;
		clearProvidersDetails=true;
		clearProvidersSummary=true;
		incrementCondAcceptCount=true;
		incrementRejectCount=true;
	}

}
