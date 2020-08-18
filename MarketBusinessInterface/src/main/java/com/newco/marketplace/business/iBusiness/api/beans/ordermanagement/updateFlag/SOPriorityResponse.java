package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.updateFlag;

import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("soPriorityResponse")   
public class SOPriorityResponse implements IAPIResponse{

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
	
	@XStreamAlias("flag")
	private Integer flag;
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Results getResults() {
		return results;
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

	public void setResults(Results results) {
		this.results = results;
	}


	

	

	

	
}
