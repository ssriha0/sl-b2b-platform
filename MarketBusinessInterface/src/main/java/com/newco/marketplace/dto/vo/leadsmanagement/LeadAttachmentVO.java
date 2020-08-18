package com.newco.marketplace.dto.vo.leadsmanagement;


//Vo class which holds lead_documents values.
public class LeadAttachmentVO {
	
	private String documentName;
	private Double documentSize;
	private Integer documentId;
	private String createdDate;
	private String firstName;
	private String lastName;
	private Integer docCategoryId;
	private String docPath;
  
	
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
	public Double getDocumentSize() {
		return documentSize;
	}
	public void setDocumentSize(Double documentSize) {
		this.documentSize = documentSize;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getDocCategoryId() {
		return docCategoryId;
	}
	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	
    
}
