package com.servicelive.wallet.creditcard.mocks;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLCreditCardVO;
import com.servicelive.wallet.creditcard.ICreditCardBO;

// TODO: Auto-generated Javadoc
/**
 * Class MockCreditCardBO.
 */
public class MockCreditCardBO implements ICreditCardBO {

	/** authorizationCode. */
	private String authorizationCode;

	/** creditCards. */
	private List<SLCreditCardVO> creditCards;

	/** isAuthorizing. */
	private boolean isAuthorizing;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.creditcard.ICreditCardBO#authorizeCardTransaction(com.servicelive.wallet.creditcard.vo.CreditCardVO)
	 */
	public SLCreditCardVO authorizeCardTransaction(SLCreditCardVO creditCard) throws SLBusinessServiceException {

		creditCard.setAuthorized(isAuthorizing);
		creditCard.setAuthorizationCode(authorizationCode);
		if (creditCards == null) creditCards = new ArrayList<SLCreditCardVO>();
		creditCards.add(creditCard);
		return creditCard;
	}

	/**
	 * getCreditCards.
	 * 
	 * @return List<CreditCardVO>
	 */
	public List<SLCreditCardVO> getCreditCards() {

		return this.creditCards;
	}

	/**
	 * reset.
	 * 
	 * @return void
	 */
	public void reset() {

		creditCards = new ArrayList<SLCreditCardVO>();
		isAuthorizing = true;
		authorizationCode = null;
	}

	/**
	 * setAuthorizationCode.
	 * 
	 * @param authorizationCode 
	 * 
	 * @return void
	 */
	public void setAuthorizationCode(String authorizationCode) {

		this.authorizationCode = authorizationCode;
	}

	/**
	 * setAuthorizing.
	 * 
	 * @param isAuthorizing 
	 * 
	 * @return void
	 */
	public void setAuthorizing(boolean isAuthorizing) {

		this.isAuthorizing = isAuthorizing;
	}

}
