package com.newco.marketplace.tokenize;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;

/**
 * Interface IHSAuthServiceCreditCardBO.
 */
public interface IHSTokenizeServiceCreditCardBO {

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
	public TokenizeResponse tokenizeHSSTransaction(String accNum, String userName) throws SLBusinessServiceException;	
	
	/**Method to tokenize  Credit card closing service order from event handler
	 * @param soId
	 * @param accountNo
	 * @param resourceId
	 * @return
	 * @throws SLBusinessServiceException
	 * @throws Exception 
	 */
	public HsTokenizeResponse tokenizeHSSTransaction(String soId,String accountNo,Long resourceId)throws SLBusinessServiceException, Exception;

}
