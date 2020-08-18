/**
 * 
 */
package com.servicelive.wallet.creditcard.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.vo.SLCreditCardVO;

// TODO: Auto-generated Javadoc
/**
 * Interface ICreditCardDao.
 */
/**
 * @author Reen_Jossy
 *
 */
public interface ICreditCardDao {

	/**
	 * getCreditCardResponseDetails.
	 * 
	 * @param response 
	 * 
	 * @return String
	 * 
	 * @throws DataServiceException 
	 */
	public String getCreditCardResponseDetails(String response) throws DataServiceException;

	/**
	 * saveAuthorizationResponse.
	 * 
	 * @param creditCardVO 
	 * 
	 * @return void
	 * 
	 * @throws DataServiceException 
	 */
	public void saveAuthorizationResponse(SLCreditCardVO creditCardVO) throws DataServiceException;

	/**
	 * saveHSSAuthorizationResponse.
	 * 
	 * @param creditCardVO 
	 * 
	 * @return long
	 * 
	 * @throws DataServiceException 
	 */
	public long saveHSSAuthorizationResponse(SLCreditCardVO creditCardVO) throws DataServiceException;
	
	
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getApplicationFlag(String appKey) throws DataServiceException;

	/**
	 * @param details
	 * @throws DataServiceException
	 */
	public void updateMaskedAccountNoToken(SLCreditCardVO details)throws DataServiceException;
	
}
