package com.newco.marketplace.api.mobile.beans.uploadProviderProfileImage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderDocument;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XSD(name = "proProfileImageUploadResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "profileUploadResponse")
@XStreamAlias("profileUploadResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderProfileImageUploadResponse {
	
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
