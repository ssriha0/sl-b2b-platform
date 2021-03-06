package com.newco.marketplace.dto.vo.cache.event;

import java.util.HashMap;

import com.newco.marketplace.interfaces.AOPConstants;

public class CreateSOCacheEvent extends CacheEvent {
	
	public CreateSOCacheEvent(HashMap<String, Object> parms) {
		// extract parameters
		buyerId = ((Integer)parms.get(AOPConstants.AOP_BUYER_ID)).intValue();
		buyerResourceId = ((Integer)parms.get(AOPConstants.AOP_BUYER_RESOURCE_ID)).intValue();
		soId = ((String)parms.get(AOPConstants.AOP_SO_ID));
		if(parms.get(AOPConstants.AOP_PROVIDER_ID) != null){
			vendorId = ((Integer)parms.get(AOPConstants.AOP_PROVIDER_ID)).intValue();
		}
		else{
			vendorId = -1;
		}
		if(parms.get(AOPConstants.AOP_VENDOR_RESOURCE_ID) != null){
			vendorResourceId = ((Integer)parms.get(AOPConstants.AOP_VENDOR_RESOURCE_ID)).intValue();
		}
		else{
			vendorResourceId = -1;
		}
				
		// setflags
		clearBuyerSummary = true;
	}

}
