package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * Class TransactionEntryVO.
 */
public class TransactionEntryVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -6729272647297236103L;

	/**
	 * getSerialversionuid.
	 * 
	 * @return long
	 */
	public static long getSerialversionuid() {

		return serialVersionUID;
	}

	/** accountNumber. */
	private Long accountNumber;

	/** CCInd. */
	private Boolean CCInd;

	/** createdDate. */
	private Date createdDate;

	/** entryTypeId. */
	private long entryTypeId;

	/** isCredit. */
	private boolean isCredit = false;

	/** ledgerEntityId. */
	private long ledgerEntityId;

	/** ledgerEntityTypeId. */
	private long ledgerEntityTypeId;

	/** ledgerEntryId. */
	private long ledgerEntryId;

	/** ledgerEntryRuleId. */
	private long ledgerEntryRuleId;

	/** modifiedBy. */
	private String modifiedBy;

	/** modifiedDate. */
	private Date modifiedDate;

	/** originatingBuyerId. */
	private Long originatingBuyerId;

	/** reconsiledInd. */
	private Integer reconsiledInd;

	/** taccountNo. */
	private long taccountNo;

	/** transactionAmount. */
	private double transactionAmount;

	/** transactionId. */
	private long transactionId;

	/** transactionTypeId. */
	private long transactionTypeId;

	/**
	 * getAccountNumber.
	 * 
	 * @return Long
	 */
	public Long getAccountNumber() {

		return accountNumber;
	}

	/**
	 * getCCInd.
	 * 
	 * @return Boolean
	 */
	public Boolean getCCInd() {

		return CCInd;
	}

	/**
	 * getCreatedDate.
	 * 
	 * @return Date
	 */
	public Date getCreatedDate() {

		return createdDate;
	}

	/**
	 * getEntryTypeId.
	 * 
	 * @return long
	 */
	public long getEntryTypeId() {

		return entryTypeId;
	}

	/**
	 * getLedgerEntityId.
	 * 
	 * @return long
	 */
	public long getLedgerEntityId() {

		return ledgerEntityId;
	}

	/**
	 * getLedgerEntityTypeId.
	 * 
	 * @return long
	 */
	public long getLedgerEntityTypeId() {

		return ledgerEntityTypeId;
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
	 * getLedgerEntryRuleId.
	 * 
	 * @return long
	 */
	public long getLedgerEntryRuleId() {

		return ledgerEntryRuleId;
	}

	/**
	 * getModifiedBy.
	 * 
	 * @return String
	 */
	public String getModifiedBy() {

		return modifiedBy;
	}

	/**
	 * getModifiedDate.
	 * 
	 * @return Date
	 */
	public Date getModifiedDate() {

		return modifiedDate;
	}

	/**
	 * getOriginatingBuyerId.
	 * 
	 * @return Long
	 */
	public Long getOriginatingBuyerId() {

		return originatingBuyerId;
	}

	/**
	 * getReconsiledInd.
	 * 
	 * @return Integer
	 */
	public Integer getReconsiledInd() {

		return reconsiledInd;
	}

	/**
	 * getTaccountNo.
	 * 
	 * @return long
	 */
	public long getTaccountNo() {

		return taccountNo;
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
	 * getTransactionId.
	 * 
	 * @return long
	 */
	public long getTransactionId() {

		return transactionId;
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
	 * isCredit.
	 * 
	 * @return boolean
	 */
	public boolean isCredit() {

		return isCredit;
	}

	/**
	 * setAccountNumber.
	 * 
	 * @param accountNumber 
	 * 
	 * @return void
	 */
	public void setAccountNumber(Long accountNumber) {

		this.accountNumber = accountNumber;
	}

	/**
	 * setCCInd.
	 * 
	 * @param cCInd 
	 * 
	 * @return void
	 */
	public void setCCInd(Boolean cCInd) {

		CCInd = cCInd;
	}

	/**
	 * setCreatedDate.
	 * 
	 * @param createdDate 
	 * 
	 * @return void
	 */
	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * setCredit.
	 * 
	 * @param isCredit 
	 * 
	 * @return void
	 */
	public void setCredit(boolean isCredit) {

		this.isCredit = isCredit;
	}

	/**
	 * setEntryTypeId.
	 * 
	 * @param entryTypeId 
	 * 
	 * @return void
	 */
	public void setEntryTypeId(long entryTypeId) {

		this.entryTypeId = entryTypeId;
	}

	/**
	 * setLedgerEntityId.
	 * 
	 * @param ledgerEntityId 
	 * 
	 * @return void
	 */
	public void setLedgerEntityId(long ledgerEntityId) {

		this.ledgerEntityId = ledgerEntityId;
	}

	/**
	 * setLedgerEntityTypeId.
	 * 
	 * @param ledgerEntityTypeId 
	 * 
	 * @return void
	 */
	public void setLedgerEntityTypeId(long ledgerEntityTypeId) {

		this.ledgerEntityTypeId = ledgerEntityTypeId;
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
	 * setLedgerEntryRuleId.
	 * 
	 * @param ledgerEntryRuleId 
	 * 
	 * @return void
	 */
	public void setLedgerEntryRuleId(long ledgerEntryRuleId) {

		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}

	/**
	 * setModifiedBy.
	 * 
	 * @param modifiedBy 
	 * 
	 * @return void
	 */
	public void setModifiedBy(String modifiedBy) {

		this.modifiedBy = modifiedBy;
	}

	/**
	 * setModifiedDate.
	 * 
	 * @param modifiedDate 
	 * 
	 * @return void
	 */
	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/**
	 * setOriginatingBuyerId.
	 * 
	 * @param originatingBuyerId 
	 * 
	 * @return void
	 */
	public void setOriginatingBuyerId(Long originatingBuyerId) {

		this.originatingBuyerId = originatingBuyerId;
	}

	/**
	 * setReconsiledInd.
	 * 
	 * @param reconsiledInd 
	 * 
	 * @return void
	 */
	public void setReconsiledInd(Integer reconsiledInd) {

		this.reconsiledInd = reconsiledInd;
	}

	/**
	 * setTaccountNo.
	 * 
	 * @param taccountNo 
	 * 
	 * @return void
	 */
	public void setTaccountNo(long taccountNo) {

		this.taccountNo = taccountNo;
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
	 * setTransactionId.
	 * 
	 * @param transactionId 
	 * 
	 * @return void
	 */
	public void setTransactionId(long transactionId) {

		this.transactionId = transactionId;
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

	//   
	//    
	// public boolean isCCInd() {
	// return CCInd;
	// }
	// public void setCCInd(boolean CCInd) {
	// this.CCInd = CCInd;
	// }
	// public Integer getLedgerEntityId() {
	// return ledgerEntityId;
	// }
	// public void setLedgerEntityId(Integer ledgerEntityId) {
	// this.ledgerEntityId = ledgerEntityId;
	// }
	// public Long getLedgerEntityTypeId() {
	// return ledgerEntityTypeId;
	// }
	// public void setLedgerEntityTypeId(Long ledgerEntityTypeId) {
	// this.ledgerEntityTypeId = ledgerEntityTypeId;
	// }
	//	
	// public Long getTransactionId() {
	// return transactionId;
	// }
	// public void setTransactionId(Long transactionId) {
	// this.transactionId = transactionId;
	// }
	// public Long getTransactionTypeId() {
	// return transactionTypeId;
	// }
	// public void setTransactionTypeId(Long transactionTypeId) {
	// this.transactionTypeId = transactionTypeId;
	// }
	// public int getEntryTypeId() {
	// return entryTypeId;
	// }
	// public void setEntryTypeId(int entryTypeId) {
	// this.entryTypeId = entryTypeId;
	// }
	// public boolean isCredit() {
	// return isCredit;
	// }
	// public void setIsACreditTransaction(boolean isCredit) {
	// this.isCredit = isCredit;
	// }
	// public Long getLedgerEntryId() {
	// return ledgerEntryId;
	// }
	// public void setLedgerEntryId(Long ledgerEntryId) {
	// this.ledgerEntryId = ledgerEntryId;
	// }
	// public Date getCreatedDate() {
	// return createdDate;
	// }
	// public void setCreatedDate(Date createdDate) {
	// this.createdDate = createdDate;
	// }
	// public Date getModifiedDate() {
	// return modifiedDate;
	// }
	// public void setModifiedDate(Date modifiedDate) {
	// this.modifiedDate = modifiedDate;
	// }
	// public String getModifiedBy() {
	// return modifiedBy;
	// }
	// public void setModifiedBy(String modifiedBy) {
	// this.modifiedBy = modifiedBy;
	// }
	// public Integer getTaccountNo() {
	// return taccountNo;
	// }
	// public void setTaccountNo(Integer taccountNo) {
	// this.taccountNo = taccountNo;
	// }
	// public Double getTransactionAmount() {
	// return transactionAmount;
	// }
	// public void setTransactionAmount(Double transactionAmount) {
	// this.transactionAmount = transactionAmount;
	// }
	// public Long getAccountNumber() {
	// return accountNumber;
	// }
	// public void setAccountNumber(Long accountNumber) {
	// this.accountNumber = accountNumber;
	// }
	// public Long getLedgerEntryRuleId() {
	// return ledgerEntryRuleId;
	// }
	// public void setLedgerEntryRuleId(Long ledgerEntryRuleId) {
	// this.ledgerEntryRuleId = ledgerEntryRuleId;
	// }
	// public void setCredit(boolean isCredit) {
	// this.isCredit = isCredit;
	// }
	// public Long getReconsiledInd() {
	// return reconsiledInd;
	// }
	// public void setReconsiledInd(Long reconsiledInd) {
	// this.reconsiledInd = reconsiledInd;
	// }
	// public Long getOriginatingBuyerId() {
	// return originatingBuyerId;
	// }
	// public void setOriginatingBuyerId(Long originatingBuyerId) {
	// this.originatingBuyerId = originatingBuyerId;
	// }

}// end class SecretQuectionVO