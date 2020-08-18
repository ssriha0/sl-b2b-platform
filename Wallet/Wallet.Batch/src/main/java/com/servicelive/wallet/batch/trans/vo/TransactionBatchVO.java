package com.servicelive.wallet.batch.trans.vo;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * TransactionBatchVO.java - This class holds the batch number and the list of process ids for that batch
 * 
 * @author Siva
 * @version 1.0
 */
public class TransactionBatchVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3239401725807301418L;

	/** The account type id. */
	private int accountTypeId;

	/** The ach date time. */
	private String achDateTime;

	/** The ach process ids. */
	private List<Long> achProcessIds;

	/** The ach records output. */
	private StringBuffer achRecordsOutput;

	/** The ach transaction code. */
	private int achTransactionCode;

	/** The ach transcode acc id. */
	private int achTranscodeAccId;

	/** The amount mismatch flag. */
	private boolean amountMismatchFlag = false;

	/** The batch number. */
	private int batchNumber;

	/** The discretionary data. */
	private String discretionaryData; // used for MID(Merchant ID) in case of provider withdrawal we use a different mid

	/** The entry records exist. */
	private boolean entryRecordsExist;

	/** The file id modifier. */
	private String fileIdModifier;

	/** The ledger trans entry type id. */
	private int ledgerTransEntryTypeId;

	/** The process log history id. */
	private long processLogHistoryId;

	/** The process log id. */
	private long processLogId;

	/** The record counter. */
	private long recordCounter = 0;

	/** The total credit amount. */
	private double totalCreditAmount = 0.0;

	/** The total credit process id. */
	private long totalCreditProcessId;

	/** The total debit amount. */
	private double totalDebitAmount = 0.0;

	/**
	 * Gets the account type id.
	 * 
	 * @return the account type id
	 */
	public int getAccountTypeId() {

		return accountTypeId;
	}

	/**
	 * Gets the ach date time.
	 * 
	 * @return the ach date time
	 */
	public String getAchDateTime() {

		return achDateTime;
	}

	/**
	 * Gets the ach process ids.
	 * 
	 * @return the ach process ids
	 */
	public List<Long> getAchProcessIds() {

		return achProcessIds;
	}

	/**
	 * Gets the ach records output.
	 * 
	 * @return the ach records output
	 */
	public StringBuffer getAchRecordsOutput() {

		return achRecordsOutput;
	}

	/**
	 * Gets the ach transaction code.
	 * 
	 * @return the ach transaction code
	 */
	public int getAchTransactionCode() {

		return achTransactionCode;
	}

	/**
	 * Gets the ach transcode acc id.
	 * 
	 * @return the ach transcode acc id
	 */
	public int getAchTranscodeAccId() {

		return achTranscodeAccId;
	}

	/**
	 * Gets the batch number.
	 * 
	 * @return the batch number
	 */
	public int getBatchNumber() {

		return batchNumber;
	}

	/**
	 * Gets the discretionary data.
	 * 
	 * @return the discretionary data
	 */
	public String getDiscretionaryData() {

		return discretionaryData;
	}

	/**
	 * Gets the file id modifier.
	 * 
	 * @return the file id modifier
	 */
	public String getFileIdModifier() {

		return fileIdModifier;
	}

	/**
	 * Gets the ledger trans entry type id.
	 * 
	 * @return the ledger trans entry type id
	 */
	public int getLedgerTransEntryTypeId() {

		return ledgerTransEntryTypeId;
	}

	/**
	 * Gets the process log history id.
	 * 
	 * @return the process log history id
	 */
	public long getProcessLogHistoryId() {

		return processLogHistoryId;
	}

	/**
	 * Gets the process log id.
	 * 
	 * @return the process log id
	 */
	public long getProcessLogId() {

		return processLogId;
	}

	/**
	 * Gets the record counter.
	 * 
	 * @return the record counter
	 */
	public long getRecordCounter() {

		return recordCounter;
	}

	/**
	 * Gets the total credit amount.
	 * 
	 * @return the total credit amount
	 */
	public double getTotalCreditAmount() {

		return totalCreditAmount;
	}

	/**
	 * Gets the total credit process id.
	 * 
	 * @return the total credit process id
	 */
	public long getTotalCreditProcessId() {

		return totalCreditProcessId;
	}

	/**
	 * Gets the total debit amount.
	 * 
	 * @return the total debit amount
	 */
	public double getTotalDebitAmount() {

		return totalDebitAmount;
	}

	/**
	 * Checks if is amount mismatch flag.
	 * 
	 * @return true, if is amount mismatch flag
	 */
	public boolean isAmountMismatchFlag() {

		return amountMismatchFlag;
	}

	/**
	 * Checks if is entry records exist.
	 * 
	 * @return true, if is entry records exist
	 */
	public boolean isEntryRecordsExist() {

		return entryRecordsExist;
	}

	/**
	 * Sets the account type id.
	 * 
	 * @param accountTypeId the new account type id
	 */
	public void setAccountTypeId(int accountTypeId) {

		this.accountTypeId = accountTypeId;
	}

	/**
	 * Sets the ach date time.
	 * 
	 * @param achDateTime the new ach date time
	 */
	public void setAchDateTime(String achDateTime) {

		this.achDateTime = achDateTime;
	}

	/**
	 * Sets the ach process ids.
	 * 
	 * @param achProcessIds the new ach process ids
	 */
	public void setAchProcessIds(List<Long> achProcessIds) {

		this.achProcessIds = achProcessIds;
	}

	/**
	 * Sets the ach records output.
	 * 
	 * @param achRecordsOutput the new ach records output
	 */
	public void setAchRecordsOutput(StringBuffer achRecordsOutput) {

		this.achRecordsOutput = achRecordsOutput;
	}

	/**
	 * Sets the ach transaction code.
	 * 
	 * @param achTransactionCode the new ach transaction code
	 */
	public void setAchTransactionCode(int achTransactionCode) {

		this.achTransactionCode = achTransactionCode;
	}

	/**
	 * Sets the ach transcode acc id.
	 * 
	 * @param achTranscodeAccId the new ach transcode acc id
	 */
	public void setAchTranscodeAccId(int achTranscodeAccId) {

		this.achTranscodeAccId = achTranscodeAccId;
	}

	/**
	 * Sets the amount mismatch flag.
	 * 
	 * @param amountMismatchFlag the new amount mismatch flag
	 */
	public void setAmountMismatchFlag(boolean amountMismatchFlag) {

		this.amountMismatchFlag = amountMismatchFlag;
	}

	/**
	 * Sets the batch number.
	 * 
	 * @param batchNumber the new batch number
	 */
	public void setBatchNumber(int batchNumber) {

		this.batchNumber = batchNumber;
	}

	/**
	 * Sets the discretionary data.
	 * 
	 * @param discretionaryData the new discretionary data
	 */
	public void setDiscretionaryData(String discretionaryData) {

		this.discretionaryData = discretionaryData;
	}

	/**
	 * Sets the entry records exist.
	 * 
	 * @param entryRecordsExist the new entry records exist
	 */
	public void setEntryRecordsExist(boolean entryRecordsExist) {

		this.entryRecordsExist = entryRecordsExist;
	}

	/**
	 * Sets the file id modifier.
	 * 
	 * @param fileIdModifier the new file id modifier
	 */
	public void setFileIdModifier(String fileIdModifier) {

		this.fileIdModifier = fileIdModifier;
	}

	/**
	 * Sets the ledger trans entry type id.
	 * 
	 * @param ledgerTransEntryTypeId the new ledger trans entry type id
	 */
	public void setLedgerTransEntryTypeId(int ledgerTransEntryTypeId) {

		this.ledgerTransEntryTypeId = ledgerTransEntryTypeId;
	}

	/**
	 * Sets the process log history id.
	 * 
	 * @param processLogHistoryId the new process log history id
	 */
	public void setProcessLogHistoryId(long processLogHistoryId) {

		this.processLogHistoryId = processLogHistoryId;
	}

	/**
	 * Sets the process log id.
	 * 
	 * @param processLogId the new process log id
	 */
	public void setProcessLogId(long processLogId) {

		this.processLogId = processLogId;
	}

	/**
	 * Sets the record counter.
	 * 
	 * @param recordCounter the new record counter
	 */
	public void setRecordCounter(long recordCounter) {

		this.recordCounter = recordCounter;
	}

	/**
	 * Sets the total credit amount.
	 * 
	 * @param totalCreditAmount the new total credit amount
	 */
	public void setTotalCreditAmount(double totalCreditAmount) {

		this.totalCreditAmount = totalCreditAmount;
	}

	/**
	 * Sets the total credit process id.
	 * 
	 * @param totalCreditProcessId the new total credit process id
	 */
	public void setTotalCreditProcessId(long totalCreditProcessId) {

		this.totalCreditProcessId = totalCreditProcessId;
	}

	/**
	 * Sets the total debit amount.
	 * 
	 * @param totalDebitAmount the new total debit amount
	 */
	public void setTotalDebitAmount(double totalDebitAmount) {

		this.totalDebitAmount = totalDebitAmount;
	}

}
