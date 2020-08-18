package com.newco.marketplace.dto.vo.ach;

import java.sql.Timestamp;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author swamy patsa
 *
 */
public class AchProcessQueueEntryVO extends SerializableBaseVO{

	private static final long serialVersionUID = 290354307939040938L;

	private long achProcessId;
	private int entityTypeId;
	private int entityId;
	private long accountId;	
	private int transactionTypeId;
	private double transactionAmount;
	private int processStatusId;
	private Date processStatusChgDate;
	private Date createdDate;
	private long ledgerEntryId;
	private Long ledgerTransactionId;
	private int achBatchAssocId;
	private int transactionEntryTypeId;
	private Integer rejectReasonId;
	private String rejectCode;
	private boolean rejectIndicator;
	private Integer reconciledIndicator;
	private int tmpProcessStatusId;
	private Timestamp modifiedDate;
	
	public Integer getReconciledIndicator() {
		return reconciledIndicator;
	}

	public void setReconciledIndicator(int reconciledIndicator) {
		this.reconciledIndicator = reconciledIndicator;
	}

	public Integer getRejectReasonId() {
		return rejectReasonId;
	}

	public void setRejectReasonId(int rejectReasonId) {
		this.rejectReasonId = rejectReasonId;
	}

	public long getAchProcessId() {
		return achProcessId;
	}

	public void setAchProcessId(long achProcessId) {
		this.achProcessId = achProcessId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(int entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public int getProcessStatusId() {
		return processStatusId;
	}

	public void setProcessStatusId(int processStatusId) {
		this.processStatusId = processStatusId;
	}

	public double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	

	public int getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(int transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Date getProcessStatusChgDate() {
		return processStatusChgDate;
	}

	public void setProcessStatusChgDate(Date processStatusChgDate) {
		this.processStatusChgDate = processStatusChgDate;
	}

	public long getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(long ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

	public int getAchBatchAssocId() {
		return achBatchAssocId;
	}

	public void setAchBatchAssocId(int achBatchAssocId) {
		this.achBatchAssocId = achBatchAssocId;
	}

	public int getTransactionEntryTypeId() {
		return transactionEntryTypeId;
	}

	public void setTransactionEntryTypeId(int transactionEntryTypeId) {
		this.transactionEntryTypeId = transactionEntryTypeId;
	}

	public int getTmpProcessStatusId() {
		return tmpProcessStatusId;
	}

	public void setTmpProcessStatusId(int tmpProcessStatusId) {
		this.tmpProcessStatusId = tmpProcessStatusId;
	}

	public String getRejectCode() {
		return rejectCode;
	}

	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public boolean isRejectIndicator() {
		return rejectIndicator;
	}

	public void setRejectIndicator(boolean rejectIndicator) {
		this.rejectIndicator = rejectIndicator;
	}

	public Long getLedgerTransactionId() {
		return ledgerTransactionId;
	}

	public void setLedgerTransactionId(Long ledgerTransactionId) {
		this.ledgerTransactionId = ledgerTransactionId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	
	
	
	
	

}
