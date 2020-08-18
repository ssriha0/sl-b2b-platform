package com.newco.marketplace.dto.vo.fullfillment;

import com.sears.os.vo.SerializableBaseVO;

public class GLDetailVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = 1L;
	
	public String entryId;
	public String npsOrder;
	public String npsUnit;
	public String glUnit;
	public String glDivision;
	public String glAccount;
	public String glCategory;
	public String ledgerRule;
	public String soFundingType;
	public String transactionType;
	public String transactionId;
	public Double transactionAmount;
	public String orderNumber;
	public String glDatePosted;
	public String transactionDate;
	public String peopleSoftFile;
	public Integer glProcessId;
	public String getEntryId() {
		return entryId;
	}
	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
	public String getNpsOrder() {
		return npsOrder;
	}
	public void setNpsOrder(String npsOrder) {
		this.npsOrder = npsOrder;
	}
	public String getNpsUnit() {
		return npsUnit;
	}
	public void setNpsUnit(String npsUnit) {
		this.npsUnit = npsUnit;
	}
	public String getGlUnit() {
		return glUnit;
	}
	public void setGlUnit(String glUnit) {
		this.glUnit = glUnit;
	}
	public String getGlDivision() {
		return glDivision;
	}
	public void setGlDivision(String glDivision) {
		this.glDivision = glDivision;
	}
	public String getGlAccount() {
		return glAccount;
	}
	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}
	public String getGlCategory() {
		return glCategory;
	}
	public void setGlCategory(String glCategory) {
		this.glCategory = glCategory;
	}
	public String getLedgerRule() {
		return ledgerRule;
	}
	public void setLedgerRule(String ledgerRule) {
		this.ledgerRule = ledgerRule;
	}
	public String getSoFundingType() {
		return soFundingType;
	}
	public void setSoFundingType(String soFundingType) {
		this.soFundingType = soFundingType;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getGlDatePosted() {
		return glDatePosted;
	}
	public void setGlDatePosted(String glDatePosted) {
		this.glDatePosted = glDatePosted;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getPeopleSoftFile() {
		return peopleSoftFile;
	}
	public void setPeopleSoftFile(String peopleSoftFile) {
		this.peopleSoftFile = peopleSoftFile;
	}
	public Integer getGlProcessId() {
		return glProcessId;
	}
	public void setGlProcessId(Integer glProcessId) {
		this.glProcessId = glProcessId;
	}
	
}
