package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("GetEligibleProvidersResponse")
public class GetEligibleProviderResponse implements IAPIResponse {

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

	@XStreamAlias("FirmId")
	private Integer firmId;

	@XStreamAlias("LeadId")
	private String leadId;

	@XStreamAlias("EligibleProviders")
	private EligibleProvidersForLead providers;

	private ResultsCode validationCode;

	public GetEligibleProviderResponse() {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.NEW_SERVICES_NAMESPACE;
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

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public EligibleProvidersForLead getProviders() {
		return providers;
	}

	public void setProviders(EligibleProvidersForLead providers) {
		this.providers = providers;
	}

	public ResultsCode getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(ResultsCode validationCode) {
		this.validationCode = validationCode;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

}
