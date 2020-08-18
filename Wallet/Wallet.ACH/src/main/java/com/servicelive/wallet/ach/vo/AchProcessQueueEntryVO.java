package com.servicelive.wallet.ach.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Class AchProcessQueueEntryVO.
 */
public class AchProcessQueueEntryVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 290354307939040938L;

	/** accountId. */
	private long accountId;

	/** achBatchAssocId. */
	private int achBatchAssocId;

	/** achProcessId. */
	private long achProcessId;

	/** entityId. */
	private long entityId;

	/** entityTypeId. */
	private long entityTypeId;

	/** fundingTypeId. */
	private long fundingTypeId;

	/** ledgerEntryId. */
	private long ledgerEntryId;

	/** ledgerTransactionId. */
	private Long ledgerTransactionId;

	/** modifiedDate. */
	private Timestamp modifiedDate;

	/** processStatusChgDate. */
	private Date processStatusChgDate;

	/** processStatusId. */
	private int processStatusId;

	/** reconciledIndicator. */
	private Integer reconciledIndicator;

	/** rejectCode. */
	private String rejectCode;

	/** rejectIndicator. */
	private boolean rejectIndicator;

	/** rejectReasonId. */
	private Integer rejectReasonId;

	/** tmpProcessStatusId. */
	private int tmpProcessStatusId;

	/** transactionAmount. */
	private double transactionAmount;

	/** transactionEntryTypeId. */
	private int transactionEntryTypeId;

	/** transactionTypeId. */
	private long transactionTypeId;
	
	private Long hsAuthRespId;
	
	private boolean hsLiveFlag;
	
	private Date createdDate;

	/**
	 * getAccountId.
	 * 
	 * @return long
	 */
	public long getAccountId() {

		return accountId;
	}

	/**
	 * getAchBatchAssocId.
	 * 
	 * @return int
	 */
	public int getAchBatchAssocId() {

		return achBatchAssocId;
	}

	/**
	 * getAchProcessId.
	 * 
	 * @return long
	 */
	public long getAchProcessId() {

		return achProcessId;
	}

	/**
	 * getEntityId.
	 * 
	 * @return long
	 */
	public long getEntityId() {

		return entityId;
	}

	/**
	 * getEntityTypeId.
	 * 
	 * @return long
	 */
	public long getEntityTypeId() {

		return entityTypeId;
	}

	/**
	 * getFundingTypeId.
	 * 
	 * @return long
	 */
	public long getFundingTypeId() {

		return fundingTypeId;
	}

	/**
	 * getLedgerEntryId.
	 * 
	 * @return long
	 */
	public long getLedgerEntryId() {

		return ledgerEntryId;
	}

	/**
	 * getLedgerTransactionId.
	 * 
	 * @return Long
	 */
	public Long getLedgerTransactionId() {

		return ledgerTransactionId;
	}

	/**
	 * getModifiedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getModifiedDate() {

		return modifiedDate;
	}

	/**
	 * getProcessStatusChgDate.
	 * 
	 * @return Date
	 */
	public Date getProcessStatusChgDate() {

		return processStatusChgDate;
	}

	/**
	 * getProcessStatusId.
	 * 
	 * @return int
	 */
	public int getProcessStatusId() {

		return processStatusId;
	}

	/**
	 * getReconciledIndicator.
	 * 
	 * @return Integer
	 */
	public Integer getReconciledIndicator() {

		return reconciledIndicator;
	}

	/**
	 * getRejectCode.
	 * 
	 * @return String
	 */
	public String getRejectCode() {

		return rejectCode;
	}

	/**
	 * getRejectReasonId.
	 * 
	 * @return Integer
	 */
	public Integer getRejectReasonId() {

		return rejectReasonId;
	}

	/**
	 * getTmpProcessStatusId.
	 * 
	 * @return int
	 */
	public int getTmpProcessStatusId() {

		return tmpProcessStatusId;
	}

	/**
	 * getTransactionAmount.
	 * 
	 * @return double
	 */
	public double getTransactionAmount() {

		return transactionAmount;
	}

	/**
	 * getTransactionEntryTypeId.
	 * 
	 * @return int
	 */
	public int getTransactionEntryTypeId() {

		return transactionEntryTypeId;
	}

	/**
	 * getTransactionTypeId.
	 * 
	 * @return long
	 */
	public long getTransactionTypeId() {

		return transactionTypeId;
	}

	/**
	 * isRejectIndicator.
	 * 
	 * @return boolean
	 */
	public boolean isRejectIndicator() {

		return rejectIndicator;
	}

	/**
	 * setAccountId.
	 * 
	 * @param accountId 
	 * 
	 * @return void
	 */
	public void setAccountId(long accountId) {

		this.accountId = accountId;
	}

	/**
	 * setAchBatchAssocId.
	 * 
	 * @param achBatchAssocId 
	 * 
	 * @return void
	 */
	public void setAchBatchAssocId(int achBatchAssocId) {

		this.achBatchAssocId = achBatchAssocId;
	}

	/**
	 * setAchProcessId.
	 * 
	 * @param achProcessId 
	 * 
	 * @return void
	 */
	public void setAchProcessId(long achProcessId) {

		this.achProcessId = achProcessId;
	}

	/**
	 * setEntityId.
	 * 
	 * @param entityId 
	 * 
	 * @return void
	 */
	public void setEntityId(long entityId) {

		this.entityId = entityId;
	}

	/**
	 * setEntityTypeId.
	 * 
	 * @param entityTypeId 
	 * 
	 * @return void
	 */
	public void setEntityTypeId(long entityTypeId) {

		this.entityTypeId = entityTypeId;
	}

	/**
	 * setFundingTypeId.
	 * 
	 * @param fundingTypeId 
	 * 
	 * @return void
	 */
	public void setFundingTypeId(long fundingTypeId) {

		this.fundingTypeId = fundingTypeId;
	}

	/**
	 * setLedgerEntryId.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return void
	 */
	public void setLedgerEntryId(long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	/**
	 * setLedgerTransactionId.
	 * 
	 * @param ledgerTransactionId 
	 * 
	 * @return void
	 */
	public void setLedgerTransactionId(Long ledgerTransactionId) {

		this.ledgerTransactionId = ledgerTransactionId;
	}

	/**
	 * setModifiedDate.
	 * 
	 * @param modifiedDate 
	 * 
	 * @return void
	 */
	public void setModifiedDate(Timestamp modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/**
	 * setProcessStatusChgDate.
	 * 
	 * @param processStatusChgDate 
	 * 
	 * @return void
	 */
	public void setProcessStatusChgDate(Date processStatusChgDate) {

		this.processStatusChgDate = processStatusChgDate;
	}

	/**
	 * setProcessStatusId.
	 * 
	 * @param processStatusId 
	 * 
	 * @return void
	 */
	public void setProcessStatusId(int processStatusId) {

		this.processStatusId = processStatusId;
	}

	/**
	 * setReconciledIndicator.
	 * 
	 * @param reconciledIndicator 
	 * 
	 * @return void
	 */
	public void setReconciledIndicator(int reconciledIndicator) {

		this.reconciledIndicator = reconciledIndicator;
	}

	/**
	 * setRejectCode.
	 * 
	 * @param rejectCode 
	 * 
	 * @return void
	 */
	public void setRejectCode(String rejectCode) {

		this.rejectCode = rejectCode;
	}

	/**
	 * setRejectIndicator.
	 * 
	 * @param rejectIndicator 
	 * 
	 * @return void
	 */
	public void setRejectIndicator(boolean rejectIndicator) {

		this.rejectIndicator = rejectIndicator;
	}

	/**
	 * setRejectReasonId.
	 * 
	 * @param rejectReasonId 
	 * 
	 * @return void
	 */
	public void setRejectReasonId(int rejectReasonId) {

		this.rejectReasonId = rejectReasonId;
	}

	/**
	 * setTmpProcessStatusId.
	 * 
	 * @param tmpProcessStatusId 
	 * 
	 * @return void
	 */
	public void setTmpProcessStatusId(int tmpProcessStatusId) {

		this.tmpProcessStatusId = tmpProcessStatusId;
	}

	/**
	 * setTransactionAmount.
	 * 
	 * @param transactionAmount 
	 * 
	 * @return void
	 */
	public void setTransactionAmount(double transactionAmount) {

		this.transactionAmount = transactionAmount;
	}

	/**
	 * setTransactionEntryTypeId.
	 * 
	 * @param transactionEntryTypeId 
	 * 
	 * @return void
	 */
	public void setTransactionEntryTypeId(int transactionEntryTypeId) {

		this.transactionEntryTypeId = transactionEntryTypeId;
	}

	/**
	 * setTransactionTypeId.
	 * 
	 * @param transactionTypeId 
	 * 
	 * @return void
	 */
	public void setTransactionTypeId(long transactionTypeId) {

		this.transactionTypeId = transactionTypeId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getHsAuthRespId() {
		return hsAuthRespId;
	}

	public void setHsAuthRespId(Long hsAuthRespId) {
		this.hsAuthRespId = hsAuthRespId;
	}
	public boolean isHsLiveFlag() {
		return hsLiveFlag;
	}

	public void setHsLiveFlag(boolean hsLiveFlag) {
		this.hsLiveFlag = hsLiveFlag;
	}
}
