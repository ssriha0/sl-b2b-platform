package com.newco.marketplace.api.beans.so.create;

import com.newco.marketplace.api.beans.so.Identification;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for 
 * the SOCreateService
 * @author Infosys
 *
 */
@XStreamAlias("soRequest")
public class SOCreateRequest {
	
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamAlias("serviceorder")
	private ServiceOrderBean serviceOrder;

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public ServiceOrderBean getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrderBean serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

}
