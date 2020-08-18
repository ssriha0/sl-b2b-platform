package com.newco.marketplace.api.beans.alerts;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the SendAlertsResponse
 * @author Infosys
 *
 */
@XStreamAlias("sendAlertResponse")
public class SendAlertsResponse implements IAPIResponse{
	
	
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

	public SendAlertsResponse () {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.sendAlert.NAMESPACE;
	}
	public SendAlertsResponse(Results results) {
		this();
		this.results = results;
	}
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
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
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}


}
