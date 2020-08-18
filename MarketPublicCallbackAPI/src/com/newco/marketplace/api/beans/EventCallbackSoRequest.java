package com.newco.marketplace.api.beans;

import com.newco.marketplace.interfaces.EventCallbackConstants;
import com.newco.marketplace.interfaces.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing response information for the
 * SORetrieveService
 * 
 * @author Infosys
 * 
 */
@XStreamAlias("soResponse")
public class EventCallbackSoRequest implements IAPIResponse {

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

	@XStreamAlias("serviceorders")
	private ServiceOrders serviceOrders;

	public EventCallbackSoRequest() {
		this.schemaInstance = EventCallbackConstants.SCHEMA_INSTANCE;
		this.namespace = EventCallbackConstants.SORESPONSE_NAMESPACE;
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

	public ServiceOrders getServiceOrders() {
		return serviceOrders;
	}

	public void setServiceOrders(ServiceOrders serviceOrders) {
		this.serviceOrders = serviceOrders;
	}

}
