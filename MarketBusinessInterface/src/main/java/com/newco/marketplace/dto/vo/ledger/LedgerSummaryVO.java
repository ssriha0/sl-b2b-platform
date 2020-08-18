package com.newco.marketplace.dto.vo.ledger;

import com.sears.os.vo.SerializableBaseVO;

public class LedgerSummaryVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3842931576184975077L;
	private Long ledgerEntryId;
	private double transactionAmount;
	private int transactionTypeId;
	private int ledgerEntityTypeId;
	private int entryTypeId;
	private int transactionId;
	private Long ledgerEntityId;
	
	public Long getLedgerEntryId() {
		return ledgerEntryId;
	}
	public void setLedgerEntryId(Long ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
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
	public int getLedgerEntityTypeId() {
		return ledgerEntityTypeId;
	}
	public void setLedgerEntityTypeId(int ledgerEntityTypeId) {
		this.ledgerEntityTypeId = ledgerEntityTypeId;
	}
	public int getEntryTypeId() {
		return entryTypeId;
	}
	public void setEntryTypeId(int entryTypeId) {
		this.entryTypeId = entryTypeId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public Long getLedgerEntityId() {
		return ledgerEntityId;
	}
	public void setLedgerEntityId(Long ledgerEntityId) {
		this.ledgerEntityId = ledgerEntityId;
	}
}
