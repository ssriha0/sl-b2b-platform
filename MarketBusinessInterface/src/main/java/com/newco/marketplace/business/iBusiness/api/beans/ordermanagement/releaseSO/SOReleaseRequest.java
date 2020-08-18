package com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.releaseSO;

import com.newco.marketplace.business.iBusiness.om.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.business.iBusiness.om.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("soReleaseRequest")
public class SOReleaseRequest extends UserIdentificationRequest{

	 @XStreamAlias("version")   
	 @XStreamAsAttribute()   
	 private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	 @XStreamAlias("xsi:schemaLocation")   
	 @XStreamAsAttribute()   
	 private String schemaLocation;
	 
	 @XStreamAlias("xmlns")   
	 @XStreamAsAttribute()   
	 private String namespace = PublicAPIConstant.SORESPONSE_NAMESPACE;
	
	 @XStreamAlias("xmlns:xsi")   
	 @XStreamAsAttribute()   
	 private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	 @XStreamAlias("reason")   
	 private String reason;
	 
	 @XStreamAlias("comments")   
	 private String comments;
	 
	 @XStreamAlias("releaseByFirmIndicator")   
	 private Boolean releaseByFirmIndicator = Boolean.FALSE;

	 
	 public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getReleaseByFirmIndicator() {
		return releaseByFirmIndicator;
	}

	public void setReleaseByFirmIndicator(Boolean releaseByFirmIndicator) {
		this.releaseByFirmIndicator = releaseByFirmIndicator;
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
