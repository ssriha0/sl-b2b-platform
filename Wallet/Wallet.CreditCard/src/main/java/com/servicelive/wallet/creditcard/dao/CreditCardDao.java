package com.servicelive.wallet.creditcard.dao;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.common.vo.SLCreditCardVO;

// TODO: Auto-generated Javadoc
/**
 * Class CreditCardDao.
 */
public class CreditCardDao extends ABaseDao implements ICreditCardDao {

	/** logger. */
	private static final Logger logger = Logger.getLogger(CreditCardDao.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.dao.ICreditCardDao#getCreditCardResponseDetails(java.lang.String)
	 */
	public String getCreditCardResponseDetails(String response) throws DataServiceException {

		return (String) queryForObject("credit_card_response_code.query", response);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.dao.ICreditCardDao#saveAuthorizationResponse(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	public void saveAuthorizationResponse(SLCreditCardVO creditCardVO) throws DataServiceException {

		try {
			insert("creditCard.insertWSResponse", creditCardVO);
		} catch (Exception ex) {
			logger.info("[CreditCardDaoImpl.saveCardDetails - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
	public long saveHSSAuthorizationResponse(SLCreditCardVO creditCardVO) throws DataServiceException {
		long id = 0;
		try {
			id = (Long) getSqlMapClient().insert("creditCard.insertHSSResponse",creditCardVO);
			logger.info("Inside saveHSSAuthorizationResponse to save the auth details");
		} catch (Exception ex) {
			logger.info("[CreditCardDaoImpl.saveHSSAuthorizationResponse - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return id;
	}
	
	public String getApplicationFlag(String appKey) throws DataServiceException{
		String flagValue = null;
		try {
			flagValue=	(String) getSqlMapClient().queryForObject("applicaionFlag.query",appKey);
			logger.info("Inside getApplicationFlag to get the application flag value");
		} catch (Exception ex) {
			logger.info("[CreditCardDaoImpl.getApplicationFlag - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return flagValue;
	}
	/**
	 * @throws DataServiceException
	 */
	public void updateMaskedAccountNoToken(SLCreditCardVO details)throws DataServiceException{
		try{
			getSqlMapClient().update("updateMaskedAccountToken.query", details);;
		}catch (Exception ex) {
			logger.info("[CreditCardDaoImpl.updateMaskedAccountNoToken - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}
}
