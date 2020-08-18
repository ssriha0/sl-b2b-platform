package com.newco.marketplace.api.beans.so.cancel.v1_2;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * This class maps the Response xml for SO Cancellation (Cancellation through API v1.2)
 * */
@XStreamAlias("soCancelResponse")
public class SOCancelResponse implements IAPIResponse{
	
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
	
	@XStreamAlias("soStatusInfo")
	private SOStatusInfo soStatusInfo;
	
	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	
	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
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

	public SOStatusInfo getSoStatusInfo() {
		return soStatusInfo;
	}

	public void setSoStatusInfo(SOStatusInfo soStatusInfo) {
		this.soStatusInfo = soStatusInfo;
	}
}
