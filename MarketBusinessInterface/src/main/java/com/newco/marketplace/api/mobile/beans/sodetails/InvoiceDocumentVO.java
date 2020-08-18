package com.newco.marketplace.api.mobile.beans.sodetails;


/**
 * This is a generic bean class for storing Document information.
 * @author Infosys
 *
 */


public class InvoiceDocumentVO {
	
	private Integer documentId;
	
	private String documentType;
	
	private String documentDescription;
	
	private String fileType;
	
	private String fileName;
	
	private String uploadedBy;
	
	private String uploadedbyName;
	
	private String uploadDateTime;
	
	private Integer docSize;
	
    private Integer invoicePartId;

public Integer getDocumentId() {
	return documentId;
}

public void setDocumentId(Integer documentId) {
	this.documentId = documentId;
}

public String getDocumentType() {
	return documentType;
}

public void setDocumentType(String documentType) {
	this.documentType = documentType;
}

public String getDocumentDescription() {
	return documentDescription;
}

public void setDocumentDescription(String documentDescription) {
	this.documentDescription = documentDescription;
}

public String getFileType() {
	return fileType;
}

public void setFileType(String fileType) {
	this.fileType = fileType;
}

public String getFileName() {
	return fileName;
}

public void setFileName(String fileName) {
	this.fileName = fileName;
}

public String getUploadedBy() {
	return uploadedBy;
}

public void setUploadedBy(String uploadedBy) {
	this.uploadedBy = uploadedBy;
}

public String getUploadedbyName() {
	return uploadedbyName;
}

public void setUploadedbyName(String uploadedbyName) {
	this.uploadedbyName = uploadedbyName;
}

public String getUploadDateTime() {
	return uploadDateTime;
}

public void setUploadDateTime(String uploadDateTime) {
	this.uploadDateTime = uploadDateTime;
}

public Integer getDocSize() {
	return docSize;
}

public void setDocSize(Integer docSize) {
	this.docSize = docSize;
}

public Integer getInvoicePartId() {
	return invoicePartId;
}

public void setInvoicePartId(Integer invoicePartId) {
	this.invoicePartId = invoicePartId;
}
   
   
   
}
