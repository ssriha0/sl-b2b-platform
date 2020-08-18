package com.newco.marketplace.persistence.daoImpl.mobile;


import org.springframework.dao.DataAccessException;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOAccountActionsDao;
import com.sears.os.dao.impl.ABaseImplDao;


public class MobileSOAccountActionsDaoImpl extends ABaseImplDao implements IMobileSOAccountActionsDao{


	/**
	 * 
	 * to get corresponding card type id for card type
	 * 
	 * @param ccType
	 * @return
	 */
	public Integer getCardType(String ccType) throws DataServiceException{
		// TODO Auto-generated method stub
		Integer creditCardTypeId =  null;
		try{
			
			creditCardTypeId = (Integer)queryForObject("getCardType.query", ccType);
			
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getCardType() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return creditCardTypeId;
	}
	
}
