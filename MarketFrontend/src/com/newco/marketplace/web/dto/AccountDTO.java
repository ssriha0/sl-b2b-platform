package com.newco.marketplace.web.dto;

public class AccountDTO extends SerializedBaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1147526220949446593L;
	private String accountNameNum;
	private Long accountId;
	private String accountTypeId;
	private boolean activeInd;
	private Long creditCardToken;
	private String zipCode;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountNameNum() {
		return accountNameNum;
	}
	public void setAccountNameNum(String accountNameNum) {
		this.accountNameNum = accountNameNum;
	}
	public String getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public boolean isActiveInd() {
		return activeInd;
	}
	public void setActiveInd(boolean activeInd) {
		this.activeInd = activeInd;
	}
	public Long getCreditCardToken() {
		return creditCardToken;
	}
	public void setCreditCardToken(Long creditCardToken) {
		this.creditCardToken = creditCardToken;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}	

}
