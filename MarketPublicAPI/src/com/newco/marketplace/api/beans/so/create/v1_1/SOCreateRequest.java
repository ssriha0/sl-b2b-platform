package com.newco.marketplace.api.beans.so.create.v1_1;

import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.beans.so.create.ServiceOrderBean;
import com.newco.marketplace.api.common.IAPIRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the SOCreateService
 * @author Infosys
 *
 */
@XStreamAlias("soRequest")
@Namespace("http://www.servicelive.com/namespaces/soRequest")
public class SOCreateRequest extends UserIdentificationRequest implements IAPIRequest{
	
	@XStreamAlias("serviceorder")
	private ServiceOrderBean serviceOrder;

	public ServiceOrderBean getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrderBean serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

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