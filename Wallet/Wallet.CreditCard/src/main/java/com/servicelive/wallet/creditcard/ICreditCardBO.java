package com.servicelive.wallet.creditcard;

import com.servicelive.common.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;

// TODO: Auto-generated Javadoc
/**
 * Interface ICreditCardBO.
 */
public interface ICreditCardBO {

	/**
	 * authorizeCardTransaction.
	 * 
	 * @param cc 
	 * 
	 * @return CreditCardVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	SLCreditCardVO authorizeCardTransaction(SLCreditCardVO cc) throws SLBusinessServiceException;

	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 * @throws com.servicelive.common.exception.DataServiceException 
	 * @throws SLBusinessServiceException 
	 */
	String getApplicationFlag(String appKey) throws  SLBusinessServiceException;

}
