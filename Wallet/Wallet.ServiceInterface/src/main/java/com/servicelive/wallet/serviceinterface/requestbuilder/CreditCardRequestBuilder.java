package com.servicelive.wallet.serviceinterface.requestbuilder;

import com.servicelive.common.vo.SLCreditCardVO;

// TODO: Auto-generated Javadoc
/**
 * Class CreditCardRequestBuilder.
 */
public class CreditCardRequestBuilder extends ABaseRequestBuilder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.wallet.client.ICreditCardRequestBuilder#authorizeCardTransaction(int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, double)
	 */
	public SLCreditCardVO authorizeCardTransaction(Long fundingTypeId, String serviceOrderId, String cardHolderName, String expireDate, String billingAddress1,
		String billingAddress2, String billingCity, String billingState, String billingZipcode, Long cardTypeId, String cardNumber, String cardVerificationNumber,
		Double transactionAmount) {

		SLCreditCardVO creditCard = new SLCreditCardVO();

		setCardInfo(creditCard, cardTypeId, cardNumber, cardVerificationNumber);
		setTransaction(creditCard, transactionAmount);
		setCardHolderInfo(creditCard, cardHolderName, expireDate, billingAddress1, billingAddress2, billingCity, billingState, billingZipcode);

		return creditCard;
	}

	/**
	 * setCardHolderInfo.
	 * 
	 * @param creditCard 
	 * @param cardHolderName 
	 * @param expireDate 
	 * @param billingAddress1 
	 * @param billingAddress2 
	 * @param billingCity 
	 * @param billingState 
	 * @param billingZipcode 
	 * 
	 * @return void
	 */
	private void setCardHolderInfo(SLCreditCardVO creditCard, String cardHolderName, String expireDate, String billingAddress1, String billingAddress2, String billingCity,
		String billingState, String billingZipcode) {

		creditCard.setCardHolderName(cardHolderName);
		creditCard.setExpireDate(expireDate);
		creditCard.setBillingAddress1(billingAddress1);
		creditCard.setBillingAddress2(billingAddress2);
		creditCard.setBillingCity(billingCity);
		creditCard.setBillingState(billingState);
		creditCard.setZipcode(billingZipcode);
	}

	/**
	 * setCardInfo.
	 * 
	 * @param creditCard 
	 * @param cardTypeId 
	 * @param cardNumber 
	 * @param cardVerificationNumber 
	 * 
	 * @return void
	 */
	private void setCardInfo(SLCreditCardVO creditCard, Long cardTypeId, String cardNumber, String cardVerificationNumber) {

		creditCard.setCardTypeId(cardTypeId);
		creditCard.setCardNo(cardNumber);
		creditCard.setCardVerificationNo(cardVerificationNumber);
	}

	/**
	 * setTransaction.
	 * 
	 * @param creditCard 
	 * @param transactionAmount 
	 * 
	 * @return void
	 */
	private void setTransaction(SLCreditCardVO creditCard, double transactionAmount) {

		creditCard.setTransactionAmount(transactionAmount);
	}
}
