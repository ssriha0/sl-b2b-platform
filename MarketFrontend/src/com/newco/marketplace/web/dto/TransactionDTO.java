package com.newco.marketplace.web.dto;

public class TransactionDTO extends SerializedBaseDTO
{
	private Integer transactionNumber;
	private String date;
	private String time;
	private String type;
	private String soId;
	private String amount;
	private String balanceDesc;
	private boolean amountRed;
	private boolean balanceRed;
	private String status;
	private String availableBalance;
	private String accountNumber;
	private String bankName;
	private String modifiedDate;
	
	

	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	

	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public boolean isAmountRed() {
		return amountRed;
	}
	public void setAmountRed(boolean amountRed) {
		this.amountRed = amountRed;
	}
	public String getBalanceDesc() {
		return balanceDesc;
	}
	public void setBalanceDesc(String balanceDesc) {
		this.balanceDesc = balanceDesc;
	}
	public boolean isBalanceRed() {
		return balanceRed;
	}
	public void setBalanceRed(boolean balanceRed) {
		this.balanceRed = balanceRed;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
	
	
}
