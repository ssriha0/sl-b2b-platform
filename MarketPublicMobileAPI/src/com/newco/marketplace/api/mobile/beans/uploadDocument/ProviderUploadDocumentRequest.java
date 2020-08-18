package com.newco.marketplace.api.mobile.beans.uploadDocument;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XmlRootElement(name = "documentUploadRequest")
@XStreamAlias("documentUploadRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderUploadDocumentRequest  {

	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicMobileAPIConstant.PRO_UPLOAD_DOC_REQ_SCHEMALOCATION;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicMobileAPIConstant.MOBILE_SERVICES_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicMobileAPIConstant.SCHEMA_INSTANCE;
	
	@XmlElement(name="providerId")
	private Integer providerId;
	
	@XmlElement(name="document")
	private ProviderDocument document;
	
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getSchemaInstance() {
		return schemaInstance;
	}
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}	
	public ProviderDocument getDocument() {
		return document;
	}	
	public void setDocument(ProviderDocument document) {
		this.document = document;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	
	
}
