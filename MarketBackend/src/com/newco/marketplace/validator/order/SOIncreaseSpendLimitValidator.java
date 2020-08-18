package com.newco.marketplace.validator.order;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.IncreaseSpendLimitRequestVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class SOIncreaseSpendLimitValidator extends ABaseValidator implements OrderConstants{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8144527333578300353L;
	private static final Logger logger = Logger.getLogger(SOIncreaseSpendLimitValidator.class.getName());
	
	public ValidatorResponse validate(IncreaseSpendLimitRequestVO obj, boolean validate) throws Exception {

		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		logger.debug("INSIDE SOIncreaseSpendLimitValidator::: validate()::::::");
		
		if(StringUtils.isEmpty(obj.getServiceOrderID())) {
			setError(resp, SERVICE_ORDER_ID_REQUIRED);
			logger.debug(SERVICE_ORDER_ID_REQUIRED);
			return resp;
		}
		
		if(isEmpty(obj.getBuyerId())) {
			setError(resp, BUYER_ID_REQUIRED);
			logger.debug(BUYER_ID_REQUIRED);
			return resp;
		}
		
		
		logger.debug("End of SOIncreaseSpendLimitValidator");
		return resp;
	}
}