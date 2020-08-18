package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sears.os.vo.SerializableBaseVO;

public class UploadAuditSuccessVO extends SerializableBaseVO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private int fileId;
	private String soId;
	
	public UploadAuditSuccessVO(int fileId, String soId) {
		this.fileId = fileId;
		this.soId = soId;
	}
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public Log getLogger() {
		return logger;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
}
