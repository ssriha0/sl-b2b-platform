package com.newco.marketplace.dto.vo.ledger;

import java.sql.Date;
import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class AccountHistoryVO extends SerializableBaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8574094832290153146L;
	private Integer transactionId;
	private String soId;
	private Timestamp entryDate;
	private String transactionalType;
	private Double transAmount;
	private Integer achReconciledInd;
	private Integer achProcessId;
	private Integer achRejectId;
	private String rejectReasonCode;
	private Integer ledgerReconciledInd;
	private Integer credDebInd;
	private String entityType;
	private Integer entityId;
	private Integer entityTypeId;

	
	private String availableBalance;
	
	private Date fromDate;
	private Date toDate;
	
	private String accountId;
	private String accountNumber;
	private String bankName;
	private boolean slAdminInd;
	private boolean buyerAdminInd;
	private Integer returnCountLimit;
	private int startIndex;
	private int numberOfRecords;
	private String dateRangeCheck;
	private Timestamp modifiedDate;
	
	
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDateRangeCheck() {
		return dateRangeCheck;
	}

	public void setDateRangeCheck(String dateRangeCheck) {
		this.dateRangeCheck = dateRangeCheck;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getReturnCountLimit() {
		return returnCountLimit;
	}

	public void setReturnCountLimit(Integer returnCountLimit) {
		this.returnCountLimit = returnCountLimit;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public Integer getCredDebInd() {
		return credDebInd;
	}
	public void setCredDebInd(Integer credDebInd) {
		this.credDebInd = credDebInd;
	}

	public Timestamp getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Timestamp entryDate) {
		this.entryDate = entryDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransactionalType() {
		return transactionalType;
	}
	public void setTransactionalType(String transactionalType) {
		this.transactionalType = transactionalType;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Integer getAchReconciledInd() {
		return achReconciledInd;
	}
	public void setAchReconciledInd(Integer achReconciledInd) {
		this.achReconciledInd = achReconciledInd;
	}
	public Integer getAchProcessId() {
		return achProcessId;
	}
	public void setAchProcessId(Integer achProcessId) {
		this.achProcessId = achProcessId;
	}
	public Integer getAchRejectId() {
		return achRejectId;
	}
	public void setAchRejectId(Integer achRejectId) {
		this.achRejectId = achRejectId;
	}
	public Integer getLedgerReconciledInd() {
		return ledgerReconciledInd;
	}
	public void setLedgerReconciledInd(Integer ledgerReconciledInd) {
		this.ledgerReconciledInd = ledgerReconciledInd;
	}
	public String getRejectReasonCode() {
		return rejectReasonCode;
	}
	public void setRejectReasonCode(String rejectReasonCode) {
		this.rejectReasonCode = rejectReasonCode;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public void setSlAdminInd(boolean slAdminInd) {
		this.slAdminInd = slAdminInd;
	}
	public boolean isSlAdminInd() {
		return slAdminInd;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public boolean isBuyerAdminInd() {
		return buyerAdminInd;
	}
	public void setBuyerAdminInd(boolean buyerAdminInd) {
		this.buyerAdminInd = buyerAdminInd;
	}
}
