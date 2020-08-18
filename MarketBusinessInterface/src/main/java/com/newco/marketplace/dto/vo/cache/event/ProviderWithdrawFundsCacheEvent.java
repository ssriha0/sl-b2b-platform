/**
 * 
 */
package com.newco.marketplace.dto.vo.cache.event;

import java.util.HashMap;

import com.newco.marketplace.interfaces.AOPConstants;

/**
 * @author schavda
 *
 */
public class ProviderWithdrawFundsCacheEvent extends CacheEvent {
	
	public ProviderWithdrawFundsCacheEvent(HashMap<String, Object> parms) {
		
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
		
		clearAllProvidersSummary = true;
	}
	

}
