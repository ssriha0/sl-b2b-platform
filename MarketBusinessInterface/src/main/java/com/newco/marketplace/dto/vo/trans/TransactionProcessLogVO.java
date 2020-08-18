package com.newco.marketplace.dto.vo.trans;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class TransactionProcessLogVO extends SerializableBaseVO{
private String generatedFileName;
private String generatedServer;
private int transRecordCount;
private double transDepositTotal;
private double transRefundTotal;
private Timestamp createdDate;
private Timestamp modifiedDate;
public String getGeneratedFileName() {
	return generatedFileName;
}
public void setGeneratedFileName(String generatedFileName) {
	this.generatedFileName = generatedFileName;
}
public String getGeneratedServer() {
	return generatedServer;
}
public void setGeneratedServer(String generatedServer) {
	this.generatedServer = generatedServer;
}
public int getTransRecordCount() {
	return transRecordCount;
}
public void setTransRecordCount(int transRecordCount) {
	this.transRecordCount = transRecordCount;
}
public Timestamp getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(Timestamp createdDate) {
	this.createdDate = createdDate;
}
public Timestamp getModifiedDate() {
	return modifiedDate;
}
public void setModifiedDate(Timestamp modified_date) {
	this.modifiedDate = modified_date;
}
public double getTransDepositTotal() {
	return transDepositTotal;
}
public void setTransDepositTotal(double transDepositTotal) {
	this.transDepositTotal = transDepositTotal;
}
public double getTransRefundTotal() {
	return transRefundTotal;
}
public void setTransRefundTotal(double transRefundTotal) {
	this.transRefundTotal = transRefundTotal;
}

}
