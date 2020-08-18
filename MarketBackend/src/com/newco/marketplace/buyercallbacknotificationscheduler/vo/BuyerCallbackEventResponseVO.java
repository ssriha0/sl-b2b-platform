package com.newco.marketplace.buyercallbacknotificationscheduler.vo;

import com.newco.marketplace.api.annotation.XSD;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XSD(name="buyerEventCallbackResponse.xsd", path="/resources/schemas/buyerEventCallback/")
@XStreamAlias("buyerEventCallbackResponse")
public class BuyerCallbackEventResponseVO implements IAPIResponse{
	
	@XStreamAlias("xsi:schemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	public BuyerCallbackEventResponseVO() {
		
	}

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;

	public BuyerCallbackEventResponseVO(Results results) {
		this.results = results;
	}

	@XStreamAlias("results")
	private Results results;
	
	@XStreamAlias("eventId")
	private Integer eventId;
	
	@XStreamAlias("filterNames")
	private String filterNames;

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



	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getFilterNames() {
		return filterNames;
	}

	public void setFilterNames(String filterNames) {
		this.filterNames = filterNames;
	}

	@Override
	public void setVersion(String version) {
		// TODO Auto-generated method stub
		
	}

}
