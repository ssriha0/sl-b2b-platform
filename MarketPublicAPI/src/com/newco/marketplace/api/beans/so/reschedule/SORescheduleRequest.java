package com.newco.marketplace.api.beans.so.reschedule;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.common.IAPIRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the SORescheduleService
 * @author Infosys
 *
 */
@XStreamAlias("rescheduleRequest")
@Namespace("http://www.servicelive.com/namespaces/rescheduleRequest")
public class SORescheduleRequest implements IAPIRequest{
	
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("soRescheduleInfo")
	private SORescheduleInfo soRescheduleInfo;

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

	public SORescheduleInfo getSoRescheduleInfo() {
		return soRescheduleInfo;
	}

	public void setSoRescheduleInfo(SORescheduleInfo soRescheduleInfo) {
		this.soRescheduleInfo = soRescheduleInfo;
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
