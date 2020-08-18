package com.newco.marketplace.api.beans.fundingsources;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author ndixit
 * POJO for bank or credit card source type. 
 */
public class AccountSourceType {
	
		//Credit Card
		@XStreamAlias("accountHolderName")
		private String accountHolderName;
		
		@XStreamAlias("cardType")
		private String cardType;
		
		@XStreamAlias("cardNumber")
		private String cardNumber;
		
		@XStreamAlias("expirationDate")   
		public String expirationDate; //Back end uses String, need to convert into date here.
		
		@XStreamAlias("billingAddress1")
		private String billingAddress1;
		
		@XStreamAlias("billingAddress2")
		private String billingAddress2;
		
		@XStreamAlias("city")
		private String city;
		
		@XStreamAlias("state")
		private String state;
		
		@XStreamAlias("zip")
		private String zip;
		
		
		//Bank
		
		@XStreamAlias("bankAccountHolderName")
		private String bankAccountHolderName;
		
		@XStreamAlias("bankName")
		private String bankName;
		
		@XStreamAlias("accountNumber")
		private String accountNumber;
		
		@XStreamAlias("routingNumber")
		private String routingNumber;
		
		@XStreamAlias("accountTypeId")
		private Integer accountTypeId;
		
		@XStreamAlias("accountDescription")
		private String accountDescription;
		
		
		public String getAccountDescription() {
			return accountDescription;
		}
		public void setAccountDescription(String accountDescription) {
			this.accountDescription = accountDescription;
		}
		public String getAccountHolderName() {
			return accountHolderName;
		}
		public void setAccountHolderName(String accountHolderName) {
			this.accountHolderName = accountHolderName;
		}
		public String getCardType() {
			return cardType;
		}
		public void setCardType(String cardType) {
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
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getAccountNumber() {
			return accountNumber;
		}
		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}
		public String getRoutingNumber() {
			return routingNumber;
		}
		public void setRoutingNumber(String routingNumber) {
			this.routingNumber = routingNumber;
		}
		public Integer getAccountTypeId() {
			return accountTypeId;
		}
		public void setAccountType(Integer accountTypeId) {
			this.accountTypeId = accountTypeId;
		}
		public String getExpirationDate() {
			return expirationDate;
		}
		public void setExpirationDate(String expirationDate) {
			this.expirationDate = expirationDate;
		}
		public String getBankAccountHolderName() {
			return bankAccountHolderName;
		}
		public void setBankAccountHolderName(String bankAccountHolderName) {
			this.bankAccountHolderName = bankAccountHolderName;
		}
		public void setAccountTypeId(Integer accountTypeId) {
			this.accountTypeId = accountTypeId;
		}
		
	}
