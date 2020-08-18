package com.newco.marketplace.persistence.iDao;

import com.newco.marketplace.exception.core.DataServiceException;

public interface IAPISecurityDAO {
	
	public String getauthkey(String buyerID) throws DataServiceException;

}
