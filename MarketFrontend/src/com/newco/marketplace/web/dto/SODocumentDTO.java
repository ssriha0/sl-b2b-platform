package com.newco.marketplace.web.dto;

import java.io.File;
import java.util.Date;

import com.newco.marketplace.utils.DocumentUtils;


/** 
 * $Revision: 1.19 $ $Author: akashya $ $Date: 2008/05/21 23:33:01 $
 */


public class SODocumentDTO extends AbstractAjaxResultsDTO{	
	
	private static final long serialVersionUID = -2866054055922728336L;
	private Integer documentId;
	private String name;
	private String desc;
	private Double sizeDouble;
	private Integer size;
	private String sizeString;
	private byte[] blobBytes;
	private String format;
	private Date createdDate;
	public String documentTitle; 
	
	
	private File upload;//
	private Integer category;
	private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileCaption;
    private boolean docInCurrentVisit;
    private String docPath;
    private String role;
    private String uploadedBy;
 
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isDocInCurrentVisit() { 
    	return docInCurrentVisit; 
    } 
    public void setDocInCurrentVisit(boolean docInCurrentVisit) { 
    	this.docInCurrentVisit = docInCurrentVisit; 
    } 
	public String getFileCaption() {
		return fileCaption;
	}
	public void setFileCaption(String fileCaption) {
		this.fileCaption = fileCaption;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getName() {
		return name;
	}
	public void setName(String fileName) {
		this.name = fileName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSizeString() {
		return sizeString;
	}
	public void setSizeString(String sizeString) {
		this.sizeString = sizeString;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public byte[] getBlobBytes() {
		return blobBytes;
	}
	public void setBlobBytes(byte[] blobBytes) {
		this.blobBytes = blobBytes;
	}
	public String toJSON() {
		return null;
	}
	public String toXml() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<document>");
		sb.append("<document_id>").append(this.getDocumentId()).append("</document_id>");
		sb.append("<document_name>").append(this.getName()).append("</document_name>");
		sb.append("<document_size>").append(DocumentUtils.convertBytesToKiloBytes(this.getSize().intValue(), true)).append("</document_size>");
		sb.append("<document_desc>").append(this.getDesc()).append("</document_desc>");
		sb.append("<document_category_id>").append(this.getCategory()).append("</document_category_id>");
		sb.append("<document_path>").append(this.getDocPath()).append("</document_path>");
		sb.append("</document>");
		return sb.toString();
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Double getSizeDouble() {
		return sizeDouble;
	}
	public void setSizeDouble(Double sizeDouble) {
		this.sizeDouble = sizeDouble;
	}
	public StringBuffer getBuffer() {
		return null;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
}
