package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
@XmlRootElement(name = "updateLeadStatusResponse")
@XStreamAlias("updateLeadStatusResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateLeadStatusResponse implements IAPIResponse{

	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance;
	
	@XStreamAlias("results")
	private Results results;
	
	public Results getResults() {
		return results;
	}
	public void setResults(Results results) {
		this.results = results;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
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
	
	

}
