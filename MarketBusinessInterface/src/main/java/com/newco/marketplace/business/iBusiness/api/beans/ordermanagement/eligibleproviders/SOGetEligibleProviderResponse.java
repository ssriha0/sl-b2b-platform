package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("soEligibleProviderResponse")   
public class SOGetEligibleProviderResponse implements IAPIResponse {


	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version=PublicAPIConstant.SORESPONSE_VERSION;

	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;

	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace=PublicAPIConstant.SORESPONSE_NAMESPACE;

	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance=PublicAPIConstant.SCHEMA_INSTANCE;

	@XStreamAlias("results")
	private Results results;

	@XStreamAlias("eligibleProviders")
	private EligibleProviders eligibleProviders;

	@XStreamAlias("assignedResourceId")
	private String assignedResourceId;
	
	@XStreamAlias("assignedResource")
	private String assignedResource;

	public String getAssignedResourceId() {
		return assignedResourceId;
	}

	public void setAssignedResourceId(String assignedResourceId) {
		this.assignedResourceId = assignedResourceId;
	}

	public EligibleProviders getEligibleProviders() {
		return eligibleProviders;
	}

	public void setEligibleProviders(EligibleProviders eligibleProviders) {
		this.eligibleProviders = eligibleProviders;
	}


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


	public String getAssignedResource() {
		return assignedResource;
	}

	public void setAssignedResource(String assignedResource) {
		this.assignedResource = assignedResource;
	}

}
