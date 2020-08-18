package com.servicelive.wallet.batch.ach.vo;

// TODO: Auto-generated Javadoc
/**
 * EntryDetailRecordVO.java - This class represents the entry detail record as specified by NACHA
 * 
 * @author Siva
 * @version 1.0
 */
public class EntryDetailRecordVO extends NachaGenericRecordVO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5299455215743371452L;

	/** The entry detail number. */
	private String entryDetailNumber;

	/** The immediate destination. */
	private String immediateDestination;

	/** The is addenda available. */
	private boolean isAddendaAvailable;

	/** The transaction code. */
	private String transactionCode;

	/** The transaction type. */
	private String transactionType; // holds "CREDIT" ( for CREDIT ) or "DEBIT" (for DEBIT)

	/**
	 * Gets the entry detail number.
	 * 
	 * @return the entry detail number
	 */
	public String getEntryDetailNumber() {

		return entryDetailNumber;
	}

	/**
	 * Gets the immediate destination.
	 * 
	 * @return the immediate destination
	 */
	public String getImmediateDestination() {

		return immediateDestination;
	}

	/**
	 * Gets the transaction code.
	 * 
	 * @return the transaction code
	 */
	public String getTransactionCode() {

		return transactionCode;
	}

	/**
	 * Gets the transaction type.
	 * 
	 * @return the transaction type
	 */
	public String getTransactionType() {

		return transactionType;
	}

	/**
	 * Checks if is addenda available.
	 * 
	 * @return true, if is addenda available
	 */
	public boolean isAddendaAvailable() {

		return isAddendaAvailable;
	}

	/**
	 * Sets the addenda available.
	 * 
	 * @param isAddendaAvailable the new addenda available
	 */
	public void setAddendaAvailable(boolean isAddendaAvailable) {

		this.isAddendaAvailable = isAddendaAvailable;
	}

	/**
	 * Sets the entry detail number.
	 * 
	 * @param entryDetailNumber the new entry detail number
	 */
	public void setEntryDetailNumber(String entryDetailNumber) {

		this.entryDetailNumber = entryDetailNumber;
	}

	/**
	 * Sets the immediate destination.
	 * 
	 * @param immediateDestination the new immediate destination
	 */
	public void setImmediateDestination(String immediateDestination) {

		this.immediateDestination = immediateDestination;
	}

	/**
	 * Sets the transaction code.
	 * 
	 * @param transactionCode the new transaction code
	 */
	public void setTransactionCode(String transactionCode) {

		this.transactionCode = transactionCode;
	}

	/**
	 * Sets the transaction type.
	 * 
	 * @param transactionType the new transaction type
	 */
	public void setTransactionType(String transactionType) {

		this.transactionType = transactionType;
	}

}
