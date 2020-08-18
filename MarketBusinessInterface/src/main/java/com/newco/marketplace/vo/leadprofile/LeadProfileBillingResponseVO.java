package com.newco.marketplace.vo.leadprofile;

import com.newco.marketplace.api.beans.Errors;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("response")
public class LeadProfileBillingResponseVO {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileBillingVOResponse.xsd";
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileBillingVOResponse";
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("status")
	private String status;
	
	@XStreamAlias("result")
	private String result;
	
	@XStreamAlias("errors")
	private Errors errors;
	
	private Results results;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}