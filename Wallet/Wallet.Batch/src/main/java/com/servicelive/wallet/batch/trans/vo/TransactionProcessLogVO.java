package com.servicelive.wallet.batch.trans.vo;

import java.io.Serializable;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionProcessLogVO.
 */
public class TransactionProcessLogVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5856221877708185732L;

	/** The created date. */
	private Timestamp createdDate;

	/** The generated file name. */
	private String generatedFileName;

	/** The generated server. */
	private String generatedServer;

	/** The modified date. */
	private Timestamp modifiedDate;

	/** The trans deposit total. */
	private double transDepositTotal;

	/** The trans record count. */
	private int transRecordCount;

	/** The trans refund total. */
	private double transRefundTotal;

	/**
	 * Gets the created date.
	 * 
	 * @return the created date
	 */
	public Timestamp getCreatedDate() {

		return createdDate;
	}

	/**
	 * Gets the generated file name.
	 * 
	 * @return the generated file name
	 */
	public String getGeneratedFileName() {

		return generatedFileName;
	}

	/**
	 * Gets the generated server.
	 * 
	 * @return the generated server
	 */
	public String getGeneratedServer() {

		return generatedServer;
	}

	/**
	 * Gets the modified date.
	 * 
	 * @return the modified date
	 */
	public Timestamp getModifiedDate() {

		return modifiedDate;
	}

	/**
	 * Gets the trans deposit total.
	 * 
	 * @return the trans deposit total
	 */
	public double getTransDepositTotal() {

		return transDepositTotal;
	}

	/**
	 * Gets the trans record count.
	 * 
	 * @return the trans record count
	 */
	public int getTransRecordCount() {

		return transRecordCount;
	}

	/**
	 * Gets the trans refund total.
	 * 
	 * @return the trans refund total
	 */
	public double getTransRefundTotal() {

		return transRefundTotal;
	}

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Timestamp createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * Sets the generated file name.
	 * 
	 * @param generatedFileName the new generated file name
	 */
	public void setGeneratedFileName(String generatedFileName) {

		this.generatedFileName = generatedFileName;
	}

	/**
	 * Sets the generated server.
	 * 
	 * @param generatedServer the new generated server
	 */
	public void setGeneratedServer(String generatedServer) {

		this.generatedServer = generatedServer;
	}

	/**
	 * Sets the modified date.
	 * 
	 * @param modified_date the new modified date
	 */
	public void setModifiedDate(Timestamp modified_date) {

		this.modifiedDate = modified_date;
	}

	/**
	 * Sets the trans deposit total.
	 * 
	 * @param transDepositTotal the new trans deposit total
	 */
	public void setTransDepositTotal(double transDepositTotal) {

		this.transDepositTotal = transDepositTotal;
	}

	/**
	 * Sets the trans record count.
	 * 
	 * @param transRecordCount the new trans record count
	 */
	public void setTransRecordCount(int transRecordCount) {

		this.transRecordCount = transRecordCount;
	}

	/**
	 * Sets the trans refund total.
	 * 
	 * @param transRefundTotal the new trans refund total
	 */
	public void setTransRefundTotal(double transRefundTotal) {

		this.transRefundTotal = transRefundTotal;
	}

}
