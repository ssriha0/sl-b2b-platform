package com.newco.marketplace.dto.vo.ledger;

import java.sql.Timestamp;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class LedgerIdsVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9075169457644960919L;
	private List<Integer> transactionTypeIds;
	private List<Integer> ledgerEntryIds;
	
	private Timestamp glProcessedDate;
	
	private int glProcessed;
	
	public List<Integer> getTransactionTypeIds() {
		return transactionTypeIds;
	}

	public void setTransactionTypeIds(List<Integer> transactionTypeIds) {
		this.transactionTypeIds = transactionTypeIds;
	}

	public List<Integer> getLedgerEntryIds() {
		return ledgerEntryIds;
	}

	public void setLedgerEntryIds(List<Integer> ledgerEntryIds) {
		this.ledgerEntryIds = ledgerEntryIds;
	}

	public Timestamp getGlProcessedDate() {
		return glProcessedDate;
	}

	public void setGlProcessedDate(Timestamp glProcessedDate) {
		this.glProcessedDate = glProcessedDate;
	}

	public int getGlProcessed() {
		return glProcessed;
	}

	public void setGlProcessed(int glProcessed) {
		this.glProcessed = glProcessed;
	}
	
	

}
