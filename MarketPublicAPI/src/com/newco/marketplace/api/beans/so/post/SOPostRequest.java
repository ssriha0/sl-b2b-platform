package com.newco.marketplace.api.beans.so.post;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.common.IAPIRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the SOPostService
 * @author Infosys
 *
 */
@XStreamAlias("postRequest")
@Namespace("http://www.servicelive.com/namespaces/postRequest")
public class SOPostRequest implements IAPIRequest{
	
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("providerRouteInfo")
	private ProviderRouteInfo ProviderRouteInfo;
	
	private String resourceId;
	
	private String soId;
	
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

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public ProviderRouteInfo getProviderRouteInfo() {
		return ProviderRouteInfo;
	}

	public void setProviderRouteInfo(ProviderRouteInfo providerRouteInfo) {
		ProviderRouteInfo = providerRouteInfo;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the schemaLocation
	 */
	public String getSchemaLocation() {
		return schemaLocation;
	}

	/**
	 * @param schemaLocation the schemaLocation to set
	 */
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @return the schemaInstance
	 */
	public String getSchemaInstance() {
		return schemaInstance;
	}

	/**
	 * @param schemaInstance the schemaInstance to set
	 */
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}

}
