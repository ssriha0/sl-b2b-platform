package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sears.os.vo.SerializableBaseVO;

public class RunningHistoryVO extends SerializableBaseVO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private int fileId;
	private String fileName;
	private String createdDate;
	private String errorNo;
	private String successNo;
	private String buyerName;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getErrorNo() {
		return errorNo;
	}
	
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	
	public String getSuccessNo() {
		return successNo;
	}
	
	public void setSuccessNo(String successNo) {
		this.successNo = successNo;
	}

	public int getFileId() {
		return fileId;
	}
	
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
	public String getBuyerName() {
		return buyerName;
	}
	
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
}
