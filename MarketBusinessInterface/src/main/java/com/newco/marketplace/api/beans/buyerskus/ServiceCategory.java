package com.newco.marketplace.api.beans.buyerskus;


import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("serviceCategory")
public class ServiceCategory {
	
	@XStreamAlias("categoryName")
	private String categoryName;
	
	@XStreamAlias("services")
	private Services services;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
}
