package com.newco.marketplace.dto.vo.trans;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class TransactionRecordVO extends SerializableBaseVO{
	

private double amount;
private Integer transactionType;
private String creditCardNumber;
private String authorizationCode;
private long achProcessId;
private long ledgerEntryId;
private String cardTypeCode;
private String cardExpireDate;
private String achTransCodeId;


public String getAchTransCodeId() {
	return achTransCodeId;
}
public void setAchTransCodeId(String achTransCodeId) {
	this.achTransCodeId = achTransCodeId;
}
public String getCardExpireDate() {
	return cardExpireDate;
}
public void setCardExpireDate(String cardExpireDate) {
	this.cardExpireDate = cardExpireDate;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getCreditCardNumber() {
	return creditCardNumber;
}
public void setCreditCardNumber(String creditCardNumber) {
	this.creditCardNumber = creditCardNumber;
}
public String getAuthorizationCode() {
	return authorizationCode;
}
public void setAuthorizationCode(String authorizationCode) {
	this.authorizationCode = authorizationCode;
}
public long getAchProcessId() {
	return achProcessId;
}
public void setAchProcessId(long achProcessId) {
	this.achProcessId = achProcessId;
}
public long getLedgerEntryId() {
	return ledgerEntryId;
}
public void setLedgerEntryId(long ledgerEntryId) {
	this.ledgerEntryId = ledgerEntryId;
}
public String getCardTypeCode() {
	return cardTypeCode;
}
public void setCardTypeCode(String cardTypeCode) {
	this.cardTypeCode = cardTypeCode;
}
public Integer getTransactionType() {
	return transactionType;
}
public void setTransactionType(Integer transactionType) {
	this.transactionType = transactionType;
}


}
