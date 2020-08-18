package com.newco.marketplace.dto.vo.ledger;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class BAIFileVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7493608466221405660L;

	private long ledgerEntryId;
	
	private boolean reconsiledInd;
	
	private Date modifiedDate;
	
	private Date reconsidedDate;

	public long getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(long ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

	public boolean isReconsiledInd() {
		return reconsiledInd;
	}

	public void setReconsiledInd(boolean reconsiledInd) {
		this.reconsiledInd = reconsiledInd;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setReconsidedDate(Date reconsidedDate) {
		this.reconsidedDate = reconsidedDate;
	}

	public Date getReconsidedDate() {
		return reconsidedDate;
	}

}
