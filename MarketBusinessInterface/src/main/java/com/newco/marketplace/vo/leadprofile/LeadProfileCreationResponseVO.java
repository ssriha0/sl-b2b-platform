package com.newco.marketplace.vo.leadprofile;

import javax.xml.bind.annotation.XmlElement;

import com.newco.marketplace.api.beans.Errors;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("response")
public class LeadProfileCreationResponseVO {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileCreationVOResponse.xsd";
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileCreationVOResponse";
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("result")
	private String result;
	
	@XStreamAlias("errors")
	private Errors errors;
	
	@XStreamAlias("partner_id")
	private String partnerId;
	
	@XStreamAlias("status")
	private String status;	
	
	@XStreamAlias("filter_set_competitive")
	private FilterSetCompetitive competitiveFilter;
	
	@XStreamAlias("filter_set_exclusive")
	private FilterSetExclusive exclusiveFilter;

	
	private Results results;

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public FilterSetCompetitive getCompetitiveFilter() {
		return competitiveFilter;
	}

	public void setCompetitiveFilter(FilterSetCompetitive competitiveFilter) {
		this.competitiveFilter = competitiveFilter;
	}

	public FilterSetExclusive getExclusiveFilter() {
		return exclusiveFilter;
	}

	public void setExclusiveFilter(FilterSetExclusive exclusiveFilter) {
		this.exclusiveFilter = exclusiveFilter;
	}
	

}
