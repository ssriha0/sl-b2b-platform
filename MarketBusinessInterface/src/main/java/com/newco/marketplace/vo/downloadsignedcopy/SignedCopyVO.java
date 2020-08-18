package com.newco.marketplace.vo.downloadsignedcopy;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SignedCopyVO {
	private SimpleDateFormat fmtDate = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	private String soId;
	private String filePath;
	private String docTitle;
	private String fileName;
	private String fileSize;
	private String uploadedBy;
	private String uploadedDateString;
	private Date uploadedDate;
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
    
	public String getUploadedDateString() {
		Date uploadedDate = getUploadedDate();
		String formattedDate = fmtDate.format(uploadedDate);
		if(StringUtils.isNotBlank(formattedDate)){
			this.uploadedDateString = formattedDate;
		}
		return uploadedDateString;
	}
    
	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	
	

}
