package com.servicelive.wallet.creditcard.mocks;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.creditcard.dao.ICreditCardDao;

// TODO: Auto-generated Javadoc
/**
 * Class MockCreditCardDao.
 */
public class MockCreditCardDao implements ICreditCardDao {

	/** creditCardDao. */
	private ICreditCardDao creditCardDao;

	/** invocationCount. */
	private int invocationCount;

	/** lastSavedAuthResponse. */
	private SLCreditCardVO lastSavedAuthResponse;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.dao.ICreditCardDao#getCreditCardResponseDetails(java.lang.String)
	 */
	public String getCreditCardResponseDetails(String response) throws DataServiceException {
		return creditCardDao.getCreditCardResponseDetails(response);
	}

	/**
	 * getInvocationCount.
	 * 
	 * @return int
	 */
	public int getInvocationCount() {
		return invocationCount;
	}

	/**
	 * getLastSavedAuthResponse.
	 * 
	 * @return CreditCardVO
	 */
	public SLCreditCardVO getLastSavedAuthResponse() {
		return lastSavedAuthResponse;
	}

	/**
	 * reset.
	 * 
	 * @return void
	 */
	public void reset() {
		this.invocationCount = 0;
		this.lastSavedAuthResponse = null;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.dao.ICreditCardDao#saveAuthorizationResponse(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	public void saveAuthorizationResponse(SLCreditCardVO creditCardVO) throws DataServiceException {
		this.creditCardDao.saveAuthorizationResponse(creditCardVO);
		this.invocationCount++;
		this.lastSavedAuthResponse = creditCardVO;
	}

	/**
	 * setCreditCardDao.
	 * 
	 * @param creditCardDao 
	 * 
	 * @return void
	 */
	public void setCreditCardDao(ICreditCardDao creditCardDao) {
		this.creditCardDao = creditCardDao;
	}
}
