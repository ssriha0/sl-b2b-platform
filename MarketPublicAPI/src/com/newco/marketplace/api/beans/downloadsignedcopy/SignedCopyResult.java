package com.newco.marketplace.api.beans.downloadsignedcopy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Success")
public class SignedCopyResult {
	
	@XStreamOmitField
	private String filePath;
	
	@XStreamOmitField
	private String fileName;
	
	@XStreamOmitField
	private String fileSize;
	
	@XStreamOmitField
	private long fileSizeInKB;
	
	@XStreamOmitField
	private String uploadedBy;
	
	@XStreamOmitField
	private String uploadedDate;
	
	@XStreamOmitField
	private String soId;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public long getFileSizeInKB() {
		return fileSizeInKB;
	}

	public void setFileSizeInKB(long fileSizeInKB) {
		this.fileSizeInKB = fileSizeInKB;
	}

	

	
}
