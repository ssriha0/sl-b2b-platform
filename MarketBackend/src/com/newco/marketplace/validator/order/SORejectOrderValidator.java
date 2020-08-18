package com.newco.marketplace.validator.order;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ResponseSoVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public class SORejectOrderValidator extends ABaseValidator implements OrderConstants{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2459569160173617222L;
	private static final Logger logger = Logger.getLogger(SORejectOrderValidator.class.getName());
	
	public ValidatorResponse validate(ResponseSoVO responseSoVo) throws Exception {

		ValidatorResponse resp = new ValidatorResponse(VALID_RC, VALID_RC);
		logger.warn("INSIDE SORejectOrderValidator::: validate()::::::");
		
		if(StringUtils.isEmpty(responseSoVo.getSoId())) {
			setError(resp, SERVICE_ORDER_ID_REQUIRED);
			logger.debug(SERVICE_ORDER_ID_REQUIRED);
			return resp;
		}
		
		if(isEmpty(responseSoVo.getResourceId())) {
			setError(resp, BUYER_ID_REQUIRED);
			logger.debug(BUYER_ID_REQUIRED);
			return resp;
		}
		
		if(isEmpty(responseSoVo.getReasonId())) {
			setError(resp, SELECT_REJECT_REASON_CODE);
			logger.debug(SELECT_REJECT_REASON_CODE);
			return resp;
		}
		
		return resp;
	}
}