package com.newco.marketplace.api.beans.buyerskus;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("services")
public class Services {
	
	@XStreamImplicit(itemFieldName="service")
	private List<Service> service;

	public List<Service> getService() {
		return service;
	}

	public void setService(List<Service> service) {
		this.service = service;
	}

	

	

	

}
