package com.newco.marketplace.dto.vo.ptd;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class PTDProcessLogVO extends SerializableBaseVO{
	private int ptdTransactionsReceived;
	private String ptdProcessStatus;
	private int ptdLogId;
	private int ptdTransactionsUpdated;
	private Timestamp processRunDate;
	private int ptdSequenceNumber;
	private int prevSequenceNumber;
	private int ptdReconsiledInd;
	public int getPtdReconsiledInd() {
		return ptdReconsiledInd;
	}
	public void setPtdReconsiledInd(int ptdReconsiledInd) {
		this.ptdReconsiledInd = ptdReconsiledInd;
	}
	public String getPtdProcessStatus() {
		return ptdProcessStatus;
	}
	public void setPtdProcessStatus(String ptdProcessStatus) {
		this.ptdProcessStatus = ptdProcessStatus;
	}
	public int getPtdTransactionsReceived() {
		return ptdTransactionsReceived;
	}
	public void setPtdTransactionsReceived(int ptdTransactionsReceived) {
		this.ptdTransactionsReceived = ptdTransactionsReceived;
	}
	public int getPtdTransactionsUpdated() {
		return ptdTransactionsUpdated;
	}
	public void setPtdTransactionsUpdated(int ptdTransactionsUpdated) {
		this.ptdTransactionsUpdated = ptdTransactionsUpdated;
	}
	public Timestamp getProcessRunDate() {
		return processRunDate;
	}
	public void setProcessRunDate(Timestamp processRunDate) {
		this.processRunDate = processRunDate;
	}
	public int getPtdSequenceNumber() {
		return ptdSequenceNumber;
	}
	public void setPtdSequenceNumber(int ptdSequenceNumber) {
		this.ptdSequenceNumber = ptdSequenceNumber;
	}
	public int getPrevSequenceNumber() {
		return prevSequenceNumber;
	}
	public void setPrevSequenceNumber(int prevSequenceNumber) {
		this.prevSequenceNumber = prevSequenceNumber;
	}
	
	public int getPtdLogId() {
		return ptdLogId;
	}
	
	public void setPtdLogId(int ptdLogId) {
		this.ptdLogId = ptdLogId;
	}
}
