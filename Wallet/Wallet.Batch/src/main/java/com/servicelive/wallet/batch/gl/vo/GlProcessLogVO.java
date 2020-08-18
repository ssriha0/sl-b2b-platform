package com.servicelive.wallet.batch.gl.vo;

/**
 * @author swamy patsa.
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class GlProcessLogVO.
 */
public class GlProcessLogVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8554798475137419492L;

	/** The file name. */
	private String fileName;

	/** The from date. */
	private Date fromDate;

	/** The initiated manually. */
	private int initiatedManually;

	/** The process date. */
	private Timestamp processDate;

	/** The process succesfull. */
	private int processSuccesfull;

	/** The to date. */
	private Date toDate;

	/**
	 * Gets the file name.
	 * 
	 * @return the file name
	 */
	public String getFileName() {

		return fileName;
	}

	/**
	 * Gets the from date.
	 * 
	 * @return the from date
	 */
	public Date getFromDate() {

		return fromDate;
	}

	/**
	 * Gets the initiated manually.
	 * 
	 * @return the initiated manually
	 */
	public int getInitiatedManually() {

		return initiatedManually;
	}

	/**
	 * Gets the process date.
	 * 
	 * @return the process date
	 */
	public Timestamp getProcessDate() {

		return processDate;
	}

	/**
	 * Gets the process succesfull.
	 * 
	 * @return the process succesfull
	 */
	public int getProcessSuccesfull() {

		return processSuccesfull;
	}

	/**
	 * Gets the to date.
	 * 
	 * @return the to date
	 */
	public Date getToDate() {

		return toDate;
	}

	/**
	 * Sets the file name.
	 * 
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	/**
	 * Sets the from date.
	 * 
	 * @param fromDate the new from date
	 */
	public void setFromDate(Date fromDate) {

		this.fromDate = fromDate;
	}

	/**
	 * Sets the initiated manually.
	 * 
	 * @param initiatedManually the new initiated manually
	 */
	public void setInitiatedManually(int initiatedManually) {

		this.initiatedManually = initiatedManually;
	}

	/**
	 * Sets the process date.
	 * 
	 * @param processDate the new process date
	 */
	public void setProcessDate(Timestamp processDate) {

		this.processDate = processDate;
	}

	/**
	 * Sets the process succesfull.
	 * 
	 * @param processSuccesfull the new process succesfull
	 */
	public void setProcessSuccesfull(int processSuccesfull) {

		this.processSuccesfull = processSuccesfull;
	}

	/**
	 * Sets the to date.
	 * 
	 * @param toDate the new to date
	 */
	public void setToDate(Date toDate) {

		this.toDate = toDate;
	}

}
