package com.newco.marketplace.api.beans.searchCriteria;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/07/03
 * the response for SOGetSearchCriteria
 *
 */
@XStreamAlias("serviceProviders")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceProviders {
	
	@XStreamImplicit(itemFieldName="serviceProvider")
	private List<ServiceProvider> serviceProvider;

	public List<ServiceProvider> getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(List<ServiceProvider> serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	
}
