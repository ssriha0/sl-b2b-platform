package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.mysql.jdbc.Blob;
import com.newco.marketplace.webservices.base.CommonVO;

public class SODocument extends CommonVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7755316317816337483L;
	private String fileName;
	private int fileSize;
	private String fileDesc;
	private Date uploadDate;
	private Blob document;
	private int docCategoryId;
	//Priority 5B change
	private String docTitle;
		
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Blob getDocument() {
		return document;
	}
	public void setDocument(Blob document) {
		this.document = document;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public int getDocCategoryId() {
		return docCategoryId;
	}
	public void setDocCategoryId(int docCategoryId) {
		this.docCategoryId = docCategoryId;
	}
	
	
	

}
