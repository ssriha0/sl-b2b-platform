package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * Class TransactionHistoryVO.
 */
public class TransactionHistoryVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -8574094832290153146L;

	/** accountId. */
	private String accountId;

	/** accountNumber. */
	private String accountNumber;

	/** achProcessId. */
	private Integer achProcessId;

	/** achReconciledInd. */
	private Integer achReconciledInd;

	/** achRejectId. */
	private Integer achRejectId;

	/** availableBalance. */
	private String availableBalance;

	/** bankName. */
	private String bankName;

	/** buyerAdminInd. */
	private boolean buyerAdminInd;

	/** credDebInd. */
	private Integer credDebInd;

	/** entityId. */
	private Integer entityId;

	/** entityTypeId. */
	private Integer entityTypeId;

	/** entryDate. */
	private Timestamp entryDate;

	/** fromDate. */
	private Date fromDate;

	/** ledgerReconciledInd. */
	private Integer ledgerReconciledInd;

	/** rejectReasonCode. */
	private String rejectReasonCode;

	/** slAdminInd. */
	private boolean slAdminInd;

	/** soId. */
	private String soId;

	/** toDate. */
	private Date toDate;

	/** transactionalType. */
	private String transactionalType;

	/** transactionId. */
	private Integer transactionId;

	/** transAmount. */
	private Double transAmount;

	/**
	 * getAccountId.
	 * 
	 * @return String
	 */
	public String getAccountId() {

		return accountId;
	}

	/**
	 * getAccountNumber.
	 * 
	 * @return String
	 */
	public String getAccountNumber() {

		return accountNumber;
	}

	/**
	 * getAchProcessId.
	 * 
	 * @return Integer
	 */
	public Integer getAchProcessId() {

		return achProcessId;
	}

	/**
	 * getAchReconciledInd.
	 * 
	 * @return Integer
	 */
	public Integer getAchReconciledInd() {

		return achReconciledInd;
	}

	/**
	 * getAchRejectId.
	 * 
	 * @return Integer
	 */
	public Integer getAchRejectId() {

		return achRejectId;
	}

	/**
	 * getAvailableBalance.
	 * 
	 * @return String
	 */
	public String getAvailableBalance() {

		return availableBalance;
	}

	/**
	 * getBankName.
	 * 
	 * @return String
	 */
	public String getBankName() {

		return bankName;
	}

	/**
	 * getCredDebInd.
	 * 
	 * @return Integer
	 */
	public Integer getCredDebInd() {

		return credDebInd;
	}

	/**
	 * getEntityId.
	 * 
	 * @return Integer
	 */
	public Integer getEntityId() {

		return entityId;
	}

	/**
	 * getEntityTypeId.
	 * 
	 * @return Integer
	 */
	public Integer getEntityTypeId() {

		return entityTypeId;
	}

	/**
	 * getEntryDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getEntryDate() {

		return entryDate;
	}

	/**
	 * getFromDate.
	 * 
	 * @return Date
	 */
	public Date getFromDate() {

		return fromDate;
	}

	/**
	 * getLedgerReconciledInd.
	 * 
	 * @return Integer
	 */
	public Integer getLedgerReconciledInd() {

		return ledgerReconciledInd;
	}

	/**
	 * getRejectReasonCode.
	 * 
	 * @return String
	 */
	public String getRejectReasonCode() {

		return rejectReasonCode;
	}

	/**
	 * getSoId.
	 * 
	 * @return String
	 */
	public String getSoId() {

		return soId;
	}

	/**
	 * getToDate.
	 * 
	 * @return Date
	 */
	public Date getToDate() {

		return toDate;
	}

	/**
	 * getTransactionalType.
	 * 
	 * @return String
	 */
	public String getTransactionalType() {

		return transactionalType;
	}

	/**
	 * getTransactionId.
	 * 
	 * @return Integer
	 */
	public Integer getTransactionId() {

		return transactionId;
	}

	/**
	 * getTransAmount.
	 * 
	 * @return Double
	 */
	public Double getTransAmount() {

		return transAmount;
	}

	/**
	 * isBuyerAdminInd.
	 * 
	 * @return boolean
	 */
	public boolean isBuyerAdminInd() {

		return buyerAdminInd;
	}

	/**
	 * isSlAdminInd.
	 * 
	 * @return boolean
	 */
	public boolean isSlAdminInd() {

		return slAdminInd;
	}

	/**
	 * setAccountId.
	 * 
	 * @param accountId 
	 * 
	 * @return void
	 */
	public void setAccountId(String accountId) {

		this.accountId = accountId;
	}

	/**
	 * setAccountNumber.
	 * 
	 * @param accountNumber 
	 * 
	 * @return void
	 */
	public void setAccountNumber(String accountNumber) {

		this.accountNumber = accountNumber;
	}

	/**
	 * setAchProcessId.
	 * 
	 * @param achProcessId 
	 * 
	 * @return void
	 */
	public void setAchProcessId(Integer achProcessId) {

		this.achProcessId = achProcessId;
	}

	/**
	 * setAchReconciledInd.
	 * 
	 * @param achReconciledInd 
	 * 
	 * @return void
	 */
	public void setAchReconciledInd(Integer achReconciledInd) {

		this.achReconciledInd = achReconciledInd;
	}

	/**
	 * setAchRejectId.
	 * 
	 * @param achRejectId 
	 * 
	 * @return void
	 */
	public void setAchRejectId(Integer achRejectId) {

		this.achRejectId = achRejectId;
	}

	/**
	 * setAvailableBalance.
	 * 
	 * @param availableBalance 
	 * 
	 * @return void
	 */
	public void setAvailableBalance(String availableBalance) {

		this.availableBalance = availableBalance;
	}

	/**
	 * setBankName.
	 * 
	 * @param bankName 
	 * 
	 * @return void
	 */
	public void setBankName(String bankName) {

		this.bankName = bankName;
	}

	/**
	 * setBuyerAdminInd.
	 * 
	 * @param buyerAdminInd 
	 * 
	 * @return void
	 */
	public void setBuyerAdminInd(boolean buyerAdminInd) {

		this.buyerAdminInd = buyerAdminInd;
	}

	/**
	 * setCredDebInd.
	 * 
	 * @param credDebInd 
	 * 
	 * @return void
	 */
	public void setCredDebInd(Integer credDebInd) {

		this.credDebInd = credDebInd;
	}

	/**
	 * setEntityId.
	 * 
	 * @param entityId 
	 * 
	 * @return void
	 */
	public void setEntityId(Integer entityId) {

		this.entityId = entityId;
	}

	/**
	 * setEntityTypeId.
	 * 
	 * @param entityTypeId 
	 * 
	 * @return void
	 */
	public void setEntityTypeId(Integer entityTypeId) {

		this.entityTypeId = entityTypeId;
	}

	/**
	 * setEntryDate.
	 * 
	 * @param entryDate 
	 * 
	 * @return void
	 */
	public void setEntryDate(Timestamp entryDate) {

		this.entryDate = entryDate;
	}

	/**
	 * setFromDate.
	 * 
	 * @param fromDate 
	 * 
	 * @return void
	 */
	public void setFromDate(Date fromDate) {

		this.fromDate = fromDate;
	}

	/**
	 * setLedgerReconciledInd.
	 * 
	 * @param ledgerReconciledInd 
	 * 
	 * @return void
	 */
	public void setLedgerReconciledInd(Integer ledgerReconciledInd) {

		this.ledgerReconciledInd = ledgerReconciledInd;
	}

	/**
	 * setRejectReasonCode.
	 * 
	 * @param rejectReasonCode 
	 * 
	 * @return void
	 */
	public void setRejectReasonCode(String rejectReasonCode) {

		this.rejectReasonCode = rejectReasonCode;
	}

	/**
	 * setSlAdminInd.
	 * 
	 * @param slAdminInd 
	 * 
	 * @return void
	 */
	public void setSlAdminInd(boolean slAdminInd) {

		this.slAdminInd = slAdminInd;
	}

	/**
	 * setSoId.
	 * 
	 * @param soId 
	 * 
	 * @return void
	 */
	public void setSoId(String soId) {

		this.soId = soId;
	}

	/**
	 * setToDate.
	 * 
	 * @param toDate 
	 * 
	 * @return void
	 */
	public void setToDate(Date toDate) {

		this.toDate = toDate;
	}

	/**
	 * setTransactionalType.
	 * 
	 * @param transactionalType 
	 * 
	 * @return void
	 */
	public void setTransactionalType(String transactionalType) {

		this.transactionalType = transactionalType;
	}

	/**
	 * setTransactionId.
	 * 
	 * @param transactionId 
	 * 
	 * @return void
	 */
	public void setTransactionId(Integer transactionId) {

		this.transactionId = transactionId;
	}

	/**
	 * setTransAmount.
	 * 
	 * @param transAmount 
	 * 
	 * @return void
	 */
	public void setTransAmount(Double transAmount) {

		this.transAmount = transAmount;
	}
}
