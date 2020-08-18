package com.newco.marketplace.web.dto.buyerFileUpload;

import java.sql.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

public class RunningHistoryDTO extends SerializedBaseDTO{
	protected final Log logger = LogFactory.getLog(getClass());
	private String fileName;
	private Date createdDate;
	private int errorNo;
	private int successNo;
	private int file_id;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}
	public int getSuccessNo() {
		return successNo;
	}
	public void setSuccessNo(int successNo) {
		this.successNo = successNo;
	}
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
	}
}
