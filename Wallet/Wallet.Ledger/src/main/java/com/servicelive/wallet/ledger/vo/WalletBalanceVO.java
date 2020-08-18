package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;
import java.sql.Date;

public class WalletBalanceVO implements Serializable {

	private static final long serialVersionUID = -5849939924378366407L;
	//in
	private Date startDate;
	private Date endDate;
	private Integer entityType;
	private Integer entityId;
	//out
	private Integer ledgerEntryId;
	private Double transAmount;
	private Double validatedAmount;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEntityType() {
		return entityType;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	
	public Integer getLedgerEntryId() {
		return ledgerEntryId;
	}

	
	public void setLedgerEntryId(Integer ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

	
	public Double getTransAmount() {
		return transAmount;
	}

	
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	
	public Double getValidatedAmount() {
		return validatedAmount;
	}

	
	public void setValidatedAmount(Double validatedAmount) {
		this.validatedAmount = validatedAmount;
	}
}
