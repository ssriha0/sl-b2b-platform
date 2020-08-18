package com.newco.marketplace.dto.vo.ach;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;
/**  
* TransactionBatchVO.java - This class holds the batch number and the list of process ids for that batch
* 
* @author  Siva
* @version 1.0  
*/
public class TransactionBatchVO extends SerializableBaseVO{

	private static final long serialVersionUID = 3239401725807301418L;
	private int batchNumber;
	private List<Integer> achProcessIds;
	private long processLogId;
	private StringBuffer achRecordsOutput; 
	private long processLogHistoryId;
	private long totalCreditProcessId;
	private int achTranscodeAccId;
	private int accountTypeId;
	private int ledgerTransEntryTypeId;
	private int achTransactionCode;
	private boolean entryRecordsExist;
	private String fileIdModifier;
	private String achDateTime;
	private String discretionaryData; // used for MID(Merchant ID) in case of provider withdrawal we use a different mid
	private boolean amountMismatchFlag=false;
	private double totalDebitAmount = 0.0;
	private double totalCreditAmount = 0.0;
	private long recordCounter = 0;

public boolean isEntryRecordsExist() {
	return entryRecordsExist;
}

public void setEntryRecordsExist(boolean entryRecordsExist) {
	this.entryRecordsExist = entryRecordsExist;
}

public long getProcessLogHistoryId() {
	return processLogHistoryId;
}

public void setProcessLogHistoryId(long processLogHistoryId) {
	this.processLogHistoryId = processLogHistoryId;
}

public StringBuffer getAchRecordsOutput() {
	return achRecordsOutput;
}

public void setAchRecordsOutput(StringBuffer achRecordsOutput) {
	this.achRecordsOutput = achRecordsOutput;
}

public long getProcessLogId() {
	return processLogId;
}

public void setProcessLogId(long processLogId) {
	this.processLogId = processLogId;
}

public List<Integer> getAchProcessIds() {
	return achProcessIds;
}

public void setAchProcessIds(List<Integer> achProcessIds) {
	this.achProcessIds = achProcessIds;
}

public int getBatchNumber() {
	return batchNumber;
}

public void setBatchNumber(int batchNumber) {
	this.batchNumber = batchNumber;
}

public long getTotalCreditProcessId() {
	return totalCreditProcessId;
}

public void setTotalCreditProcessId(long totalCreditProcessId) {
	this.totalCreditProcessId = totalCreditProcessId;
}

public int getAchTranscodeAccId() {
	return achTranscodeAccId;
}

public void setAchTranscodeAccId(int achTranscodeAccId) {
	this.achTranscodeAccId = achTranscodeAccId;
}

public int getAccountTypeId() {
	return accountTypeId;
}

public void setAccountTypeId(int accountTypeId) {
	this.accountTypeId = accountTypeId;
}

public int getLedgerTransEntryTypeId() {
	return ledgerTransEntryTypeId;
}

public void setLedgerTransEntryTypeId(int ledgerTransEntryTypeId) {
	this.ledgerTransEntryTypeId = ledgerTransEntryTypeId;
}

public int getAchTransactionCode() {
	return achTransactionCode;
}

public void setAchTransactionCode(int achTransactionCode) {
	this.achTransactionCode = achTransactionCode;
}
	public String getFileIdModifier() {
		return fileIdModifier;
	}

	public void setFileIdModifier(String fileIdModifier) {
		this.fileIdModifier = fileIdModifier;
	}

	public String getAchDateTime() {
		return achDateTime;
	}

	public void setAchDateTime(String achDateTime) {
		this.achDateTime = achDateTime;
	}

	public String getDiscretionaryData() {
		return discretionaryData;
	}

	public void setDiscretionaryData(String discretionaryData) {
		this.discretionaryData = discretionaryData;
	}

	public boolean isAmountMismatchFlag() {
		return amountMismatchFlag;
	}

	public void setAmountMismatchFlag(boolean amountMismatchFlag) {
		this.amountMismatchFlag = amountMismatchFlag;
	}

	public double getTotalDebitAmount() {
		return totalDebitAmount;
	}

	public void setTotalDebitAmount(double totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}

	public double getTotalCreditAmount() {
		return totalCreditAmount;
	}

	public void setTotalCreditAmount(double totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}

	public long getRecordCounter() {
		return recordCounter;
	}

	public void setRecordCounter(long recordCounter) {
		this.recordCounter = recordCounter;
	}
	

}
