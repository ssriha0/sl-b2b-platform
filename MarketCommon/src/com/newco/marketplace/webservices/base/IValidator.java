package com.newco.marketplace.webservices.base;

import com.newco.marketplace.webservices.base.response.ValidatorResponse;

public interface IValidator {

	
	public ValidatorResponse validate( ParamMap pMap ) throws Exception;

}
