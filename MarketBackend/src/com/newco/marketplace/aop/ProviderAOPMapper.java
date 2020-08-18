package com.newco.marketplace.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.OrderConstants;

public class ProviderAOPMapper extends BaseAOPMapper {
	
private static final Logger logger = Logger.getLogger(ProviderAOPMapper.class);
	
	public ProviderAOPMapper() {
		super();
	}

	public ProviderAOPMapper(Object[] params){
		super(params);
	}
	
	/**
	 * Populates the HashMap
	 * @param aopParams
	 * @param security
	 * @return
	 */
	public Map<String, Object> mapParams(Map<String, Object> aopParams, SecurityContext security){
		Map<String, Object> hmParams = aopParams;
		
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
		hmParams.put(AOPConstants.AOP_SECURITY_CONTEXT, security);
		
		return hmParams;
	}
	
	/**
	 * @return 
	 */
	public HashMap<String, Object> sendWCINotRequiredAlert(){
		logger.debug("AOPMapper--> sendWCIAlert()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		return hmParams;
	}

}
