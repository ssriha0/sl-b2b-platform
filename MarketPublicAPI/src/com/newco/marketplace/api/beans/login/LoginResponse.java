package com.newco.marketplace.api.beans.login;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the CounterOfferResponse
 * @author Infosys
 *
 */
@XStreamAlias("authenticationResponse")
public class LoginResponse implements IAPIResponse{
	
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
	
	@XStreamAlias("lastName")
	private String lastname;
	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("entityId")
	private String entityId;
	
	@XStreamAlias("resourceId")
	private String resourceId;
	
	@XStreamAlias("profilePic")
	private String profilePic;

	public LoginResponse () {
		this.schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
		this.namespace = PublicAPIConstant.BuyerAccount.NAMESPACE;
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
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	

}
