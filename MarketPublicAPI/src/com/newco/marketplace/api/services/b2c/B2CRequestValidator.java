package com.newco.marketplace.api.services.b2c;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.b2c.IB2CGenericBO;


public class B2CRequestValidator {
	private static Logger logger = Logger.getLogger(B2CRequestValidator.class);
	private IB2CGenericBO b2CGenericBO;
	
	public IB2CGenericBO getB2cGenericBO() {
		return b2CGenericBO;
	}
	public void setB2CGenericBO(IB2CGenericBO b2CGenericBO) {
		this.b2CGenericBO = b2CGenericBO;
	}
	
	
}
