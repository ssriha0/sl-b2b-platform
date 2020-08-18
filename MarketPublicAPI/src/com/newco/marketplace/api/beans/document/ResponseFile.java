package com.newco.marketplace.api.beans.document;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("file")
public class ResponseFile{
	

	 
	@XStreamAlias("fileName")
	private String fileName;
	
	@XStreamAlias("documentId")
	private String documentId;
	
	@XStreamAlias("uploadedTime")
	private String uploadedTime;
	
	
	@XStreamAlias("message")
	private String message;
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getUploadedTime() {
		return uploadedTime;
	}

	public void setUploadedTime(String uploadedTime) {
		this.uploadedTime = uploadedTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
	
}
