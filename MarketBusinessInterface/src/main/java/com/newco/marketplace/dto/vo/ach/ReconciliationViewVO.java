package com.newco.marketplace.dto.vo.ach;

import java.util.Date;
import java.util.Map;

import com.sears.os.vo.SerializableBaseVO;

public class ReconciliationViewVO extends SerializableBaseVO{
private String transactionType;

private int numOfRecords;

private double amount;

public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public int getNumOfRecords() {
		return numOfRecords;
	}
	public void setNumOfRecords(int numOfRecords) {
		this.numOfRecords = numOfRecords;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	} 
}
