package com.newco.marketplace.api.beans.buyerskus;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("serviceCategories")
public class ServiceCategories {
	
	@XStreamImplicit(itemFieldName="serviceCategory")
	private List<ServiceCategory> serviceCategory;

	public List<ServiceCategory> getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(List<ServiceCategory> serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	

	

	

}
