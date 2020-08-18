package com.servicelive.wallet.batch.ach.vo;

// TODO: Auto-generated Javadoc
/**
 * BatchControlRecordVO.java - This class is a template class for Batch Control Record as specified by NACHA
 * 
 * @author Siva
 * @version 1.0
 */
public class BatchControlRecordVO extends NachaGenericRecordVO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2610603337293181770L;

	/** The entry hash. */
	private long entryHash;

	/** The total credit amount. */
	private long totalCreditAmount;

	/** The total debit amount. */
	private long totalDebitAmount;

	/**
	 * Gets the entry hash.
	 * 
	 * @return the entry hash
	 */
	public long getEntryHash() {

		return entryHash;
	}

	/**
	 * Gets the total credit amount.
	 * 
	 * @return the total credit amount
	 */
	public long getTotalCreditAmount() {

		return totalCreditAmount;
	}

	/**
	 * Gets the total debit amount.
	 * 
	 * @return the total debit amount
	 */
	public long getTotalDebitAmount() {

		return totalDebitAmount;
	}

	/**
	 * Sets the entry hash.
	 * 
	 * @param entryHash the new entry hash
	 */
	public void setEntryHash(long entryHash) {

		this.entryHash = entryHash;
	}

	/**
	 * Sets the total credit amount.
	 * 
	 * @param totalCreditAmount the new total credit amount
	 */
	public void setTotalCreditAmount(long totalCreditAmount) {

		this.totalCreditAmount = totalCreditAmount;
	}

	/**
	 * Sets the total debit amount.
	 * 
	 * @param totalDebitAmount the new total debit amount
	 */
	public void setTotalDebitAmount(long totalDebitAmount) {

		this.totalDebitAmount = totalDebitAmount;
	}

}
