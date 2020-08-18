package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "so_document")
@XmlRootElement()
public class SODocument extends SOChild{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7755316317816337483L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_document_id")
	private Integer soDocumentId;
	
	@Column(name = "document_id")
	private Integer documentId;
		
	@Column(name = "doc_source")
	private String docSource;
	

	public String getDocSource() {
		return docSource;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public Integer getSoDocumentId() {
		return soDocumentId;
	}

	@XmlElement()
	public void setDocSource(String docSource) {
		this.docSource = docSource;
	}

	@XmlElement()
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	@XmlElement()
	public void setSoDocumentId(Integer soDocumentId) {
		this.soDocumentId = soDocumentId;
	}
	
}
