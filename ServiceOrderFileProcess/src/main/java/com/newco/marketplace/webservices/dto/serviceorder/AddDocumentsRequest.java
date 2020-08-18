package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.List;

import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class AddDocumentsRequest extends ABaseWebserviceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8245729929970616482L;
	private String serviceOrderID;
	private List<Document> documents;
	
	@XmlElement(minOccurs="1", nillable=false)
	public String getServiceOrderID() {
		return serviceOrderID;
	}

	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

}
