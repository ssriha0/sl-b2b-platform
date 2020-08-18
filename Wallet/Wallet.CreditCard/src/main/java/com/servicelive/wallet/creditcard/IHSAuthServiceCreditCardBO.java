package com.servicelive.wallet.creditcard;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;

/**
 * Interface IHSAuthServiceCreditCardBO.
 */
public interface IHSAuthServiceCreditCardBO {

	/**
	 * Method to authorize Credit card while adding credit card and while adding fund
	 * 
	 * @param cc 
	 * @param userName 
	 * 
	 * @return SLCreditCardVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public SLCreditCardVO authorizeHSSTransaction(SLCreditCardVO cc, String userName) throws SLBusinessServiceException;
	
	/**
	 *  Method to authorize Credit card while refunding fund
	 * @param cc
	 * @param userName
	 * @return
	 * @throws SLBusinessServiceException
	 */
	public SLCreditCardVO refundHSTransaction(SLCreditCardVO cc, String userName)
			throws SLBusinessServiceException;	
	

}
