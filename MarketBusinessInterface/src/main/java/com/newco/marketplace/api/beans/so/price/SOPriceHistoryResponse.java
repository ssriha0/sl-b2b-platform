package com.newco.marketplace.api.beans.so.price;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XStreamAlias("soPriceHistory") 
public class SOPriceHistoryResponse  implements IAPIResponse {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version=PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace=PublicAPIConstant.PRICEHISTORY_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance=PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("serviceOrders")
	private ServiceOrders serviceOrdersList;

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

	public ServiceOrders getServiceOrdersList() {
		return serviceOrdersList;
	}

	public void setServiceOrdersList(ServiceOrders serviceOrdersList) {
		this.serviceOrdersList = serviceOrdersList;
	}

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}
}

	

	
	
	


