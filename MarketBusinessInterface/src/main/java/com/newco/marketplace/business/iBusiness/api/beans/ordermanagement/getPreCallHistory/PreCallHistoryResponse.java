package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory;

import java.util.List;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.retrieve.v1_2.ServiceOrders;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.PreCallHistory;
import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("getPrecallHistoryDetailsResponse")
public class PreCallHistoryResponse implements IAPIResponse{
	
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

	@XStreamAlias("precallHistoryDetails")
	private PreCallHistoryDetails preCallHistoryDetails;

	public PreCallHistoryResponse() {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.SORESPONSE_NAMESPACE;
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

	public PreCallHistoryDetails getPreCallHistoryDetails() {
		return preCallHistoryDetails;
	}

	public void setPreCallHistoryDetails(PreCallHistoryDetails preCallHistoryDetails) {
		this.preCallHistoryDetails = preCallHistoryDetails;
	}

}
