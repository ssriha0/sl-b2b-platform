package com.newco.marketplace.api.beans.fundingsources;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("creditCard")
public class CreditCardAccount {
	@XStreamAlias("accountHolderName")   
	public String accountHolderName;
	
	@XStreamAlias("cardType")   
	public Integer cardType;           //Visa/Master/Amex/Discover
	
	@XStreamAlias("cardNumber")   
	public String cardNumber;
	
	@XStreamAlias("billingAddress1")   
	public String billingAddress1;
	
	@XStreamAlias("billingAddress2")   
	public String billingAddress2;
	
	@XStreamAlias("billingCity")   
	public String billingCity;
	
	@XStreamAlias("billingState")   
	public String billingState;
	
	@XStreamAlias("billingZip")   
	public String billingZip;
	
	@XStreamAlias("fundingSourceId")   
	public Long ccAccountId;
	
	@XStreamAlias("expirationDate")   
	public String expirationDate; //Back end uses String, need to convert into date here.

	@XStreamAlias("defaultCard")   
	public boolean defaultCard; //Back end uses String, need to convert into date here.

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBillingAddress1() {
		return billingAddress1;
	}

	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}

	public String getBillingAddress2() {
		return billingAddress2;
	}

	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	public Long getCcAccountId() {
		return ccAccountId;
	}

	public void setCcAccountId(Long ccAccountId) {
		this.ccAccountId = ccAccountId;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean getDefaultCard() {
		return defaultCard;
	}

	public void setDefaultCard(boolean defaultCard) {
		this.defaultCard = defaultCard;
	}

}
