package com.newco.marketplace.api.beans.so.addEstimate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute; 


//@XSD(name="addSOEstimateResponse.xsd",  path="/resources/schemas/mobile/")
//@XStreamAlias("addEstimateResponse")
@XStreamAlias("addEstimateResponse")
//@XmlAccessorType(XmlAccessType.FIELD)
public class AddEstimateResponse implements IAPIResponse { 
	
	@XStreamAlias("version")
	@XStreamAsAttribute()
	private String version = PublicAPIConstant.SORESPONSE_VERSION;

	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation; 

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace = PublicAPIConstant.SORESPONSE_NAMESPACE;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("estimationId")
	private Integer estimationId;
	
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

	public Integer getEstimationId() {
		return estimationId;
	}

	public void setEstimationId(Integer estimationId) {
		this.estimationId = estimationId;
	}
	
	
	
}
