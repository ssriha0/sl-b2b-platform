package com.servicelive.wallet.creditcard;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;

// TODO: Auto-generated Javadoc
/**
 * Class RouteCreditCardBO.
 */
public class RouteCreditCardBO implements ICreditCardBO {

	/** searsCreditCard. */
	private ICreditCardBO searsCreditCard;

	/** thirdPartyCreditCard. */
	private ICreditCardBO thirdPartyCreditCard;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.ICreditCardBO#authorizeCardTransaction(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	public SLCreditCardVO authorizeCardTransaction(SLCreditCardVO cc) throws SLBusinessServiceException {

		return isSearsCard(cc) ? searsCreditCard.authorizeCardTransaction(cc) : thirdPartyCreditCard.authorizeCardTransaction(cc);
	}

	/**
	 * isSearsCard.
	 * 
	 * @param cc 
	 * 
	 * @return boolean
	 */
	protected boolean isSearsCard(SLCreditCardVO cc) {

		// check database table lu_card_types for more details
		// SEARS CARD TYPEs = 0,1,2,3,4
		// ThirdParty CARD TYPEs = 5,6,7,8

		long iCardTypeId = cc.getCardTypeId();

		if (iCardTypeId == CommonConstants.CARD_ID_SEARS || iCardTypeId == CommonConstants.CARD_ID_SEARS_PREMIER || iCardTypeId == CommonConstants.CARD_ID_SEARS_PLUS
			|| iCardTypeId == CommonConstants.CARD_ID_SEARS_COMMERCIAL || iCardTypeId == CommonConstants.CARD_ID_SEARS_MASTERCARD) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * setSearsCreditCard.
	 * 
	 * @param searsAuthService 
	 * 
	 * @return void
	 */
	public void setSearsCreditCard(ICreditCardBO searsAuthService) {

		this.searsCreditCard = searsAuthService;
	}

	/**
	 * setThirdPartyCreditCard.
	 * 
	 * @param thirdPartyAuthService 
	 * 
	 * @return void
	 */
	public void setThirdPartyCreditCard(ICreditCardBO thirdPartyAuthService) {

		this.thirdPartyCreditCard = thirdPartyAuthService;
	}

	public String getApplicationFlag(String appKey)
			throws SLBusinessServiceException {
		return searsCreditCard.getApplicationFlag(appKey);
	}
	
}
