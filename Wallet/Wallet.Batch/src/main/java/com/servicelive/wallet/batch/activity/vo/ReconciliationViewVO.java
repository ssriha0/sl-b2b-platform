package com.servicelive.wallet.batch.activity.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ReconciliationViewVO.
 */
public class ReconciliationViewVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4695198723642497623L;

	/** The amount. */
	private double amount;

	/** The num of records. */
	private int numOfRecords;

	/** The transaction type. */
	private String transactionType;

	/**
	 * Gets the amount.
	 * 
	 * @return the amount
	 */
	public double getAmount() {

		return amount;
	}

	/**
	 * Gets the num of records.
	 * 
	 * @return the num of records
	 */
	public int getNumOfRecords() {

		return numOfRecords;
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
	 * Sets the amount.
	 * 
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {

		this.amount = amount;
	}

	/**
	 * Sets the num of records.
	 * 
	 * @param numOfRecords the new num of records
	 */
	public void setNumOfRecords(int numOfRecords) {

		this.numOfRecords = numOfRecords;
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
