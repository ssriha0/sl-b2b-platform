package com.newco.marketplace.validator.order.util;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;


public class OrderDetailsValidator extends ABaseValidator implements OrderConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3839512011801180614L;

	public ValidatorResponse validate(Integer buyerId, String soId) throws Exception {
	
		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		
		// check some required fields
		if (isEmpty(buyerId))
			setError(resp, BUYER_ID_REQUIRED);
	
		if(StringUtils.isEmpty(soId))
			setError(resp, SERVICE_ORDER_NUMBER_REQUIRED);

		return resp;
	}
}
