package com.newco.marketplace.aop;

import java.util.HashMap;

import com.newco.marketplace.interfaces.AOPConstants;

/**
 * This class holds the data that will be transferred 
 * from AOP to AOP application frameworks(Logging, Alert, Cache)  
 */
public class AOPHashMap extends HashMap<String, Object> {

	private static final long serialVersionUID = -6686142168610205921L;

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		for (String key : this.keySet()) {
	
			if(!AOPConstants.AOP_SERVICE_ORDER.equals(key)) {
				Object val = this.get(key);
				sb.append(key);
				sb.append("=");
				sb.append(val);
				sb.append(AOPConstants.AOP_PARAMS_SEPARATATOR);
			}
			
		}
		
		return sb.toString();
	}	

}
