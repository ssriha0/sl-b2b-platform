package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author ndixit
 *
 */
@XSD(name="reassignSOResponse.xsd", path="/resources/schemas/so/")
@XStreamAlias("reassignSOResponse")	           
public class ReassignSOResponse implements IAPIResponse{
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance;
	 
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private Double version;
	 
	@XStreamAlias("results")
	private Results results;
	
	public ReassignSOResponse () {	
		
	}
	public ReassignSOResponse (Results results) {
		this.results = results;
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

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public void setResults(Results results) {
		this.results=results;
	}

	public void setVersion(String version) {
		// TODO Auto-generated method stub
	}
	public Results getResults() {
		return results;
	}

}
