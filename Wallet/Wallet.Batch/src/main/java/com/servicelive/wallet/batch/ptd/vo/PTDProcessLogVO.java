package com.servicelive.wallet.batch.ptd.vo;

import java.io.Serializable;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDProcessLogVO.
 */
public class PTDProcessLogVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8825779670607414092L;

	/** The prev sequence number. */
	private int prevSequenceNumber;

	/** The process run date. */
	private Timestamp processRunDate;

	/** The ptd log id. */
	private int ptdLogId;

	/** The ptd process status. */
	private String ptdProcessStatus;

	/** The ptd reconsiled ind. */
	private int ptdReconsiledInd;

	/** The ptd sequence number. */
	private int ptdSequenceNumber;

	/** The ptd transactions received. */
	private int ptdTransactionsReceived;

	/** The ptd transactions updated. */
	private int ptdTransactionsUpdated;

	/**
	 * Gets the prev sequence number.
	 * 
	 * @return the prev sequence number
	 */
	public int getPrevSequenceNumber() {

		return prevSequenceNumber;
	}

	/**
	 * Gets the process run date.
	 * 
	 * @return the process run date
	 */
	public Timestamp getProcessRunDate() {

		return processRunDate;
	}

	/**
	 * Gets the ptd log id.
	 * 
	 * @return the ptd log id
	 */
	public int getPtdLogId() {

		return ptdLogId;
	}

	/**
	 * Gets the ptd process status.
	 * 
	 * @return the ptd process status
	 */
	public String getPtdProcessStatus() {

		return ptdProcessStatus;
	}

	/**
	 * Gets the ptd reconsiled ind.
	 * 
	 * @return the ptd reconsiled ind
	 */
	public int getPtdReconsiledInd() {

		return ptdReconsiledInd;
	}

	/**
	 * Gets the ptd sequence number.
	 * 
	 * @return the ptd sequence number
	 */
	public int getPtdSequenceNumber() {

		return ptdSequenceNumber;
	}

	/**
	 * Gets the ptd transactions received.
	 * 
	 * @return the ptd transactions received
	 */
	public int getPtdTransactionsReceived() {

		return ptdTransactionsReceived;
	}

	/**
	 * Gets the ptd transactions updated.
	 * 
	 * @return the ptd transactions updated
	 */
	public int getPtdTransactionsUpdated() {

		return ptdTransactionsUpdated;
	}

	/**
	 * Sets the prev sequence number.
	 * 
	 * @param prevSequenceNumber the new prev sequence number
	 */
	public void setPrevSequenceNumber(int prevSequenceNumber) {

		this.prevSequenceNumber = prevSequenceNumber;
	}

	/**
	 * Sets the process run date.
	 * 
	 * @param processRunDate the new process run date
	 */
	public void setProcessRunDate(Timestamp processRunDate) {

		this.processRunDate = processRunDate;
	}

	/**
	 * Sets the ptd log id.
	 * 
	 * @param ptdLogId the new ptd log id
	 */
	public void setPtdLogId(int ptdLogId) {

		this.ptdLogId = ptdLogId;
	}

	/**
	 * Sets the ptd process status.
	 * 
	 * @param ptdProcessStatus the new ptd process status
	 */
	public void setPtdProcessStatus(String ptdProcessStatus) {

		this.ptdProcessStatus = ptdProcessStatus;
	}

	/**
	 * Sets the ptd reconsiled ind.
	 * 
	 * @param ptdReconsiledInd the new ptd reconsiled ind
	 */
	public void setPtdReconsiledInd(int ptdReconsiledInd) {

		this.ptdReconsiledInd = ptdReconsiledInd;
	}

	/**
	 * Sets the ptd sequence number.
	 * 
	 * @param ptdSequenceNumber the new ptd sequence number
	 */
	public void setPtdSequenceNumber(int ptdSequenceNumber) {

		this.ptdSequenceNumber = ptdSequenceNumber;
	}

	/**
	 * Sets the ptd transactions received.
	 * 
	 * @param ptdTransactionsReceived the new ptd transactions received
	 */
	public void setPtdTransactionsReceived(int ptdTransactionsReceived) {

		this.ptdTransactionsReceived = ptdTransactionsReceived;
	}

	/**
	 * Sets the ptd transactions updated.
	 * 
	 * @param ptdTransactionsUpdated the new ptd transactions updated
	 */
	public void setPtdTransactionsUpdated(int ptdTransactionsUpdated) {

		this.ptdTransactionsUpdated = ptdTransactionsUpdated;
	}
}
