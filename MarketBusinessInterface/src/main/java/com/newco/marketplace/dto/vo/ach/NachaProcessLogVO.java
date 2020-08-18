package com.newco.marketplace.dto.vo.ach;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class NachaProcessLogVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3928164985824695761L;
	private long nachaProcessId;
	private String generatedFileName;
	private String processSuccessful;
	private String initiatedManually;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String generatedMachineName;
	private int processStatusId;
	private String fileIdModifier;
	private String achDateTime;
	private Timestamp nachaRunDate;
	private Timestamp originationRunDate;
	private Double totalDebitAmount;
	private Double totalCreditAmount;
	private Long recordCount;
	
	public int getProcessStatusId() {
		return processStatusId;
	}
	public void setProcessStatusId(int processStatusId) {
		this.processStatusId = processStatusId;
	}
	public String getGeneratedMachineName() {
		return generatedMachineName;
	}
	public void setGeneratedMachineName(String generatedMachineName) {
		this.generatedMachineName = generatedMachineName;
	}
	public String getGeneratedFileName() {
		return generatedFileName;
	}
	public void setGeneratedFileName(String generatedFileName) {
		this.generatedFileName = generatedFileName;
	}
	public String getProcessSuccessful() {
		return processSuccessful;
	}
	public void setProcessSuccessful(String processSuccessful) {
		this.processSuccessful = processSuccessful;
	}
	public String getInitiatedManually() {
		return initiatedManually;
	}
	public void setInitiatedManually(String initiatedManually) {
		this.initiatedManually = initiatedManually;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getNachaProcessId() {
		return nachaProcessId;
	}
	public void setNachaProcessId(long nachaProcessId) {
		this.nachaProcessId = nachaProcessId;
	}
	
	public String getFileIdModifier() {
		return fileIdModifier;
	}
	public void setFileIdModifier(String fileIdModifier) {
		this.fileIdModifier = fileIdModifier;
	}
	public String getAchDateTime() {
		return achDateTime;
	}
	public void setAchDateTime(String achDateTime) {
		this.achDateTime = achDateTime;
	}
	public Timestamp getNachaRunDate() {
		return nachaRunDate;
	}
	public void setNachaRunDate(Timestamp nachaRunDate) {
		this.nachaRunDate = nachaRunDate;
	}
	public Double getTotalDebitAmount() {
		return totalDebitAmount;
	}
	public void setTotalDebitAmount(Double totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}
	public Double getTotalCreditAmount() {
		return totalCreditAmount;
	}
	public void setTotalCreditAmount(Double totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public Timestamp getOriginationRunDate() {
		return originationRunDate;
	}
	public void setOriginationRunDate(Timestamp originationRunDate) {
		this.originationRunDate = originationRunDate;
	}
	
	
}
