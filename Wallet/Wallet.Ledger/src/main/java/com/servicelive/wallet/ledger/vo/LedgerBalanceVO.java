package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;

public class LedgerBalanceVO  implements Serializable {
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
	
	/** ledgerEntityId */
	private Long ledgerEntityId;
	/** ledgerEntityTypeId */
	private Integer ledgerEntityTypeId;
	/** ledgerEntryId */
	private Long ledgerEntryId;
	/** balanceAffectedInd */
	private String balanceAffectedInd;
	/** ledgerBalanceId */
	private Long ledgerBalanceId;
	/** availableBalance */
	private Double availableBalance;
	/** projectBalance */
	private Double projectBalance;
	/** reconsiledInd */
	private Integer reconsiledInd;
	public Integer isReconsiledInd() {
		return reconsiledInd;
	}
	/** affectsBalanceInd. */
	private Integer affectsBalanceInd;

	public Integer getAffectsBalanceInd() {
		return affectsBalanceInd;
	}

	public void setAffectsBalanceInd(Integer affectsBalanceInd) {
		this.affectsBalanceInd = affectsBalanceInd;
	}

	public void setReconsiledInd(Integer reconsiledInd) {
		this.reconsiledInd = reconsiledInd;
	}

	public Long getLedgerEntityId() {
		return ledgerEntityId;
	}

	public void setLedgerEntityId(Long ledgerEntityId) {
		this.ledgerEntityId = ledgerEntityId;
	}

	public Integer getLedgerEntityTypeId() {
		return ledgerEntityTypeId;
	}

	public void setLedgerEntityTypeId(Integer ledgerEntityTypeId) {
		this.ledgerEntityTypeId = ledgerEntityTypeId;
	}

	public String getBalanceAffectedInd() {
		return balanceAffectedInd;
	}

	public void setBalanceAffectedInd(String balanceAffectedInd) {
		this.balanceAffectedInd = balanceAffectedInd;
	}

	public Long getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(Long ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

	public Long getLedgerBalanceId() {
		return ledgerBalanceId;
	}

	public void setLedgerBalanceId(Long ledgerBalanceId) {
		this.ledgerBalanceId = ledgerBalanceId;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getProjectBalance() {
		return projectBalance;
	}

	public void setProjectBalance(Double projectBalance) {
		this.projectBalance = projectBalance;
	}

}
