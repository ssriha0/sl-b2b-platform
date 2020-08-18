package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "lead_documents")
@XmlRootElement()
public class LeadDocuments extends LeadChild{
	private static final long serialVersionUID = 9180446939179439349L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="sl_lead_doc_id")
	private Integer leadDocumenetId;
	
	@Column(name="doc_id")
	private Integer documentId;
	
	@Column(name="doc_type")
	private String documenetTypeId;
	
	@Column(name="created_by")
	private String createdBy;
	
	public Integer getLeadDocumenetId() {
		return leadDocumenetId;
	}

	public void setLeadDocumenetId(Integer leadDocumenetId) {
		this.leadDocumenetId = leadDocumenetId;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getDocumenetTypeId() {
		return documenetTypeId;
	}

	public void setDocumenetTypeId(String documenetTypeId) {
		this.documenetTypeId = documenetTypeId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
