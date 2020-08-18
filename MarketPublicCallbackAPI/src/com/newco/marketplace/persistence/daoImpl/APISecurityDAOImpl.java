package com.newco.marketplace.persistence.daoImpl;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.IAPISecurityDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class APISecurityDAOImpl extends ABaseImplDao implements IAPISecurityDAO{
	private static final Logger logger = Logger
			.getLogger(APISecurityDAOImpl.class);
	@Override
	public String getauthkey(String buyerID) throws DataServiceException  {
			String authkey;
			try {	
				authkey = (String) queryForObject("getApiKey.query", buyerID);	

			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				throw new DataServiceException(ex.getMessage(), ex);
			}
			return authkey;
	}	
}
