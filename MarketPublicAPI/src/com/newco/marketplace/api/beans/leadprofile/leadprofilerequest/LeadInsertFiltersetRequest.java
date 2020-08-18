package com.newco.marketplace.api.beans.leadprofile.leadprofilerequest;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


@XmlRootElement(name = "leadInsertFiltersetRequest")
@XStreamAlias("leadInsertFiltersetRequest")
public class LeadInsertFiltersetRequest {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadInsertFiltersetRequest.xsd";
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadInsertFiltersetRequest";
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("vendorId")
	private Integer vendorId;
	
	@XStreamAlias("termsAndCondition")
	private String termsAndCondition;
	
	@XStreamAlias("projects")
	private Projects projects;

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
	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	public String getTermsAndCondition() {
		return termsAndCondition;
	}

	public void setTermsAndCondition(String termsAndCondition) {
		this.termsAndCondition = termsAndCondition;
	}
	

}
