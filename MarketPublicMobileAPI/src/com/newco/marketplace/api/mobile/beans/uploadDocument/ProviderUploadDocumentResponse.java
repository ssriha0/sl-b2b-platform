package com.newco.marketplace.api.mobile.beans.uploadDocument;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "documentUploadResponse")
@XStreamAlias("documentUploadResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderUploadDocumentResponse implements IAPIResponse {
	
	@XmlElement(name="soId")
	private String soId;
	
	@XStreamAlias("results")
	private Results results;
	
	@XmlElement(name="document")
	private ProviderDocument document;
	
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	
	public ProviderDocument getDocument() {
		return document;
	}

	public void setDocument(ProviderDocument document) {
		this.document = document;
	}
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public void setNamespace(String namespace) {
		// TODO Auto-generated method stub
		
	}
	public void setSchemaInstance(String schemaInstance) {
		// TODO Auto-generated method stub
		
	}
	public void setSchemaLocation(String schemaLocation) {
		// TODO Auto-generated method stub
		
	}
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
