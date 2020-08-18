package com.newco.marketplace.business.iBusiness;

import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IAPISecurityBO {
	
	public String validateAuth(String authData, String buyerID) throws BusinessServiceException;
}
