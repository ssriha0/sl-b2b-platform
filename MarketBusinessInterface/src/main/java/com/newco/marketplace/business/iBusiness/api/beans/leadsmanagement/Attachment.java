package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "Attachment")
@XStreamAlias("Attachment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Attachment {

	@XStreamAlias("DocumentName")
	private String documentName;

	@XStreamAlias("DocumentSize")
	private Double documentSize;
	
	@XStreamAlias("DocumentId")
	private Integer documentId;
	
	@XStreamAlias("CreatedDate")
	private String createdDate;

	@XStreamAlias("FirstName")
	private String firstName;
	
	@XStreamAlias("LastName")
	private String lastName;
	
	@XStreamAlias("DocCategoryId")
	private Integer docCategoryId;
	
	@XStreamAlias("DocPath")
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
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
