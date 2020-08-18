package com.newco.marketplace.validator.order;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class RouteSOValidator extends ABaseValidator implements OrderConstants{

	
/**
	 * 
	 */
	private static final long serialVersionUID = 349188271438912949L;
private static final Logger logger = Logger.getLogger(RouteSOValidator.class.getName());
	
	public ValidatorResponse validate(String serviceOrderID) throws Exception {

		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		logger.warn("INSIDE RouteSOValidator::: validate()::::::");
		
		if(StringUtils.isEmpty(serviceOrderID)) {
			setError(resp, SERVICE_ORDER_ID_REQUIRED);
			return resp;
		}
		return resp;
	}
	
	
}
