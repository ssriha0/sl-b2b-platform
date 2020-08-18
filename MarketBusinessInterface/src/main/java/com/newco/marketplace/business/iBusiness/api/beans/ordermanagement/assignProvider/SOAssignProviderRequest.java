package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.assignProvider;

import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("soAssignProviderRequest")
public class SOAssignProviderRequest extends UserIdentificationRequest{
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation;
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.FETCH_SO_NAMESPACE;
	 
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("resourceId")   
	private Integer resourceId;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
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

	
}
