/**
 * 
 */
package com.servicelive.common.vo;

import org.springframework.beans.BeanUtils;

import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.servicelive.common.exception.SLBusinessServiceException;


/**
 * Class CreditCardVO.
 */
public class SLCreditCardVO extends CreditCardVO {

	private static final long serialVersionUID = 6724803049756925008L;

	public SLCreditCardVO () {
		super();
	}
	
	public static SLCreditCardVO adapt(CreditCardVO ccVO) throws SLBusinessServiceException {
		if (ccVO == null){
			return null;
		}
		if (ccVO instanceof SLCreditCardVO)
			return (SLCreditCardVO) ccVO;

		return copy(ccVO);
	}
	
	public static SLCreditCardVO copy(CreditCardVO ccVO) throws SLBusinessServiceException{
		if (ccVO == null){
			return null;
		}

		SLCreditCardVO slccVO = new SLCreditCardVO();
		BeanUtils.copyProperties(ccVO, slccVO);
		return slccVO;
	}

}