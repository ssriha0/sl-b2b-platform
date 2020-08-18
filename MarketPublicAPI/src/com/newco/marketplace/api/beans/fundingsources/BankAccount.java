package com.newco.marketplace.api.beans.fundingsources;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author ndixit
 * POJO for bank account : Used in funding source APIs.
 */
@XStreamAlias("bankAccount")
public class BankAccount {
	
	//Bank
	
	@XStreamAlias("accountHolderName")   
	public String accountHolderName;
	
	@XStreamAlias("bankName")   
	public String bankName;
	
	@XStreamAlias("accountNumber")   
	public String accountNumber;
	
	@XStreamAlias("routingNumber")   
	public String routingNumber;
	
	@XStreamAlias("bankAccountType")   
	public Integer bankAccountType;
	
	@XStreamAlias("fundingSourceId")   
	public Long bankAccountId;

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

	public Integer getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

}
