package com.newco.marketplace.web.dto.simple;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class CreditCardDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2396151951744453090L;
	
	private Long cardId;
	private String creditCardName;
	private String creditCardType;
	private String creditCardHolderName;
	private String creditCardNumber;
	private String securityCode;
	private String expirationMonth;
	private String expirationYear;
	
	private String billingAddress1;
	private String billingAddress2;
	private String billingApartmentNumber;
	private String billingCity;
	private String billingState;
	private String billingZipCode;
	private String billingLocationName;
	
	private boolean isSearsWhiteCard;
	
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}
	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = StringUtils.trim(creditCardHolderName);
	}
	public String getCreditCardName() {
		return creditCardName;
	}
	public void setCreditCardName(String creditCardName) {
		this.creditCardName = StringUtils.trim(creditCardName);
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = StringUtils.trim(creditCardNumber);
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public String getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getBillingAddress1() {
		return billingAddress1;
	}
	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = StringUtils.trim(billingAddress1);
	}
	public String getBillingAddress2() {
		return billingAddress2;
	}
	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = StringUtils.trim(billingAddress2);
	}
	public String getBillingCity() {
		return billingCity;
	}
	public void setBillingCity(String billingCity) {
		this.billingCity = StringUtils.trim(billingCity);
	}
	public String getBillingState() {
		return billingState;
	}
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	public String getBillingZipCode() {
		return billingZipCode;
	}
	public void setBillingZipCode(String billingZipCode) {
		this.billingZipCode = StringUtils.trim(billingZipCode);
	}
	public String getBillingLocationName() {
		return billingLocationName;
	}
	public void setBillingLocationName(String billingLocationName) {
		this.billingLocationName = billingLocationName;
	}
	public String getBillingApartmentNumber() {
		return billingApartmentNumber;
	}
	public void setBillingApartmentNumber(String billingApartmentNumber) {
		this.billingApartmentNumber = billingApartmentNumber;
	}	
	public boolean getIsSearsWhiteCard() {
		return isSearsWhiteCard;
	}
	public void setIsSearsWhiteCard(boolean isSearsWhiteCard) {
		this.isSearsWhiteCard = isSearsWhiteCard;
	}	
}
