package com.servicelive.wallet.batch.gl.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.servicelive.common.CommonConstants;


// TODO: Auto-generated Javadoc
/**
 * The Class ShopifyGLProcessLogVO to hold the GL Process Log
 */
public class ShopifyGLProcessLogVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8554798475137419492L;

	private Integer glProcessId;
	private Date fromDate;
	private Date toDate;
	private Timestamp processDate;
	private String fileName;
	private int processSuccesfull;
	private int initiatedManually;
	
	public ShopifyGLProcessLogVO(){
		
	}
	
	public ShopifyGLProcessLogVO( Date fromDate,
			Date toDate, String fileName) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.fileName = fileName;
		this.processSuccesfull = CommonConstants.PROCESS_SUCCESSFUL;
		this.initiatedManually = CommonConstants.INITIATED_MANUALLY;
	}
	public Integer getGlProcessId() {
		return glProcessId;
	}
	public void setGlProcessId(Integer glProcessId) {
		this.glProcessId = glProcessId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Timestamp getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Timestamp processDate) {
		this.processDate = processDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getProcessSuccesfull() {
		return processSuccesfull;
	}
	public void setProcessSuccesfull(int processSuccesfull) {
		this.processSuccesfull = processSuccesfull;
	}
	public int getInitiatedManually() {
		return initiatedManually;
	}
	public void setInitiatedManually(int initiatedManually) {
		this.initiatedManually = initiatedManually;
	}
	
	
	
	
}
