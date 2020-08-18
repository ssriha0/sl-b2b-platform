package com.newco.marketplace.business.businessImpl;

import com.newco.marketplace.business.iBusiness.IAPISecurityBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.persistence.iDao.IAPISecurityDAO;
import org.apache.log4j.Logger;

public class APISecurityBOImpl implements IAPISecurityBO{
	private static final Logger logger = Logger
			.getLogger(APISecurityBOImpl.class);
	private IAPISecurityDAO apiSecurityDAO;
	
	public String validateAuth(String authData, String buyerID) throws BusinessServiceException {
		// SLT-3625
		String key=null;
			logger.info("Entering ApiSecurityBOImpl-->ValidateAuth()");
			try {
				 key = apiSecurityDAO.getauthkey(buyerID);
				
			} catch (Exception ex) {
				logger.error("Exception Occured in ApiSecurityBOImpl-->ValidateAuth()");
				throw new BusinessServiceException(ex.getMessage());
			}
			return key;
		}

	public IAPISecurityDAO getApiSecurityDAO() {
		return apiSecurityDAO;
	}

	public void setApiSecurityDAO(IAPISecurityDAO apiSecurityDAO) {
		this.apiSecurityDAO = apiSecurityDAO;
	}
	
}
