package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaProcessLogVO.
 */
public class NachaProcessLogVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3928164985824695761L;

	/** The ach date time. */
	private String achDateTime;

	/** The created date. */
	private Timestamp createdDate;

	/** The file id modifier. */
	private String fileIdModifier;

	/** The generated file name. */
	private String generatedFileName;

	/** The generated machine name. */
	private String generatedMachineName;

	/** The initiated manually. */
	private String initiatedManually;

	/** The modified date. */
	private Timestamp modifiedDate;

	/** The nacha process id. */
	private long nachaProcessId;

	/** The nacha run date. */
	private Timestamp nachaRunDate;

	/** The origination run date. */
	private Timestamp originationRunDate;

	/** The process status id. */
	private int processStatusId;

	/** The process successful. */
	private String processSuccessful;

	/** The record count. */
	private Long recordCount;

	/** The total credit amount. */
	private Double totalCreditAmount;

	/** The total debit amount. */
	private Double totalDebitAmount;

	/**
	 * Gets the ach date time.
	 * 
	 * @return the ach date time
	 */
	public String getAchDateTime() {

		return achDateTime;
	}

	/**
	 * Gets the created date.
	 * 
	 * @return the created date
	 */
	public Timestamp getCreatedDate() {

		return createdDate;
	}

	/**
	 * Gets the file id modifier.
	 * 
	 * @return the file id modifier
	 */
	public String getFileIdModifier() {

		return fileIdModifier;
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
	 * Gets the generated machine name.
	 * 
	 * @return the generated machine name
	 */
	public String getGeneratedMachineName() {

		return generatedMachineName;
	}

	/**
	 * Gets the initiated manually.
	 * 
	 * @return the initiated manually
	 */
	public String getInitiatedManually() {

		return initiatedManually;
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
	 * Gets the nacha process id.
	 * 
	 * @return the nacha process id
	 */
	public long getNachaProcessId() {

		return nachaProcessId;
	}

	/**
	 * Gets the nacha run date.
	 * 
	 * @return the nacha run date
	 */
	public Timestamp getNachaRunDate() {

		return nachaRunDate;
	}

	/**
	 * Gets the origination run date.
	 * 
	 * @return the origination run date
	 */
	public Timestamp getOriginationRunDate() {

		return originationRunDate;
	}

	/**
	 * Gets the process status id.
	 * 
	 * @return the process status id
	 */
	public int getProcessStatusId() {

		return processStatusId;
	}

	/**
	 * Gets the process successful.
	 * 
	 * @return the process successful
	 */
	public String getProcessSuccessful() {

		return processSuccessful;
	}

	/**
	 * Gets the record count.
	 * 
	 * @return the record count
	 */
	public Long getRecordCount() {

		return recordCount;
	}

	/**
	 * Gets the total credit amount.
	 * 
	 * @return the total credit amount
	 */
	public Double getTotalCreditAmount() {

		return totalCreditAmount;
	}

	/**
	 * Gets the total debit amount.
	 * 
	 * @return the total debit amount
	 */
	public Double getTotalDebitAmount() {

		return totalDebitAmount;
	}

	/**
	 * Sets the ach date time.
	 * 
	 * @param achDateTime the new ach date time
	 */
	public void setAchDateTime(String achDateTime) {

		this.achDateTime = achDateTime;
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
	 * Sets the file id modifier.
	 * 
	 * @param fileIdModifier the new file id modifier
	 */
	public void setFileIdModifier(String fileIdModifier) {

		this.fileIdModifier = fileIdModifier;
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
	 * Sets the generated machine name.
	 * 
	 * @param generatedMachineName the new generated machine name
	 */
	public void setGeneratedMachineName(String generatedMachineName) {

		this.generatedMachineName = generatedMachineName;
	}

	/**
	 * Sets the initiated manually.
	 * 
	 * @param initiatedManually the new initiated manually
	 */
	public void setInitiatedManually(String initiatedManually) {

		this.initiatedManually = initiatedManually;
	}

	/**
	 * Sets the modified date.
	 * 
	 * @param modifiedDate the new modified date
	 */
	public void setModifiedDate(Timestamp modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/**
	 * Sets the nacha process id.
	 * 
	 * @param nachaProcessId the new nacha process id
	 */
	public void setNachaProcessId(long nachaProcessId) {

		this.nachaProcessId = nachaProcessId;
	}

	/**
	 * Sets the nacha run date.
	 * 
	 * @param nachaRunDate the new nacha run date
	 */
	public void setNachaRunDate(Timestamp nachaRunDate) {

		this.nachaRunDate = nachaRunDate;
	}

	/**
	 * Sets the origination run date.
	 * 
	 * @param originationRunDate the new origination run date
	 */
	public void setOriginationRunDate(Timestamp originationRunDate) {

		this.originationRunDate = originationRunDate;
	}

	/**
	 * Sets the process status id.
	 * 
	 * @param processStatusId the new process status id
	 */
	public void setProcessStatusId(int processStatusId) {

		this.processStatusId = processStatusId;
	}

	/**
	 * Sets the process successful.
	 * 
	 * @param processSuccessful the new process successful
	 */
	public void setProcessSuccessful(String processSuccessful) {

		this.processSuccessful = processSuccessful;
	}

	/**
	 * Sets the record count.
	 * 
	 * @param recordCount the new record count
	 */
	public void setRecordCount(Long recordCount) {

		this.recordCount = recordCount;
	}

	/**
	 * Sets the total credit amount.
	 * 
	 * @param totalCreditAmount the new total credit amount
	 */
	public void setTotalCreditAmount(Double totalCreditAmount) {

		this.totalCreditAmount = totalCreditAmount;
	}

	/**
	 * Sets the total debit amount.
	 * 
	 * @param totalDebitAmount the new total debit amount
	 */
	public void setTotalDebitAmount(Double totalDebitAmount) {

		this.totalDebitAmount = totalDebitAmount;
	}

}
