package com.newco.marketplace.api.beans.so.retrieve;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.common.IAPIRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the SORetrieveService
 * @author Infosys
 *
 */

@XStreamAlias("soRequest")
@Namespace("http://www.servicelive.com/namespaces/soRequest")
public class SORetrieveRequest implements IAPIRequest{
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("responsefilter")
	private ResponseFilter responseFilter;
	
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

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public ResponseFilter getResponseFilter() {
		return responseFilter;
	}

	public void setResponseFilter(ResponseFilter responseFilter) {
		this.responseFilter = responseFilter;
	}

	
}
